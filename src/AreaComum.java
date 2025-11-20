import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class AreaComum {
    private String nome;
    private int limiteHoras;
    private List<Reserva> reservas;

    // Construtor
    public AreaComum(String nome, int limiteHoras) {
        this.nome = nome;
        this.limiteHoras = limiteHoras;
        this.reservas = new ArrayList<>();
    }

    // Metodo para verificar se a área está disponível para uma nova reserva
    public boolean verificarDisponibilidade(Date dataHoraInicio, int duracaoHoras) {
        // 1. Verificar se a duração excede o limite
        if (duracaoHoras > this.limiteHoras) {
            System.out.println(" Reserva excede o limite de " + this.limiteHoras + " horas.");
            return false;
        }

        // 2. Definir o período da nova reserva
        long inicioNova = dataHoraInicio.getTime();
        long fimNova = inicioNova + (long)duracaoHoras * 60 * 60 * 1000;

        // 3. Verificar se há sobreposição com as reservas existentes
        for (Reserva reservaExistente : reservas) {
            long inicioExistente = reservaExistente.getDataHoraInicio().getTime();
            long fimExistente = reservaExistente.getDataHoraFim().getTime();

            // Lógica de sobreposição:
            // Uma nova reserva se sobrepõe se:
            // (Início Nova < Fim Existente) E (Fim Nova > Início Existente)
            if (inicioNova < fimExistente && fimNova > inicioExistente) {
                System.out.println("Conflito de horário com uma reserva existente.");
                return false; // Sobreposição encontrada
            }
        }
        return true;
    }
    // Metodo de exemplo para adicionar uma reserva (assumindo que já foi verificado)
    public void adicionarReserva(Reserva novaReserva) {
        this.reservas.add(novaReserva);
        System.out.println(" Reserva adicionada com sucesso para " + this.nome + ".");
    }
    public String getNome() {
        return nome;
    }
    public List<Reserva> getReservas() {
        return reservas;
    }
}