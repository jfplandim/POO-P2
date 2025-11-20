import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Reserva {
    // Limite de duração da reserva em horas (Regra: limite de horas)
    private static final int LIMITE_DURACAO_HORAS = 8;
    // Tempo limite para cancelamento sem multa, em milissegundos (Regra: cancelamento com multa (48h))
    private static final long LIMITE_CANCELAMENTO_SEM_MULTA = TimeUnit.HOURS.toMillis(48);
    private Date dataHoraInicio;
    private int duracaoHoras;
    private String responsavel;
    private boolean cancelada;

    // Construtor
    public Reserva(Date dataHoraInicio, int duracaoHoras, String responsavel) throws IllegalArgumentException{
        // Validação da regra: limite de horas
        if (duracaoHoras <= 0 || duracaoHoras > LIMITE_DURACAO_HORAS) {
            throw new IllegalArgumentException("Duração inválida. A reserva deve ter entre 1 e " + LIMITE_DURACAO_HORAS + " horas.");
        }
        this.dataHoraInicio = dataHoraInicio;
        this.duracaoHoras = duracaoHoras;
        this.responsavel = responsavel;
        this.cancelada = false;
    }
    public Date getDataHoraInicio() {
        return dataHoraInicio;
    }
    public int getDuracaoHoras() {
        return duracaoHoras;
    }
    public String getResponsavel() {
        return responsavel;
    }

    public boolean isCancelada() {
        return cancelada;
    }
    // Metodo para obter a data/hora de término da reserva
    public Date getDataHoraFim() {
        long milliseconds = dataHoraInicio.getTime() + (long)duracaoHoras * 60 * 60 * 1000;
        return new Date(milliseconds);
    }
}