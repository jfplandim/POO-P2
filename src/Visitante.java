import java.util.Date;

public class Visitante extends Pessoa{
    private Date dataHoraEntrada;
    private Date dataHoraSaida;
    private Morador moradorVisitado;

    public Visitante(String nome, String documento, String telefone, Morador moradorVisitado) throws CampoInvalidoException{
        super(nome, documento, telefone);
        this.dataHoraEntrada = new Date(); //entrada do visitante
        this.dataHoraSaida = null;   //visitante ainda não saiu
        this.moradorVisitado = moradorVisitado;
    }

    public void registrarSaida() throws CampoInvalidoException{
        //verificação para impedir a saída duas vezes
        if (this.dataHoraSaida != null){
            throw new CampoInvalidoException("O visitante já teve sua saída registrada.");
        }
        this.dataHoraSaida = new Date();
    }
}
