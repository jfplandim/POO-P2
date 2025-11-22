import java.util.Date;

public class ChamadoManutencao {
    private static int proximoId = 1;
    private int id;
    private String areaAfetada;
    private String descricao;
    private StatusChamado status; // Agora o Java reconhece isso!
    private double custo;
    private Date dataAbertura;
    private Date dataFechamento;


    // Construtor
    public ChamadoManutencao(String areaAfetada, String descricao) {
        this.id = proximoId++; //id automatico
        this.areaAfetada = areaAfetada;
        this.descricao = descricao;
        this.status = StatusChamado.ABERTO;
        this.dataAbertura = new Date();
        this.custo = 0.0;
    }

    // Métodos de Negócio
    public void iniciarAtendimento() throws OperacaoInvalidaException {
       //se ja foi fechado, não pode mais iniciar atendimento
        if (this.status == StatusChamado.FECHADO) {
            throw new OperacaoInvalidaException("Não é possível iniciar atendimento: o chamado já está fechado.");
        }
        //se ja esta em atendimento, nao pode iniciar um novo
        if (this.status == StatusChamado.EM_ANDAMENTO) {
            throw new OperacaoInvalidaException("O chamado ja está em andamento.");
        }
        //status era aberto -> muda para em andamento
        this.status = StatusChamado.EM_ANDAMENTO;
    }

    public void fecharChamado(double custo) throws OperacaoInvalidaException {
        //nao pode fechar o que ja esta fechado
        if (this.status == StatusChamado.FECHADO){
            throw new OperacaoInvalidaException("Esta chamado já está fechado");
        }
        //nao pode fechar s enunca entrou em atendimento
        if (this.status == StatusChamado.ABERTO){
            throw new OperacaoInvalidaException("Não é possível fechar: o chamado ainda não foi colocado em andamento.");
        }
        this.status = StatusChamado.FECHADO;
        this.custo = custo;
        this.dataFechamento = new Date();
    }

    // Getters (Necessários para o Menu mostrar os dados)
    public int getId() { return id; }
    public String getAreaAfetada() {return areaAfetada; }
    public String getDescricao() { return descricao; }
    public StatusChamado getStatus() { return status; }
    public double getCusto() { return custo; }
    public Date getDataAbertura() {
        return dataAbertura;
    }

    public Date getDataFechamento() {
        return dataFechamento;
    }

    @Override
    public String toString() {
        String abertura = (dataAbertura != null) ? dataAbertura.toString() : "—";
        String fechamento = (dataFechamento != null) ? dataFechamento.toString() : "—";

        return "\n--- Chamado " + id + " ---" +
                "\nÁrea afetada: " + areaAfetada +
                "\nDescrição: " + descricao +
                "\nStatus: " + status +
                "\nAbertura: " + abertura +
                "\nFechamento: " + fechamento +
                "\nCusto: R$ " + String.format("%.2f", custo) +
                "\n----------------------------\n";
    }
}