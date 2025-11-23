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
    // SALVAR AGORA SALVA TODA A LISTA (COM OS ANTIGOS + NOVOS)
    // ============================================================
    public void salvarPagamentosTXT(String arquivo) throws IOException {

        BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo));

        for (Pagamento p : pagamentos) {
            String linha =
                    p.getValor() + ";" +
                            p.getMorador().getDocumento() + ";" +
                            p.getMorador().getNome() + ";" +
                            p.getStatus() + ";" +
                            p.getId();

            bw.write(linha);
            bw.newLine();
        }

        bw.close();
        System.out.println("✓ Pagamentos atualizados em " + arquivo);
    }

    // ============================================================
    // CARREGA TODOS OS PAGAMENTOS QUE JÁ EXISTEM NO TXT
    // ============================================================
    public void carregarPagamentosTXT(String arquivo, List<Morador> moradores) throws IOException {

        File f = new File(arquivo);
        if (!f.exists()) {
            return; // não existe → primeira execução
        }

        BufferedReader br = new BufferedReader(new FileReader(f));
        String linha = br.readLine();

        while (linha != null) {
            linha = linha.trim();

            if (!linha.isEmpty()) {

                String[] partes = linha.split(";");

                double valor = Double.parseDouble(partes[0]);
                String documento = partes[1];
                String nomeIgnorado = partes[2];
                Pagamento.Status status = Pagamento.Status.valueOf(partes[3]);
                int id = Integer.parseInt(partes[4]);

                Morador m = buscarMoradorPorDocumento(moradores, documento);

                if (m != null) {
                    Pagamento p = new Pagamento(m, id, valor, status);
                    pagamentos.add(p);
                    m.getPagamentos().add(p);
                }
            }

            linha = br.readLine();
        }

        br.close();
        System.out.println("✓ Pagamentos carregados do arquivo: " + pagamentos.size());
    }

    private Morador buscarMoradorPorDocumento(List<Morador> moradores, String documento) {
        for (Morador m : moradores) {
            if (m.getDocumento().equals(documento)) {
                return m;
            }
        }
        return null;
    }
}