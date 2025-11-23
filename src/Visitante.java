import java.util.Date;

public class Visitante extends Pessoa {
    private Date dataHoraEntrada;
    private Date dataHoraSaida;
    private Morador moradorVisitado;

    public Visitante(String nome, String documento, String telefone, Morador moradorVisitado) throws CampoInvalidoException {
        super(nome, documento, telefone);
        this.dataHoraEntrada = new Date(); //entrada do visitante
        this.dataHoraSaida = null;   //visitante ainda não saiu
        this.moradorVisitado = moradorVisitado;
    }
    public Visitante(String nome, String documento, String telefone,Morador moradorVisitado, Date entrada, Date saida)throws CampoInvalidoException {
        super(nome,documento,telefone);
        this.dataHoraEntrada=entrada;
        this.dataHoraSaida=saida;
        this.moradorVisitado=moradorVisitado;
    }


    public void setDataHoraEntrada(Date dataHoraEntrada) {
        this.dataHoraEntrada = dataHoraEntrada;
    }

    public void setDataHoraSaida(Date dataHoraSaida) {
        this.dataHoraSaida = dataHoraSaida;
    }

    public void setMoradorVisitado(Morador moradorVisitado) {
        this.moradorVisitado = moradorVisitado;
    }

    public Date getDataHoraEntrada() {
        return dataHoraEntrada;
    }

    public Date getDataHoraSaida() {
        return dataHoraSaida;
    }

    public Morador getMoradorVisitado() {
        return moradorVisitado;
    }

    public void registrarSaida() throws CampoInvalidoException {
        //verificação para impedir a saída duas vezes
        if (this.dataHoraSaida != null){
            throw new CampoInvalidoException("O visitante já teve sua saída registrada.");
        }
        this.dataHoraSaida = new Date();
    }
    //metodo para formatar e gerar o relatorio de visita
        @Override
        public String toString() {
            String saidaFormatada = (dataHoraSaida != null) ? dataHoraSaida.toString() : "Ainda no condomínio";

            return "Visitante: " + getNome() + "\n" +
                    "Documento: " + getDocumento() + "\n" +
                    "Telefone: " + getTelefone() + "\n" +
                    "Morador visitado: " + moradorVisitado.getNome() + "\n" +
                    "Entrada: " + dataHoraEntrada + "\n" +
                    "Saída: " + saidaFormatada;
        }
}
