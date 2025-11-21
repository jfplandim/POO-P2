import java.util.ArrayList;
import java.util.List;

public class controleFinanceiro {
    private List<Pagamento> pagamentos;
    private List<Chamado> chamados;


    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public List<Chamado> getChamados() {
        return chamados;
    }

    //construtor
    public controleFinanceiro(){
        this.pagamentos= new ArrayList<>();
        this.chamados=new ArrayList<>();
    }

    public double calcularTotalRecebido(){
        return  pagamentos.stream()
                .filter(Pagamento::recebido)
                .mapToDouble(Pagamento::getValor)
                .sum();
    }

    public double calcularAtraso(){
        return pagamentos.stream()
                .filter(Pagamento::Atrasado)
                .mapToDouble(Pagamento::getValor)
                .sum();

    }
    public double calcularGastosManutencao() {
        return pagamentos.stream()
                .filter(Pagamento::isManutencao)
                .mapToDouble(Pagamento::getValor)
                .sum();
    }


    public String gerarRelatorio(){
        double totalrecebido= calcularTotalRecebido();
        double Atraso= calcularAtraso();
        double manutencao=calcularGastosManutencao();

        double saldoDisponivel=totalrecebido-manutencao;

        long chamadosAbertos=chamados.stream()
                .filter(c-> c.getStatus().equals("aberto"))
                .count();
        return String.format(
                "================================================\n" +
                        "|           RELATÓRIO FINANCEIRO             |\n" +
                        "================================================\n" +
                        "| Total Recebido:         R$ %,.2f\n" +
                        "| Gastos com Manutenção:  R$ %,.2f\n" +
                        "|----------------------------------------------\n" +
                        "| **Saldo Operacional**:  R$ %,.2f\n" +
                        "|----------------------------------------------\n" +
                        "| Total em Atraso:        R$ %,.2f\n" +
                        "| Chamados Abertos:       %d\n" +
                        "================================================\n",
                totalrecebido,
                manutencao,
                saldoDisponivel,
                Atraso,
                chamadosAbertos
        );
    }

}
