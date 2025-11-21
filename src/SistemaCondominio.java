import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SistemaCondominio {

    private List<ChamadoManutencao> chamados;

    public SistemaCondominio() {
        this.chamados = new ArrayList<>();
    }

    public void exibirMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

        do {
            System.out.println("\n=== CONDOMÍNIO VISTA ALEGRE ===");
            System.out.println("1. Gestão de Moradores (Em breve)");
            System.out.println("2. Reservas e Áreas (Em breve)");
            System.out.println("3. Visitantes (Em breve)");
            System.out.println("4. Financeiro (Em breve)");
            System.out.println("5. MANUTENÇÃO");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite apenas números.");
                continue;
            }

            switch (opcao) {
                case 1:
                case 2:
                case 3:
                case 4:
                    System.out.println("Módulo ainda não integrado.");
                    break;
                case 5:
                    menuManutencao(scanner);
                    break;
                case 0:
                    System.out.println("Encerrando sistema...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void menuManutencao(Scanner scanner) {
        System.out.println("\n--- MENU MANUTENÇÃO ---");
        System.out.println("1. Abrir Novo Chamado");
        System.out.println("2. Listar Chamados");
        System.out.println("3. Atender/Fechar Chamado");
        System.out.print("Opção: ");

        int op = 0;
        try {
            op = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return;
        }

        if (op == 1) {
            System.out.print("Área afetada: ");
            String area = scanner.nextLine();
            System.out.print("Descrição do problema: ");
            String desc = scanner.nextLine();

            ChamadoManutencao novo = new ChamadoManutencao(chamados.size() + 1, area, desc);
            chamados.add(novo);
            System.out.println("Chamado aberto com sucesso!");

        } else if (op == 2) {
            System.out.println("\n--- Lista de Chamados ---");
            if (chamados.isEmpty()) {
                System.out.println("Nenhum chamado registrado.");
            } else {
                for (ChamadoManutencao c : chamados) {
                    System.out.println(c);
                }
            }

        } else if (op == 3) {
            System.out.print("Digite o ID do chamado: ");
            int idBusca = Integer.parseInt(scanner.nextLine());

            boolean achou = false;
            for (ChamadoManutencao c : chamados) {
                if (c.getId() == idBusca) {
                    System.out.println("Chamado encontrado: " + c.getDescricao());
                    System.out.println("1. Iniciar Atendimento | 2. Finalizar e Cobrar");
                    int acao = Integer.parseInt(scanner.nextLine());

                    if (acao == 1) {
                        c.iniciarAtendimento();
                    } else if (acao == 2) {
                        System.out.print("Informe o custo final (R$): ");
                        double custo = Double.parseDouble(scanner.nextLine());
                        c.fecharChamado(custo);
                    }
                    achou = true;
                    break;
                }
            }
            if (!achou) System.out.println("Chamado não encontrado.");
        }
    }
}