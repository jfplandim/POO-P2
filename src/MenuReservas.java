import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MenuReservas {

    private List<Reserva> reservas;

    public MenuReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public void exibir() {
        Scanner sc = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n===== MENU DE RESERVAS =====");
            System.out.println("1 - Criar Reserva");
            System.out.println("2 - Listar Reservas");
            System.out.println("3 - Cancelar Reserva");
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
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    //metodos
    private void criarReserva() {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("\n=== CRIAR RESERVA ===");

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            System.out.print("Data e hora de início (dd/MM/yyyy HH:mm): ");
            String dataStr = sc.nextLine();
            Date dataInicio = sdf.parse(dataStr);

            System.out.print("Duração em horas (1 a 8): ");
            int duracao = Integer.parseInt(sc.nextLine());

            System.out.print("Responsável pela reserva: ");
            String responsavel = sc.nextLine();

            // CHAMA O CONSTRUTOR DA CLASSE RESERVA
            Reserva r = new Reserva(dataInicio, duracao, responsavel);
            reservas.add(r);

            System.out.println("\nReserva criada com sucesso!");
            System.out.println("Início: " + sdf.format(r.getDataHoraInicio()));
            System.out.println("Fim: " + sdf.format(r.getDataHoraFim()));

        } catch (ParseException e) {
            System.out.println("Formato de data inválido!");
        } catch (Exception e) {
            System.out.println("Erro ao criar reserva: " + e.getMessage());
        }
    }


    private void listarReservas() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        System.out.println("\n=== LISTA DE RESERVAS ===");

        if (reservas.isEmpty()) {
            System.out.println("Nenhuma reserva cadastrada.");
            return;
        }

        for (Reserva r : reservas) {
            System.out.println("-------------------------------------");
            System.out.println("Responsável: " + r.getResponsavel());
            System.out.println("Início: " + sdf.format(r.getDataHoraInicio()));
            System.out.println("Fim: " + sdf.format(r.getDataHoraFim()));
            System.out.println("Duração: " + r.getDuracaoHoras() + "h");
            System.out.println("Status: " + (r.isCancelada() ? "Cancelada" : "Ativa"));
        }
    }


    private void cancelarReserva() {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n=== CANCELAR RESERVA ===");

        System.out.print("Responsável da reserva: ");
        String resp = sc.nextLine();

        Reserva reservaEncontrada = null;

        for (Reserva r : reservas) {
            if (r.getResponsavel().equalsIgnoreCase(resp)) {
                reservaEncontrada = r;
                break;
            }
        }

        if (reservaEncontrada == null) {
            System.out.println("Reserva não encontrada.");
            return;
        }

        try {
            // 🌟 AQUI É ONDE USAMOS O MÉTODO DA CLASSE RESERVA
            reservaEncontrada.cancelar(new Date());
            System.out.println("Reserva cancelada com sucesso!");

        } catch (CampoInvalidoException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
