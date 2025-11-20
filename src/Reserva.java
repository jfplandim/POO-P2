import java.util.Date;

public class Reserva {
    private Date dataHoraInicio;
    private int duracaoHoras;
    private String responsavel;

    // Construtor
    public Reserva(Date dataHoraInicio, int duracaoHoras, String responsavel) {
        this.dataHoraInicio = dataHoraInicio;
        this.duracaoHoras = duracaoHoras;
        this.responsavel = responsavel;
    }
    public Date getDataHoraInicio() {
        return dataHoraInicio;
    }
    public int getDuracaoHoras() {
        return duracaoHoras;
    }
    // Metodo para obter a data/hora de término da reserva
    public Date getDataHoraFim() {
        long milliseconds = dataHoraInicio.getTime() + (long)duracaoHoras * 60 * 60 * 1000;
        return new Date(milliseconds);
    }
}