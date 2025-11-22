import java.util.List;
import java.util.Scanner;

public class MenuApartamentos {

    private List<Apartamento> apartamentos;

    public MenuApartamentos(List<Apartamento> apartamentos) {
        this.apartamentos = apartamentos;
    }

    public void exibir() {
        Scanner sc = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n===== MENU DE APARTAMENTOS =====");
            System.out.println("1 - Cadastrar Apartamento");
            System.out.println("2 - Listar Apartamentos");
            System.out.println("3 - Buscar Apartamento");
            System.out.println("4 - Listar Moradores de um Apartamento");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            try {
                opcao = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida!");
                continue;
            }

            switch (opcao) {
                case 1 -> cadastrarApartamento();
                case 2 -> listarApartamentos();
                case 3 -> buscarApartamento();
                case 4 -> listarMoradoresPorApartamento();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    //metodos

    private void cadastrarApartamento() {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("\n=== CADASTRAR APARTAMENTO ===");

            System.out.print("Número do apartamento: ");
            int numero = Integer.parseInt(sc.nextLine());

            System.out.print("Bloco: ");
            String bloco = sc.nextLine();

            System.out.print("Vagas de garagem: ");
            int vagas = Integer.parseInt(sc.nextLine());

            Apartamento ap = new Apartamento(numero, bloco, vagas);
            apartamentos.add(ap);

            System.out.println("Apartamento cadastrado com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao cadastrar apartamento: " + e.getMessage());
        }
    }

    private void listarApartamentos() {
        System.out.println("\n=== LISTA DE APARTAMENTOS ===");

        if (apartamentos.isEmpty()) {
            System.out.println("Nenhum apartamento cadastrado.");
            return;
        }

        for (Apartamento a : apartamentos) {
            System.out.println(
                    "Número: " + a.getNumero() +
                            " | Bloco: " + a.getBloco() +
                            " | Vagas: " + a.getVagasGaragem() +
                            " | Moradores: " + a.getMoradores().size()
            );
        }
    }

    private void buscarApartamento() {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n=== BUSCAR APARTAMENTO ===");

        System.out.print("Número do apartamento: ");
        int numero = Integer.parseInt(sc.nextLine());

        System.out.print("Bloco: ");
        String bloco = sc.nextLine();

        for (Apartamento a : apartamentos) {
            if (a.getNumero() == numero && a.getBloco().equalsIgnoreCase(bloco)) {

                System.out.println("Apartamento encontrado!");
                System.out.println("Número: " + a.getNumero());
                System.out.println("Bloco: " + a.getBloco());
                System.out.println("Vagas: " + a.getVagasGaragem());
                System.out.println("Total de moradores: " + a.getMoradores().size());
                return;
            }
        }

        System.out.println("Apartamento não encontrado!");
    }

    private void listarMoradoresPorApartamento() {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n=== MORADORES DE UM APARTAMENTO ===");
        System.out.print("Número do apartamento: ");
        int numero = Integer.parseInt(sc.nextLine());

        System.out.print("Bloco: ");
        String bloco = sc.nextLine();

        for (Apartamento a : apartamentos) {
            if (a.getNumero() == numero && a.getBloco().equalsIgnoreCase(bloco)) {

                System.out.println("\nMoradores do apartamento:");

                if (a.getMoradores().isEmpty()) {
                    System.out.println("Nenhum morador neste apartamento.");
                    return;
                }

                for (Morador m : a.getMoradores()) {
                    System.out.println("ID: " + m.getId() +
                            " | Nome: " + m.getNome() +
                            " | Documento: " + m.getDocumento());
                }
                return;
            }
        }

        System.out.println("Apartamento não encontrado!");
    }
}
