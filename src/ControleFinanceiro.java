import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;


public class ControleFinanceiro {
    private List<Pagamento> pagamentos;
    private List<ChamadoManutencao> chamados;
    private final String financeiro="arquivos-pagamentos";


    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public List<ChamadoManutencao> getChamados() {
        return chamados;
    }

    //construtor
    public ControleFinanceiro(){
        this.pagamentos= carregarPagamentos();
        this.chamados=new ArrayList<>();
    }

    public double calcularTotalRecebido()  {
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
    public double calcularGastosManutencao(){

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
                .filter(c-> c.getStatus() == StatusChamado.ABERTO)
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

   public void salvarPagamentos(){
        try(ObjectOutputStream o=new ObjectOutputStream(new FileOutputStream(financeiro))){
            o.writeObject( pagamentos);
            System.out.println("arquivo salvo com sucesso: "+financeiro);
        } catch (IOException e) {
            System.err.println("Algo deu errado na criacao do arquivo: "+financeiro);
        }

   }

   public List<Pagamento>carregarPagamentos(){
       File novoArquivo=new File(financeiro);
       if(!novoArquivo.exists()){
           System.out.println("arquivo de persistencia nao encontrado");
           return new ArrayList<>();
       }
       try(ObjectInputStream a= new ObjectInputStream(new FileInputStream(financeiro))){
           System.out.println("pagamento carregado com sucesso");

           List<Pagamento> pagamentos1 = (List<Pagamento>) a.readObject();
           return pagamentos1;

       }catch (IOException| ClassNotFoundException e ){
           System.err.println("Erro ao carregar o arquivo");
           return new ArrayList<>();

       }

   }

    public void salvarRelatorioFinanceiroTxt() {

        String relatorio = gerarRelatorio();


        try (PrintWriter writer = new PrintWriter(new FileWriter(financeiro))) {
            writer.print(relatorio);
            System.out.println("\nRelatório financeiro resumido salvo em: " + financeiro);

        } catch (IOException e) {
            System.err.println("Erro ao salvar o relatório financeiro TXT: " + e.getMessage());
        }
    }


}
