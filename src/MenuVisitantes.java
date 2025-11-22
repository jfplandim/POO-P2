import java.util.List;
import java.util.Scanner;

public class MenuVisitantes {

    private List<Visitante> visitantes;
    private List<Morador> moradores;

    public MenuVisitantes(List<Visitante> visitantes, List<Morador> moradores) {
        this.visitantes = visitantes;
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
                System.out.println("Morador não encontrado!");
                return;
            }

            Visitante v = new Visitante(nome, doc, tel, moradorVisitado);

            visitantes.add(v);

            System.out.println("Entrada registrada com sucesso!");
            System.out.println("Data/Hora de entrada: " + v.getDataHoraEntrada());

        } catch (Exception e) {
            System.out.println("Erro ao registrar entrada: " + e.getMessage());
        }
    }

    private void registrarSaida() {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n=== REGISTRAR SAÍDA ===");

        System.out.print("Documento do visitante: ");
        String doc = sc.nextLine();

        Visitante visitante = null;
        for (Visitante v : visitantes) {
            if (v.getDocumento().equalsIgnoreCase(doc)) {
                visitante = v;
                break;
            }
        }

        if (visitante == null) {
            System.out.println("Visitante não encontrado!");
            return;
        }

        try {
            visitante.registrarSaida();
            System.out.println("Saída registrada com sucesso!");
            System.out.println("Data/Hora de saída: " + visitante.getDataHoraSaida());

        } catch (Exception e) {
            System.out.println("Erro ao registrar saída: " + e.getMessage());
        }
    }

    private void listarVisitantes() {
        System.out.println("\n=== LISTA DE VISITANTES ===");

        if (visitantes.isEmpty()) {
            System.out.println("Nenhum visitante registrado.");
            return;
        }

        for (Visitante v : visitantes) {
            System.out.println("-----------------------------------------");
            System.out.println("Nome: " + v.getNome());
            System.out.println("Documento: " + v.getDocumento());
            System.out.println("Morador visitado: " + v.getMoradorVisitado().getNome());
            System.out.println("Entrada: " + v.getDataHoraEntrada());
            System.out.println("Saída: " + (v.getDataHoraSaida() != null ?
                    v.getDataHoraSaida() : "Ainda no condomínio"));
        }
    }
}
