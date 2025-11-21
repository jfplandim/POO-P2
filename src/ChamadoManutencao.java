import java.util.Date;

public class ChamadoManutencao {
    private int id;
    private String areaAfetada;
    private String descricao;
    private StatusChamado status; // Agora o Java reconhece isso!
    private double custo;
    private Date dataAbertura;
    private Date dataFechamento;

    // Construtor
    public ChamadoManutencao(int id, String areaAfetada, String descricao) {
        this.id = id;
        this.areaAfetada = areaAfetada;
        this.descricao = descricao;
        this.status = StatusChamado.ABERTO;
        this.dataAbertura = new Date();
        this.custo = 0.0;
    }

    // Métodos de Negócio
    public void iniciarAtendimento() {
        if (this.status == StatusChamado.FECHADO) {
            System.out.println("Erro: Chamado já fechado.");
            return;
        }
        this.status = StatusChamado.EM_ANDAMENTO;
        System.out.println("Chamado " + id + " em andamento.");
    }

    public void fecharChamado(double custo) {
        this.status = StatusChamado.FECHADO;
        this.custo = custo;
        this.dataFechamento = new Date();
        System.out.println("Chamado " + id + " finalizado. Custo: R$ " + custo);
    }

    // Getters (Necessários para o Menu mostrar os dados)
    public int getId() { return id; }
    public String getAreaAfetada() { return areaAfetada; }
    public String getDescricao() { return descricao; }
    public StatusChamado getStatus() { return status; }
    public double getCusto() { return custo; }

    @Override
    public String toString() {
        return String.format("ID: %d | Área: %s | Desc: %s | Status: %s | Custo: R$ %.2f",
                id, areaAfetada, descricao, status, custo);
    }
}