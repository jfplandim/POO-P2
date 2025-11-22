import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Persistencia {

    public static void salvarApartamentos(List<Apartamento> apartamentos, String caminhoArquivo) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo));
        for (Apartamento ap : apartamentos) {
            String linha = ap.getNumero() + ";" + ap.getBloco() + ";" + ap.getVagasGaragem();
            bw.write(linha);
            bw.newLine();
        }
        bw.close();
        System.out.println("✓ " + apartamentos.size() + " apartamentos salvos em " + caminhoArquivo);
    }

    public static void salvarMoradores(List<Morador> moradores, String caminhoArquivo) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo));
        int contador = 0;
        for (Morador m : moradores) {
            // Verifica se o morador tem apartamento antes de salvar
            if (m.getApartamento() != null) {
                String linha = m.getId() + ";"
                        + m.getNome() + ";"
                        + m.getDocumento() + ";"
                        + m.getTelefone() + ";"
                        + m.getQuantidadePets() + ";"
                        + m.getApartamento().getNumero() + ";"
                        + m.getApartamento().getBloco();
                bw.write(linha);
                bw.newLine();
                contador++;
            }
        }
        bw.close();
        System.out.println("✓ " + contador + " moradores salvos em " + caminhoArquivo);
    }

    public static List<Apartamento> carregarApartamentos(String caminhoArquivo) throws IOException {
        List<Apartamento> apartamentos = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo));
        String linha = br.readLine();

        while (linha != null) {
            linha = linha.trim(); // Remove espaços extras
            if (!linha.isEmpty()) { // Ignora linhas vazias
                String[] partes = linha.split(";");
                int numero = Integer.parseInt(partes[0].trim());
                String bloco = partes[1].trim();
                int vagasGaragem = Integer.parseInt(partes[2].trim());

                Apartamento ap = new Apartamento(numero, bloco, vagasGaragem);
                apartamentos.add(ap);
            }
            linha = br.readLine();
        }
        br.close();
        System.out.println("✓ " + apartamentos.size() + " apartamentos lidos de " + caminhoArquivo);
        return apartamentos;
    }

    public static List<Morador> carregarMoradores(String caminhoArquivo, List<Apartamento> apartamentos) throws IOException, CampoInvalidoException {
        List<Morador> moradores = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo));
        String linha = br.readLine();
        int linhaNumero = 0;

        while (linha != null) {
            linhaNumero++;
            linha = linha.trim(); // Remove espaços extras

            if (!linha.isEmpty()) { // Ignora linhas vazias
                try {
                    String[] partes = linha.split(";");

                    if (partes.length != 7) {
                        System.err.println("⚠ Linha " + linhaNumero + " com formato incorreto (esperado 7 campos, encontrado " + partes.length + ")");
                        linha = br.readLine();
                        continue;
                    }

                    int id = Integer.parseInt(partes[0].trim());
                    String nome = partes[1].trim();
                    String documento = partes[2].trim();
                    String telefone = partes[3].trim();
                    int quantidadePets = Integer.parseInt(partes[4].trim());
                    int numeroApto = Integer.parseInt(partes[5].trim());
                    String blocoApto = partes[6].trim();

                    // Encontrar o apartamento correto
                    Apartamento apartamentoEncontrado = null;
                    for (Apartamento ap : apartamentos) {
                        if (ap.getNumero() == numeroApto && ap.getBloco().equals(blocoApto)) {
                            apartamentoEncontrado = ap;
                            break;
                        }
                    }

                    if (apartamentoEncontrado == null) {
                        System.err.println("⚠ Linha " + linhaNumero + ": Apartamento " + numeroApto + "-" + blocoApto + " não encontrado");
                        linha = br.readLine();
                        continue;
                    }

                    // Criar o morador
                    Morador m = new Morador(id, nome, documento, telefone, quantidadePets);

                    // Associar morador ao apartamento
                    m.setApartamento(apartamentoEncontrado);
                    boolean adicionado = apartamentoEncontrado.adicionarMorador(m);

                    if (!adicionado) {
                        System.err.println("⚠ Linha " + linhaNumero + ": Não foi possível adicionar morador " + nome + " ao apartamento");
                    }

                    // Adicionar à lista de moradores
                    moradores.add(m);

                } catch (NumberFormatException e) {
                    System.err.println("⚠ Linha " + linhaNumero + ": Erro ao converter números - " + e.getMessage());
                } catch (CampoInvalidoException e) {
                    System.err.println("⚠ Linha " + linhaNumero + ": " + e.getMessage());
                }
            }
            linha = br.readLine();
        }
        br.close();
        System.out.println("✓ " + moradores.size() + " moradores lidos de " + caminhoArquivo);
        return moradores;
    }
}