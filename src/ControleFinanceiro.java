import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ControleFinanceiro {

    private List<Pagamento> pagamentos;
    private List<ChamadoManutencao> chamados;

    public ControleFinanceiro() {
        this.pagamentos = new ArrayList<>();
        this.chamados = new ArrayList<>();
    }

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public List<ChamadoManutencao> getChamados() {
        return chamados;
    }

    // ============================================================
    // SALVAR PAGAMENTOS EM TXT (AGORA COM NOME)
    // ============================================================
    public void salvarPagamentosTXT(String arquivo) throws IOException {

        BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo));

        for (Pagamento p : pagamentos) {

            String linha =
                    p.getValor() + ";" +
                            p.getMorador().getDocumento() + ";" +
                            p.getMorador().getNome() + ";" +        // <--- NOME ADICIONADO
                            p.getStatus() + ";" +
                            p.getId();

            bw.write(linha);
            bw.newLine();
        }

        bw.close();
        System.out.println("✓ Pagamentos salvos em " + arquivo);
    }

    // ============================================================
    // CARREGAR PAGAMENTOS DO TXT (LENDO O NOME TBM)
    // ============================================================
    public void carregarPagamentosTXT(String arquivo, List<Morador> moradores) throws IOException {

        File f = new File(arquivo);
        if (!f.exists()) {
            System.out.println("Nenhum arquivo de pagamentos encontrado.");
            return;
        }

        BufferedReader br = new BufferedReader(new FileReader(f));
        String linha = br.readLine();

        while (linha != null) {

            linha = linha.trim();

            if (!linha.isEmpty()) {

                String[] partes = linha.split(";");

                double valor = Double.parseDouble(partes[0]);
                String documento = partes[1];
                String nomeIgnorado = partes[2];            // <--- NOME LIDO MAS NÃO USADO NA LÓGICA
                Pagamento.Status status = Pagamento.Status.valueOf(partes[3]);
                int id = Integer.parseInt(partes[4]);

                Morador m = buscarMoradorPorDocumento(moradores, documento);

                if (m != null) {
                    Pagamento p = new Pagamento(m, id, valor, status);
                    m.getPagamentos().add(p);
                    pagamentos.add(p);
                }
            }

            linha = br.readLine();
        }

        br.close();
        System.out.println("✓ Pagamentos carregados: " + pagamentos.size());
    }

    private Morador buscarMoradorPorDocumento(List<Morador> moradores, String documento) {
        for (Morador m : moradores) {
            if (m.getDocumento().equals(documento)) {
                return m;
            }
        }
        return null;
    }

    // ============================================================
    // RELATÓRIO FINANCEIRO
    // ============================================================
    public void gerarRelatorioFinanceiroTXT(String arquivo) throws IOException {

        BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo));

        bw.write("======= RELATÓRIO FINANCEIRO =======\n\n");

        double total = 0;

        for (Pagamento p : pagamentos) {

            bw.write("-----------------------------------\n");
            bw.write("ID: " + p.getId() + "\n");
            bw.write("Morador: " + p.getMorador().getNome() + "\n");  // <--- NOME
            bw.write("Documento: " + p.getMorador().getDocumento() + "\n");
            bw.write("Valor: R$ " + p.getValor() + "\n");
            bw.write("Status: " + p.getStatus() + "\n");

            total += p.getValor();
        }

        bw.write("\nTOTAL GERAL: R$ " + total + "\n");

        bw.close();
        System.out.println("✓ Relatório financeiro salvo em " + arquivo);
    }
}