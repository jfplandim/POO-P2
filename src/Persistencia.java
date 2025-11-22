import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Persistencia {

    public static void salvarApartamentos(List<Apartamento> apartamentos, String caminhoArquivo) throws IOException {
        //abrir o arquivo para escrever
        BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo));
        for (Apartamento ap : apartamentos) {
            //lendo a linha dentro do arquivo
            String linha = ap.getNumero() + ";" + ap.getBloco() + ";" + ap.getVagasGaragem();
            //gravar a linha
            bw.write(linha);
            bw.newLine();
        }
        bw.close();
    }

    public static void salvarMoradores(List<Morador> moradores, String caminhoArquivo) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo));
        for (Morador m : moradores) {
            String linha = m.getId() + ";"
                    + m.getNome() + ";"
                    + m.getDocumento() + ";"
                    + m.getTelefone() + ";"
                    + m.getQuantidadePets() + ";"
                    + m.getApartamento().getNumero() + ";"
                    + m.getApartamento().getBloco();
            bw.write(linha);
            bw.newLine();
        }
        bw.close();
    }

    public static List<Apartamento> carregarApartamentos(String caminhoArquivo) throws IOException {
        //lista que sera retornada
        List<Apartamento> apartamentos = new ArrayList<>();
        //abrir o arquivo para ler
        BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo));
        //tenta ler a primeira linha do arquivo
        String linha = br.readLine();
        while (linha != null) {
            String[] partes = linha.split(";");
            //divide a linha
            int numero = Integer.parseInt(partes[0]);
            String bloco = partes[1];
            int vagasGaragem = Integer.parseInt(partes[2]);
            //adicionar o objeto na lista
            Apartamento ap = new Apartamento(numero, bloco, vagasGaragem);
            apartamentos.add(ap);
            linha = br.readLine();
        }
        br.close();
        return apartamentos;
    }

    public static List<Morador> carregarMoradores(String caminhoArquivo, List<Apartamento> apartamentos) throws IOException, CampoInvalidoException {
        List<Morador> moradores = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo));
        String linha = br.readLine();
        while (linha != null) {
            String[] partes = linha.split(";");

            int id = Integer.parseInt(partes[0]);
            String nome = partes[1];
            String documento = partes[2];
            String telefone = partes[3];
            int quantidadePets = Integer.parseInt(partes[4]);
            int numeroApto = Integer.parseInt(partes[5]);
            String blocoApto = partes[6];
            //encontrar o apartamento correto
            Apartamento apartamentoEncontrado = null;
            for (Apartamento ap : apartamentos) {
                if (ap.getNumero() == numeroApto && ap.getBloco().equals(blocoApto)) {
                    apartamentoEncontrado = ap;
                    break;
                }
            }
            //se não encontrar o ap (não deveria acontecer)
            if (apartamentoEncontrado == null) {
                throw new IOException("Apartamento " + numeroApto + "-" + blocoApto +
                        " não encontrado ao carregar moradores.");
            }
            // Criar o morador
            Morador m = new Morador(id, nome, documento, telefone, quantidadePets);
            moradores.add(m);
            linha = br.readLine();

            m.setApartamento(apartamentoEncontrado);
            apartamentoEncontrado.adicionarMorador(m);

            // Adicionar à lista de moradores
            moradores.add(m);

            linha = br.readLine();
        }
        br.close();
        return moradores;
    }
}