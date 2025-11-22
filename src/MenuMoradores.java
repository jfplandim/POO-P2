import java.util.List;
import java.util.Scanner;

public class MenuMoradores {

    private List<Morador> moradores;
    private List<Apartamento> apartamentos;

    public MenuMoradores(List<Morador> moradores, List<Apartamento> apartamentos) {
        this.moradores = moradores;
        this.apartamentos = apartamentos;
    }

    public void exibir() {
        Scanner sc = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n===== MENU DE MORADORES =====");
            System.out.println("1 - Cadastrar Morador");
            System.out.println("2 - Listar Moradores");
            System.out.println("3 - Buscar Morador por Documento");
            System.out.println("4 - Associar Morador a Apartamento");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            try {
                opcao = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida!");
                continue;
            }

            switch (opcao) {
                case 1 -> cadastrarMorador();
                case 2 -> listarMoradores();
                case 3 -> buscarMoradorDocumento();
                case 4 -> associarMoradorApartamento();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    //metodos
    private void cadastrarMorador() {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("\n=== CADASTRAR MORADOR ===");

            System.out.print("ID do morador: ");
            int id = Integer.parseInt(sc.nextLine());

            System.out.print("Nome: ");
            String nome = sc.nextLine();

            System.out.print("Documento: ");
            String doc = sc.nextLine();

            System.out.print("Telefone: ");
            String tel = sc.nextLine();

            System.out.print("Quantidade de pets: ");
            int pets = Integer.parseInt(sc.nextLine());

            Morador m = new Morador(id, nome, doc, tel, pets);

            moradores.add(m);

            System.out.println("Morador cadastrado com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao cadastrar morador: " + e.getMessage());
        }
    }

    private void listarMoradores() {
        System.out.println("\n=== LISTA DE MORADORES ===");

        if (moradores.isEmpty()) {
            System.out.println("Nenhum morador cadastrado.");
            return;
        }

        for (Morador m : moradores) {
            System.out.println("ID: " + m.getId() +
                    " | Nome: " + m.getNome() +
                    " | Documento: " + m.getDocumento() +
                    " | Apto: " +
                    (m.getApartamento() != null ? m.getApartamento().getNumero() : "Nenhum"));
        }
    }

    private void buscarMoradorDocumento() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Digite o documento do morador: ");
        String doc = sc.nextLine();

        for (Morador m : moradores) {
            if (m.getDocumento().equalsIgnoreCase(doc)) {

                System.out.println("Morador encontrado:");
                System.out.println("ID: " + m.getId());
                System.out.println("Nome: " + m.getNome());
                System.out.println("Telefone: " + m.getTelefone());
                System.out.println("Pets: " + m.getQuantidadePets());
                System.out.println("Apartamento: " +
                        (m.getApartamento() != null ? m.getApartamento().getNumero() : "Nenhum"));

                return;
            }
        }

        System.out.println("Morador não encontrado.");
    }

    private void associarMoradorApartamento() {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n=== ASSOCIAR MORADOR A APARTAMENTO ===");

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

        System.out.print("Número do apartamento: ");
        int numero = Integer.parseInt(sc.nextLine());

        System.out.print("Bloco do apartamento: ");
        String bloco = sc.nextLine();

        Apartamento apt = null;
        for (Apartamento a : apartamentos) {
            if (a.getNumero() == numero && a.getBloco().equalsIgnoreCase(bloco)) {
                apt = a;
                break;
            }
        }

        if (apt == null) {
            System.out.println("Apartamento não encontrado!");
            return;
        }

        if (apt.adicionarMorador(morador)) {
            morador.setApartamento(apt);
            System.out.println("Morador associado ao apartamento com sucesso!");
        } else {
            System.out.println("Não foi possível associar o morador ao apartamento.");
        }
    }
}
