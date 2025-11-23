import java.util.List;
import java.util.Scanner;

public class MenuPagamentos {

    private List<Pagamento> pagamentos;
    private List<Morador> moradores;
    private ControleFinanceiro controleFinanceiro;

    public MenuPagamentos(List<Pagamento> pagamentos, List<Morador> moradores) {
        this.pagamentos = pagamentos;
        this.moradores = moradores;
        this.controleFinanceiro = new ControleFinanceiro();

        // carregar pagamentos já existentes no arquivo
        try {
            controleFinanceiro.carregarPagamentosTXT("pagamentos.txt", moradores);
            this.pagamentos.clear();
            this.pagamentos.addAll(controleFinanceiro.getPagamentos());
        } catch (Exception e) {
            System.out.println("Nenhum pagamento carregado: " + e.getMessage());
        }
    }

    public void exibir() {
        Scanner sc = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 0) {

            System.out.println("\n===== MENU DE PAGAMENTOS =====");
            System.out.println("1 - Registrar Pagamento");
            System.out.println("2 - Listar Pagamentos");
            System.out.println("3 - Salvar Pagamentos em TXT");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            try {
                opcao = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                continue;
            }

            switch (opcao) {
                case 1 -> registrarPagamento();
                case 2 -> listarPagamentos();
                case 3 -> salvarTXT();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    // ============================================================
    // REGISTRAR PAGAMENTO (agora salva permanentemente)
    // ============================================================
    private void registrarPagamento() {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Documento do morador: ");
            String doc = sc.nextLine();

            Morador morador = null;

            for (Morador m : moradores) {
                if (m.getDocumento().equals(doc)) {
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

            Pagamento p = new Pagamento(morador, id, valor, Pagamento.Status.pendente);

            pagamentos.add(p);
            controleFinanceiro.getPagamentos().add(p);
            morador.getPagamentos().add(p);

            // salvar imediatamente
            salvarTXT();

            System.out.println("✓ Pagamento registrado e salvo!");

        } catch (Exception e) {
            System.out.println("Erro ao registrar: " + e.getMessage());
        }
    }

    // ============================================================
    // LISTAR
    // ============================================================
    private void listarPagamentos() {
        System.out.println("\n=== LISTA DE PAGAMENTOS ===");
        if (pagamentos.isEmpty()) {
            System.out.println("Nenhum pagamento.");
            return;
        }

        for (Pagamento p : pagamentos) {
            System.out.println("--------------------------------");
            System.out.println("ID: " + p.getId());
            System.out.println("Nome: " + p.getMorador().getNome());
            System.out.println("Documento: " + p.getMorador().getDocumento());
            System.out.println("Valor: R$ " + p.getValor());
            System.out.println("Status: " + p.getStatus());
        }
    }

    // ============================================================
    // SALVAR LISTA COMPLETA NO TXT (persistência cumulativa)
    // ============================================================
    private void salvarTXT() {
        try {
            controleFinanceiro.salvarPagamentosTXT("pagamentos.txt");
        } catch (Exception e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }
}