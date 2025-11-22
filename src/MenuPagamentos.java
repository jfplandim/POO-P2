import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Date;

public class MenuPagamentos {

    private List<Pagamento> pagamentos;
    private List<Morador> moradores;
    ControleFinanceiro CF=new ControleFinanceiro();

    public MenuPagamentos(List<Pagamento> pagamentos, List<Morador> moradores) {
        this.pagamentos = pagamentos;
        this.moradores =moradores;

    }

    public void exibir() {
        Scanner sc = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 0) {

            System.out.println("\n===== MENU DE PAGAMENTOS =====");
            System.out.println("1 - Registrar Pagamento");
            System.out.println("2 - Listar Pagamentos");
            System.out.println("3 - Pagamentos Recebidos");
            System.out.println("4 - Pagamentos Atrasados");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            try {
                opcao = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Entrada inválida!");
                continue;
            }

            switch (opcao) {
                case 1 -> registrarPagamento();
                case 2 -> listarPagamentos();
                case 3-> GerarRelatorioTXT();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    // =========================================================
    // 1 - Registrar Pagamento
    // =========================================================
    private void registrarPagamento() {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("\n=== REGISTRAR PAGAMENTO ===");

            // 1. Buscar morador
            System.out.print("Documento do morador: ");
            String doc = sc.nextLine();

            Morador morador = null;
            for (Morador m : moradores) {
                if (m.getDocumento().equalsIgnoreCase(doc)) {
                    morador = m;
                    break;
                }
            }

            if (morador == null) {
                System.out.println("Morador não encontrado!");
                return;
            }

            System.out.print("ID do pagamento: ");
            int id = Integer.parseInt(sc.nextLine());

            System.out.print("Valor: ");
            double valor = Double.parseDouble(sc.nextLine());

            System.out.print("Mês de referência: ");
            String mes = sc.nextLine();

            System.out.print("Pagamento de manutenção? (s/n): ");
            boolean manutencao = sc.nextLine().equalsIgnoreCase("s");

            // Define vencimento (hoje como exemplo)
            Date vencimento = new Date();

            Pagamento p = new Pagamento(
                    morador,
                    id,
                    valor,
                    mes,
                    null,          // dataPagamento (será setado depois)
                    vencimento,
                    Pagamento.Status.pendente
            );

            // seta flag de manutenção
            if (manutencao) {
                // você precisa adicionar um setManutencao se quiser mudar isso
            }

            pagamentos.add(p);
            morador.getPagamentos().add(p);

            System.out.println("Pagamento registrado com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao registrar pagamento: " + e.getMessage());
        }
    }

    // =========================================================
    // 2 - Listar todos os pagamentos
    // =========================================================
    private void listarPagamentos() {

        System.out.println("\n=== LISTA DE PAGAMENTOS ===");

        if (pagamentos.isEmpty()) {
            System.out.println("Nenhum pagamento registrado.");
            return;
        }

        for (Pagamento p : pagamentos) {
            System.out.println("----------------------------------");
            System.out.println("ID: " + p.getId());
            System.out.println("Valor: R$ " + p.getValor());
            System.out.println("Mês: " + p.getMes());
            System.out.println("Morador: " + p.getMorador().getNome());
            System.out.println("Documento: " + p.getMorador().getDocumento());
            System.out.println("Recebido? " + (p.recebido() ? "Sim" : "Não"));
            System.out.println("Atrasado? " + (p.Atrasado() ? "Sim" : "Não"));
        }
    }

  private void GerarRelatorioTXT(){
        CF.salvarRelatorioFinanceiroTxt();
  }


}
