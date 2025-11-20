import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GerenciadorReservas {
    // Lista para "persistir" as reservas em memória (Regra: Persistência das reservas)
    private final List<Reserva> reservas;
    public GerenciadorReservas() {
        this.reservas = new ArrayList<>();
    }
    //  Implementação da Regra: disponibilidade
    /**
     * Verifica se o período de uma nova reserva se sobrepõe a qualquer reserva existente e não cancelada.
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
            Date existenteInicio = existente.getDataHoraInicio();
            Date existenteFim = existente.getDataHoraFim();

            // Condição de sobreposição: A nova reserva começa antes do fim da existente E termina depois do início da existente.
            if (novoInicio.before(existenteFim) && novoFim.after(existenteInicio)) {
                return true; // Conflito encontrado
            }
        }
        return false;
    }
    /**
     * Tenta adicionar uma nova reserva à lista.
     * @param novaReserva A Reserva a ser adicionada.
     * @return true se a reserva for feita com sucesso, false em caso de conflito.
     */
    public boolean adicionarReserva(Reserva novaReserva) {
        if (temConflito(novaReserva)) {
            System.out.println("Erro: Período de reserva indisponível (conflito de horário).");
            return false;
        }
        // Simulação de persistência: adiciona à lista
        this.reservas.add(novaReserva);
        System.out.println(" Reserva adicionada com sucesso.");
        return true;
    }
    public List<Reserva> listarReservasAtivas() {
        List<Reserva> ativas = new ArrayList<>();
        for (Reserva r : reservas) {
            if (!r.isCancelada()) {
                ativas.add(r);
            }
        }
        return ativas;
    }
}