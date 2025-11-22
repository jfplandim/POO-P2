import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SistemaCondominio {
    private List<Morador> moradores;
    private List<Apartamento> apartamentos;
    private List<Visitante> visitantes;
    private List<Reserva> reservas;
    private List<AreaComum> areasComuns;
    private ControleFinanceiro controleFinanceiro;
    private List<ChamadoManutencao> chamados;
    private MenuMoradores menuMoradores;

    public SistemaCondominio() {
        this.moradores = new ArrayList<>();
        this.apartamentos = new ArrayList<>();
        this.visitantes = new ArrayList<>();
        this.reservas = new ArrayList<>();
        this.areasComuns = new ArrayList<>();
        this.controleFinanceiro = new ControleFinanceiro();
        this.chamados = new ArrayList<>();

        this.menuMoradores = new MenuMoradores(this);
    }

    public void abrirChamadoManutencao() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n=== ABERTURA DE CHAMADO ===");

        System.out.println("Área afetada: ");
        String area = sc.nextLine();

        System.out.println("Descrição do problema: ");
        String desc = sc.nextLine();

        //cria o chamado automaticamente com ID gerado
        ChamadoManutencao chamado = new ChamadoManutencao(area, desc);
        //adiciona na lista geral do sistema
        this.controleFinanceiro.getChamados().add(chamado);
        System.out.println("\nChamdado criado com sucesso");
        System.out.println(chamado);
    }

    private ChamadoManutencao buscarChamadoPorId(int id) throws OperacaoInvalidaException {
        for (ChamadoManutencao c : this.controleFinanceiro.getChamados()) {
            if (c.getId() == id) {
                return c;
            }
        }
        throw new OperacaoInvalidaException("Chamado não encontrado: ID " + id);
    }

    public void atualizarChamado() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n=== ATUALIZAR CHAMADO ===");

        try {
            System.out.print("Informe o ID do chamado: ");
            int id = Integer.parseInt(sc.nextLine());

            // Busca com exceção
            ChamadoManutencao chamado = buscarChamadoPorId(id);

            int opcao = -1;

            while (opcao != 3) {
                System.out.println("\n--- O que deseja fazer com o chamado " + id + "? ---");
                System.out.println("1 - Iniciar atendimento");
                System.out.println("2 - Fechar chamado");
                System.out.println("3 - Voltar");
                System.out.print("Escolha: ");

                opcao = Integer.parseInt(sc.nextLine());

                try {

                    switch (opcao) {
                        case 1:
                            chamado.iniciarAtendimento();
                            System.out.println("Atendimento iniciado com sucesso!");
                            break;

                        case 2:
                            System.out.print("Informe o custo da manutenção: ");
                            double custo = Double.parseDouble(sc.nextLine());
                            chamado.fecharChamado(custo);
                            System.out.println("Chamado fechado com sucesso!");
                            break;

                        case 3:
                            System.out.println("Voltando...");
                            break;

                        default:
                            System.out.println("Opção inválida.");
                    }

                } catch (OperacaoInvalidaException e) {
                    System.out.println("=================================");
                    System.out.println("|      ERRO NA OPERAÇÃO        |");
                    System.out.println("| " + e.getMessage());
                    System.out.println("=================================");
                }
            }

        } catch (OperacaoInvalidaException e) {
            System.out.println("=================================");
            System.out.println("|      ERRO NA OPERAÇÃO        |");
            System.out.println("| " + e.getMessage());
            System.out.println("=================================");
        } catch (NumberFormatException e) {
            System.out.println("=================================");
            System.out.println("|      ERRO NA OPERAÇÃO        |");
            System.out.println("| ID inválido! Digite números. |");
            System.out.println("=================================");
        }
    }

    public void listarChamadosPorStatus() {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n=== LISTAR CHAMADOS POR STATUS ===");
        System.out.println("1 - Abertos");
        System.out.println("2 - Em andamento");
        System.out.println("3 - Fechados");
        System.out.println("4 - Voltar");
        System.out.print("Escolha: ");

        int opcao = Integer.parseInt(sc.nextLine());
        StatusChamado statusEscolhido = null;

        switch (opcao) {
            case 1:
                statusEscolhido = StatusChamado.ABERTO;
                break;
            case 2:
                statusEscolhido = StatusChamado.EM_ANDAMENTO;
                break;
            case 3:
                statusEscolhido = StatusChamado.FECHADO;
                break;
            case 4:
                return;
            default:
                System.out.println("Opção inválida!");
                return;
        }

        System.out.println("\n=== RESULTADOS ===");

        boolean encontrou = false;

        for (ChamadoManutencao c : this.controleFinanceiro.getChamados()) {
            if (c.getStatus() == statusEscolhido) {
                System.out.println(c);
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhum chamado com esse status.");
        }
    }

    public void menuManutencao() {
        Scanner sc = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 4) {
            System.out.println("\n===== MENU DE MANUTENÇÃO =====");
            System.out.println("1 - Abrir chamado");
            System.out.println("2 - Listar chamados por status");
            System.out.println("3 - Atualizar chamado");
            System.out.println("4 - Voltar");
            System.out.print("Escolha: ");

            opcao = Integer.parseInt(sc.nextLine());

            switch (opcao) {
                case 1:
                    abrirChamadoManutencao();
                    break;

                case 2:
                    listarChamadosPorStatus();
                    break;

                case 3:
                    atualizarChamado();
                    break;

                case 4:
                    System.out.println("Voltando ao menu principal...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        }
    }


    public void exibirMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n===== SISTEMA DO CONDOMÍNIO =====");
            System.out.println("1 - Moradores");
            System.out.println("2 - Apartamentos");
            System.out.println("3 - Visitantes");
            System.out.println("4 - Reservas");
            System.out.println("5 - Pagamentos");
            System.out.println("6 - Manutenção");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida! Digite um número.");
                continue;
            }

            switch (opcao) {

                case 1:
                    menuMoradores.exibir();
                    break;

                case 2:
                    System.out.println("Menu de Apartamentos - (Pessoa 1)");
                    // TODO: chamar métodos da pessoa 1
                    break;

                case 3:
                    System.out.println("Menu de Visitantes - (Pessoa 3)");
                    // TODO: chamar métodos da pessoa 3
                    break;

                case 4:
                    System.out.println("Menu de Reservas - (Pessoa 2)");
                    // TODO: chamar métodos da pessoa 2
                    break;

                case 5:
                    System.out.println("Menu de Pagamentos - (Pessoa 4)");
                    // TODO: chamar métodos da pessoa 4
                    break;

                case 6:
                    menuManutencao();
                    break;

                case 0:
                    System.out.println("Encerrando sistema...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        }

    }
}