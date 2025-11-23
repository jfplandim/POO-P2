import java.util.List;
import java.util.Scanner;

public class MenuVisitantes {

    private ControleVisitante controleVisitante;
    private List<Morador> moradores;

    public MenuVisitantes(ControleVisitante controleVisitante, List<Morador> moradores) {
        this.controleVisitante = controleVisitante;
        this.moradores = moradores;
    }

    public void exibir() {
        Scanner sc = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n===== MENU DE VISITANTES =====");
            System.out.println("1 - Registrar Entrada de Visitante");
            System.out.println("2 - Registrar Saída de Visitante");
            System.out.println("3 - Listar Visitantes");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            try {
                opcao = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Entrada inválida!");
                continue;
            }

            switch (opcao) {
                case 1 -> registrarEntrada();
                case 2 -> registrarSaida();
                case 3 -> listarVisitantes();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    //metodos

    private void registrarEntrada() {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("\n=== REGISTRAR ENTRADA ===");

            System.out.print("Nome do visitante: ");
            String nome = sc.nextLine();

            System.out.print("Documento do visitante: ");
            String doc = sc.nextLine();

            System.out.print("Telefone: ");
            String tel = sc.nextLine();

            System.out.print("Documento do morador visitado: ");
            String docMorador = sc.nextLine();

            Morador moradorVisitado = null;
            for (Morador m : moradores) {
                if (m.getDocumento().equalsIgnoreCase(docMorador)) {
                    moradorVisitado = m;
                    break;
                }
            }

            if (moradorVisitado == null) {
                System.out.println("✗ Morador não encontrado!");
                return;
            }

            Visitante v = new Visitante(nome, doc, tel, moradorVisitado);
            controleVisitante.registrarEntrada(v);

            System.out.println("Data/Hora de entrada: " + v.getDataHoraEntrada());

        } catch (Exception e) {
            System.out.println("✗ Erro ao registrar entrada: " + e.getMessage());
        }
    }

    private void registrarSaida() {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n=== REGISTRAR SAÍDA ===");

        System.out.print("Documento do visitante: ");
        String doc = sc.nextLine();

        controleVisitante.registrarSaida(doc);

    }

    private void listarVisitantes() {
       controleVisitante.gerarRelatorioVisitas();
    }
}
