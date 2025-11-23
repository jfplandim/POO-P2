import java.util.List;
import java.util.Scanner;
import java.text.SimpleDateFormat;

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
            controleFinanceiro.carregarPagamentosComMultas("pagamentos.txt", moradores);
            this.pagamentos.clear();
            this.pagamentos.addAll(controleFinanceiro.getPagamentos());
        } catch (Exception e) {
            System.out.println("Nenhum pagamento carregado: " + e.getMessage());
        }
    }

    public void exibir() {
        Scanner sc = new Scanner(System.in);
        int opcao = -1;

        //Verifica atrasos ao abrir o menu
        controleFinanceiro.verificarPagamentosAtrasados();

        while (opcao != 0) {

            System.out.println("\n===== MENU DE PAGAMENTOS =====");
            System.out.println("1 - Registrar Novo Pagamento");
            System.out.println("2 - Efetuar Pagamento");
            System.out.println("3 - Listar Todos os Pagamentos");
            System.out.println("4 - Listar Pagamentos Atrasados");
            System.out.println("5 - Salvar Pagamentos em TXT");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            try {
                opcao = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                continue;
            }

            switch (opcao) {
                case 1 -> registrarPagamento();
                case 2 -> efetuarPagamento();
                case 3 -> listarPagamentos();
                case 4 -> listarPagamentosAtrasados();
                case 5 -> salvarTXT();
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

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        for (Pagamento p : pagamentos) {
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println("ID: " + p.getId());
            System.out.println("Nome: " + p.getMorador().getNome());
            System.out.println("Documento: " + p.getMorador().getDocumento());
            System.out.println("Valor original: R$ " + String.format("%.2f", p.getValor()));

            if (p.getMulta() > 0) {
                System.out.println("Multa: R$ " + String.format("%.2f", p.getMulta()));
                System.out.println("TOTAL: R$ " + String.format("%.2f", p.getValorTotal()));
            }

            System.out.println("Vencimento: " + sdf.format(p.getDataVencimento()));

            if (p.getDataPagamento() != null) {
                System.out.println("Pago em: " + sdf.format(p.getDataPagamento()));
            }

            String statusIcon = switch (p.getStatus()) {
                case pago -> "✓";
                case pendente -> "⏳";
                case atrasado -> "⚠";
                case cancelado -> "✗";
            };

            System.out.println("Status: " + statusIcon + " " + p.getStatus());
        }

        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("Total de pagamentos: " + pagamentos.size());
    }

    // ============================================================
    // SALVAR LISTA COMPLETA NO TXT (persistência cumulativa)
    // ============================================================
    private void salvarTXT() {
        try {
            controleFinanceiro.salvarPagamentosComMultas("pagamentos.txt");
        } catch (Exception e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }

    // ============================================================
    // EFETUAR PAGAMENTO (marcar como pago)
    // ============================================================
    private void efetuarPagamento() {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("ID do pagamento: ");
            int id = Integer.parseInt(sc.nextLine());

            Pagamento pagamento = null;
            for (Pagamento p : pagamentos) {
                if (p.getId() == id) {
                    pagamento = p;
                    break;
                }
            }

            if (pagamento == null) {
                System.out.println("✗ Pagamento não encontrado!");
                return;
            }

            if (pagamento.recebido()) {
                System.out.println("✗ Este pagamento já foi efetuado!");
                return;
            }

            pagamento.registrarPagamento();
            salvarTXT(); // Salva automaticamente

            System.out.println("Valor pago: R$ " + String.format("%.2f", pagamento.getValorTotal()));

        } catch (Exception e) {
            System.out.println("✗ Erro: " + e.getMessage());
        }
    }

    // ============================================================
    // LISTAR APENAS ATRASADOS
    // ============================================================
    private void listarPagamentosAtrasados() {
        System.out.println("\n=== PAGAMENTOS ATRASADOS ===");

        boolean encontrou = false;

        for (Pagamento p : pagamentos) {
            if (p.getStatus() == Pagamento.Status.atrasado) {
                System.out.println("--------------------------------");
                System.out.println("ID: " + p.getId());
                System.out.println("Nome: " + p.getMorador().getNome());
                System.out.println("Documento: " + p.getMorador().getDocumento());
                System.out.println("Valor original: R$ " + p.getValor());
                System.out.println("Multa: R$ " + p.getMulta());
                System.out.println("TOTAL: R$ " + String.format("%.2f", p.getValorTotal()));
                System.out.println("Vencimento: " + p.getDataVencimento());
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("✓ Nenhum pagamento atrasado!");
        }
    }
}