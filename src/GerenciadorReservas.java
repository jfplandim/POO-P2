import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GerenciadorReservas {
    // Lista para "persistir" as reservas em memória (Regra: Persistência das reservas)
    private final List<Reserva> reservas;

    // Horário de funcionamento (8h às 22h)
    private static final int HORA_ABERTURA = 8;
    private static final int HORA_FECHAMENTO = 22;

    public GerenciadorReservas() {
        this.reservas = new ArrayList<>();
    }

    /**
     * Limpa segundos e milissegundos de uma data, mantendo apenas hora e minutos
     * @param data Data a ser limpa
     * @return Nova data sem segundos e milissegundos
     */
    public static Date limparSegundos(Date data) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * PASSO 1: Retorna os dias que têm pelo menos um horário disponível para uma área específica
     * @param dataInicio Data inicial do período de busca
     * @param dataFim Data final do período de busca
     * @param duracaoDesejada Duração em horas que a pessoa quer reservar
     * @param area Área desejada (Academia, Piscina, Salão de Festas)
     * @return Lista de datas (dias) que possuem horários disponíveis
     */
    public List<Date> getDiasDisponiveis(Date dataInicio, Date dataFim, int duracaoDesejada, AreaReservavel area) {
        List<Date> diasDisponiveis = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(dataInicio);

        // Zera horas/minutos/segundos
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        Calendar calFim = Calendar.getInstance();
        calFim.setTime(dataFim);
        calFim.set(Calendar.HOUR_OF_DAY, 0);
        calFim.set(Calendar.MINUTE, 0);
        calFim.set(Calendar.SECOND, 0);
        calFim.set(Calendar.MILLISECOND, 0);

        Date hoje = new Date();
        Calendar calHoje = Calendar.getInstance();
        calHoje.setTime(hoje);
        calHoje.set(Calendar.HOUR_OF_DAY, 0);
        calHoje.set(Calendar.MINUTE, 0);
        calHoje.set(Calendar.SECOND, 0);
        calHoje.set(Calendar.MILLISECOND, 0);
        Date inicioHoje = calHoje.getTime();

        // Percorre todos os dias do período
        while (!cal.after(calFim)) {
            Date diaAtual = cal.getTime();

            // Ignora dias passados (apenas datas futuras ou hoje)
            if (!diaAtual.before(inicioHoje)) {
                // Verifica se este dia tem pelo menos um horário disponível
                List<String> horarios = getHorariosDisponiveis(diaAtual, duracaoDesejada, area);
                if (!horarios.isEmpty()) {
                    diasDisponiveis.add(diaAtual);
                }
            }

            // Avança para o próximo dia
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        return diasDisponiveis;
    }

    /**
     * PASSO 2: Obtém os horários disponíveis para um dia e área específicos
     * @param data A data escolhida (apenas o dia é considerado)
     * @param duracaoDesejada Duração em horas que a pessoa quer reservar
     * @param area Área desejada
     * @return Lista de horários disponíveis no formato "HH:00"
     */
    public List<String> getHorariosDisponiveis(Date data, int duracaoDesejada, AreaReservavel area) {
        List<String> horariosDisponiveis = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);

        // Zera horas/minutos/segundos para trabalhar apenas com o dia
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        Date hoje = new Date();

        // Verifica cada hora do dia
        for (int hora = HORA_ABERTURA; hora < HORA_FECHAMENTO; hora++) {
            cal.set(Calendar.HOUR_OF_DAY, hora);
            Date horarioInicio = cal.getTime();

            // Se é hoje, ignora horários que já passaram
            if (horarioInicio.before(hoje)) {
                continue;
            }

            // Verifica se há espaço para a duração desejada
            if (hora + duracaoDesejada <= HORA_FECHAMENTO) {
                if (horarioEstaDisponivel(horarioInicio, duracaoDesejada, area)) {
                    horariosDisponiveis.add(String.format("%02d:00", hora));
                }
            }
        }
        return horariosDisponiveis;
    }

    /**
     * Verifica se um horário específico está disponível para uma área (sem conflitos)
     * @param horarioInicio Data e hora de início desejada
     * @param duracao Duração em horas
     * @param area Área desejada
     * @return true se estiver disponível, false se houver conflito
     */
    private boolean horarioEstaDisponivel(Date horarioInicio, int duracao, AreaReservavel area) {
        Calendar calInicio = Calendar.getInstance();
        calInicio.setTime(horarioInicio);
        calInicio.add(Calendar.HOUR_OF_DAY, duracao);
        Date horarioFim = calInicio.getTime();

        // Verifica conflito com reservas existentes da mesma área
        for (Reserva reserva : reservas) {
            if (reserva.isCancelada()) {
                continue; // Ignora reservas canceladas
            }

            // Verifica apenas reservas da mesma área
            if (!reserva.getArea().equals(area)) {
                continue;
            }

            Date reservaInicio = reserva.getDataHoraInicio();
            Date reservaFim = reserva.getDataHoraFim();

            // Verifica se há sobreposição de horários
            boolean haConflito = !(horarioFim.getTime() <= reservaInicio.getTime() ||
                    horarioInicio.getTime() >= reservaFim.getTime());

            if (haConflito) {
                return false;
            }
        }

        return true;
    }

    /**
     * Verifica se o período de uma nova reserva se sobrepõe a qualquer reserva existente da mesma área
     * @param novaReserva A Reserva a ser verificada.
     * @return true se houver conflito de horário, false caso contrário.
     */
    private boolean temConflito(Reserva novaReserva) {
        Date novoInicio = novaReserva.getDataHoraInicio();
        Date novoFim = novaReserva.getDataHoraFim();

        for (Reserva existente : reservas) {
            // Ignora reservas canceladas
            if (existente.isCancelada()) {
                continue;
            }

            // Verifica apenas conflitos na mesma área
            if (!existente.getArea().equals(novaReserva.getArea())) {
                continue;
            }

            Date existenteInicio = existente.getDataHoraInicio();
            Date existenteFim = existente.getDataHoraFim();

            // Condição de sobreposição
            if (novoInicio.before(existenteFim) && novoFim.after(existenteInicio)) {
                return true; // Conflito encontrado
            }
        }
        return false;
    }

    /**
     * Tenta adicionar uma nova reserva à lista.
     * IMPORTANTE: Automaticamente limpa segundos e milissegundos da data
     * @param dataHoraInicio Data e hora de início (será limpa automaticamente)
     * @param duracaoHoras Duração da reserva em horas
     * @param responsavel Nome do responsável pela reserva
     * @param area Área a ser reservada
     * @return true se a reserva for feita com sucesso, false em caso de conflito
     */
    public boolean adicionarReserva(Date dataHoraInicio, int duracaoHoras, String responsavel, AreaReservavel area) {
        try {
            // Limpa segundos e milissegundos automaticamente
            Date dataHoraLimpa = limparSegundos(dataHoraInicio);

            // Cria a reserva com a data limpa
            Reserva novaReserva = new Reserva(dataHoraLimpa, duracaoHoras, responsavel, area);

            if (temConflito(novaReserva)) {
                System.out.println("Erro: Período de reserva indisponível para " + area.getNome() + " (conflito de horário).");
                return false;
            }

            // Simulação de persistência: adiciona à lista
            this.reservas.add(novaReserva);
            System.out.println("✓ Reserva adicionada com sucesso para " + area.getNome() + ".");
            return true;

        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao criar reserva: " + e.getMessage());
            return false;
        }
    }

    /**
     * Lista todas as reservas ativas (não canceladas)
     * @return Lista de reservas ativas
     */
    public List<Reserva> listarReservasAtivas() {
        List<Reserva> ativas = new ArrayList<>();
        for (Reserva r : reservas) {
            if (!r.isCancelada()) {
                ativas.add(r);
            }
        }
        return ativas;
    }

    /**
     * Lista todas as reservas ativas de um responsável específico
     * @param responsavel Nome do responsável
     * @return Lista de reservas do responsável
     */
    public List<Reserva> listarReservasPorResponsavel(String responsavel) {
        List<Reserva> reservasDoResponsavel = new ArrayList<>();
        for (Reserva r : reservas) {
            if (!r.isCancelada() && r.getResponsavel().equalsIgnoreCase(responsavel)) {
                reservasDoResponsavel.add(r);
            }
        }
        return reservasDoResponsavel;
    }

    /**
     * Busca uma reserva pelo ID
     * @param id ID da reserva
     * @return Reserva encontrada ou null
     */
    public Reserva buscarReservaPorId(int id) {
        for (Reserva r : reservas) {
            if (r.getId() == id) {
                return r;
            }
        }
        return null;
    }

    /**
     * Cancela uma reserva específica pelo ID
     * @param idReserva ID da reserva a ser cancelada
     * @return true se cancelada com sucesso, false caso contrário
     */
    public boolean cancelarReserva(int idReserva) {
        Reserva reserva = buscarReservaPorId(idReserva);

        if (reserva == null) {
            System.out.println("Erro: Reserva não encontrada.");
            return false;
        }

        try {
            return reserva.cancelar(new Date());
        } catch (CampoInvalidoException e) {
            System.out.println("Erro: " + e.getMessage());
            return false;
        }
    }
}