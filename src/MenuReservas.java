import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MenuReservas {

    private GerenciadorReservas gerenciador;

    public MenuReservas(GerenciadorReservas gerenciador) {
        this.gerenciador = gerenciador;
        gerenciador.carregarReservas(); // ✅ Carrega automaticamente
    }

    public void exibir() {
        Scanner sc = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n===== MENU DE RESERVAS =====");
            System.out.println("1 - Criar Reserva");
            System.out.println("2 - Listar Reservas");
            System.out.println("3 - Cancelar Reserva");
            System.out.println("4 - Salvar Reservas");  // ✅ NOVO
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            try {
                opcao = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Entrada inválida!");
                continue;
            }

            switch (opcao) {
                case 1 -> criarReserva();
                case 2 -> listarReservas();
                case 3 -> cancelarReserva();
                case 4 -> gerenciador.salvarReservas();  // ✅ NOVO
                case 0 -> {
                    gerenciador.salvarReservas();  // ✅ Salva ao sair
                    System.out.println("Voltando...");
                }
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private void criarReserva() {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("\n=== CRIAR RESERVA ===");
            System.out.println("\n=== ESCOLHA A ÁREA ===");
            System.out.println("1 - Academia");
            System.out.println("2 - Piscina");
            System.out.println("3 - Salão de Festas");
            System.out.print("Opção: ");
            int opcaoArea = Integer.parseInt(sc.nextLine());

            AreaReservavel area;
            switch (opcaoArea) {
                case 1:
                    area = AreaReservavel.ACADEMIA;
                    break;
                case 2:
                    area = AreaReservavel.PISCINA;
                    break;
                case 3:
                    area = AreaReservavel.SALAO_FESTAS;
                    break;
                default:
                    System.out.println("Opção inválida!");
                    return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            System.out.print("Data e hora de início (dd/MM/yyyy HH:mm): ");
            String dataStr = sc.nextLine();
            Date dataInicio = sdf.parse(dataStr);

            System.out.print("Duração em horas (1 a 8): ");
            int duracao = Integer.parseInt(sc.nextLine());

            System.out.print("Responsável pela reserva: ");
            String responsavel = sc.nextLine();

            // USA O GERENCIADOR PARA ADICIONAR A RESERVA (com validação de conflitos)
            boolean sucesso = gerenciador.adicionarReserva(dataInicio, duracao, responsavel, area);

            if (sucesso) {
                System.out.println("\n✓ Reserva criada com sucesso!");
                System.out.println("Área: " + area.getNome());
                System.out.println("Início: " + sdf.format(dataInicio));
            }

        } catch (ParseException e) {
            System.out.println("Formato de data inválido!");
        } catch (Exception e) {
            System.out.println("Erro ao criar reserva: " + e.getMessage());
        }
    }

    private void listarReservas() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        System.out.println("\n=== LISTA DE RESERVAS ===");

        List<Reserva> reservasAtivas = gerenciador.listarReservasAtivas();

        if (reservasAtivas.isEmpty()) {
            System.out.println("Nenhuma reserva ativa cadastrada.");
            return;
        }

        for (Reserva r : reservasAtivas) {
            System.out.println("-------------------------------------");
            System.out.println("ID: " + r.getId());
            System.out.println("Área: " + r.getArea().getNome());
            System.out.println("Responsável: " + r.getResponsavel());
            System.out.println("Início: " + sdf.format(r.getDataHoraInicio()));
            System.out.println("Fim: " + sdf.format(r.getDataHoraFim()));
            System.out.println("Duração: " + r.getDuracaoHoras() + "h");
        }
    }

    private void cancelarReserva() {
        Scanner sc = new Scanner(System.in);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        System.out.println("\n=== CANCELAR RESERVA ===");

        System.out.print("Digite o nome do responsável: ");
        String responsavel = sc.nextLine();

        // Busca reservas do responsável
        List<Reserva> reservasDoResponsavel = gerenciador.listarReservasPorResponsavel(responsavel);

        if (reservasDoResponsavel.isEmpty()) {
            System.out.println("Nenhuma reserva encontrada para o responsável: " + responsavel);
            return;
        }

        // Exibe as reservas do responsável
        System.out.println("\n=== RESERVAS DE " + responsavel.toUpperCase() + " ===");
        for (Reserva r : reservasDoResponsavel) {
            System.out.println("-------------------------------------");
            System.out.println("ID: " + r.getId());
            System.out.println("Área: " + r.getArea().getNome());
            System.out.println("Início: " + sdf.format(r.getDataHoraInicio()));
            System.out.println("Fim: " + sdf.format(r.getDataHoraFim()));
            System.out.println("Duração: " + r.getDuracaoHoras() + "h");
        }

        // Permite escolher qual cancelar
        System.out.print("\nDigite o ID da reserva que deseja cancelar: ");
        try {
            int idEscolhido = Integer.parseInt(sc.nextLine());

            boolean cancelada = gerenciador.cancelarReserva(idEscolhido);

            if (cancelada) {
                System.out.println("✓ Reserva cancelada com sucesso!");
            }

        } catch (NumberFormatException e) {
            System.out.println("ID inválido!");
        }
    }
}
