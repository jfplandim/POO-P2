import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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





    private Morador buscarMoradorPorDocumento(List<Morador> moradores, String documento) {
        for (Morador m : moradores) {
            if (m.getDocumento().equals(documento)) {
                return m;
            }
        }
        return null;
    }

    // ============================================================
    // VERIFICAR E APLICAR MULTAS AUTOMATICAMENTE
    // ============================================================
    public void verificarPagamentosAtrasados() {
        int multasAplicadas = 0;

        for (Pagamento p : pagamentos) {
            if (p.estaAtrasado() && p.getStatus() != Pagamento.Status.atrasado) {
                p.verificarAtraso();
                multasAplicadas++;
            }
        }

        if (multasAplicadas > 0) {
            System.out.println("⚠ " + multasAplicadas + " multa(s) aplicada(s) por atraso.");
        }
    }

    // ============================================================
    // ADICIONAR PAGAMENTO COM VERIFICAÇÃO
    // ============================================================
    public void adicionarPagamento(Pagamento p) {
        pagamentos.add(p);
        p.verificarAtraso(); // Verifica se já está atrasado ao adicionar
    }

    // ============================================================
    // SALVAR COM MULTAS E STATUS CORRETO
    // ============================================================
    public void salvarPagamentosComMultas(String arquivo) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo));

        for (Pagamento p : pagamentos) {
            String linha =
                    p.getValor() + ";" +
                            p.getMorador().getDocumento() + ";" +
                            p.getMorador().getNome() + ";" +
                            p.getStatus() + ";" +
                            p.getId() + ";" +
                            p.getMulta(); //salva a multa

            bw.write(linha);
            bw.newLine();
        }

        bw.close();
        System.out.println("✓ Pagamentos atualizados em " + arquivo);
    }

    // ============================================================
    // CARREGAR COM MULTAS
    // ============================================================
    public void carregarPagamentosComMultas(String arquivo, List<Morador> moradores) throws IOException {
        File f = new File(arquivo);
        if (!f.exists()) {
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
                String nomeIgnorado = partes[2];
                Pagamento.Status status = Pagamento.Status.valueOf(partes[3]);
                int id = Integer.parseInt(partes[4]);
                double multa = (partes.length > 5) ? Double.parseDouble(partes[5]) : 0.0;

                Morador m = buscarMoradorPorDocumento(moradores, documento);

                if (m != null) {
                    Pagamento p = new Pagamento(m, id, valor, status);
                    p.setMulta(multa); //restaura a multa salva
                    pagamentos.add(p);
                    m.getPagamentos().add(p);
                }
            }

            linha = br.readLine();
        }

        br.close();
        System.out.println("✓ Pagamentos carregados: " + pagamentos.size());
    }

    // ============================================================
    // PERSISTÊNCIA DE CHAMADOS
    // ============================================================

    public void salvarChamados(String arquivo) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo));

        for (ChamadoManutencao c : chamados) {
            // Formato: ID;AREA;DESCRICAO;STATUS;CUSTO;DATA_ABERTURA_MS;DATA_FECHAMENTO_MS
            //Usa ponto como separador decimal
            String linha = String.format(Locale.US, "%d;%s;%s;%s;%.2f;%d;%d",
                    c.getId(),
                    c.getAreaAfetada(),
                    c.getDescricao().replace(";", ","), // Remove ; da descrição
                    c.getStatus().name(),
                    c.getCusto(),
                    c.getDataAbertura().getTime(),
                    c.getDataFechamento() != null ? c.getDataFechamento().getTime() : -1
            );
            bw.write(linha);
            bw.newLine();
        }

        bw.close();
        System.out.println("✓ Chamados salvos: " + chamados.size());
    }

    public void carregarChamados(String arquivo) throws IOException {
        File f = new File(arquivo);
        if (!f.exists()) {
            System.out.println("⚠ Arquivo de chamados não encontrado.");
            return;
        }

        chamados.clear();
        int maiorId = 0;

        BufferedReader br = new BufferedReader(new FileReader(f));
        String linha = br.readLine();

        while (linha != null) {
            linha = linha.trim();

            if (!linha.isEmpty()) {
                try {
                    String[] partes = linha.split(";");
                    if (partes.length != 7) {
                        linha = br.readLine();
                        continue;
                    }

                    int id = Integer.parseInt(partes[0]);
                    String area = partes[1];
                    String descricao = partes[2];
                    StatusChamado status = StatusChamado.valueOf(partes[3]);

                    //ubstitui vírgula por ponto antes de converter
                    String custoStr = partes[4].replace(",", ".");
                    double custo = Double.parseDouble(custoStr);

                    long dataAberturaMs = Long.parseLong(partes[5]);
                    long dataFechamentoMs = Long.parseLong(partes[6]);

                    // Cria o chamado e restaura seus dados
                    ChamadoManutencao c = criarChamadoComId(id, area, descricao, status,
                            custo, dataAberturaMs, dataFechamentoMs);
                    chamados.add(c);

                    if (id > maiorId) {
                        maiorId = id;
                    }

                } catch (Exception e) {
                    System.err.println("⚠ Erro ao processar chamado: " + e.getMessage());
                }
            }

            linha = br.readLine();
        }

        br.close();

        // Atualiza o contador de IDs
        ChamadoManutencao.setProximoId(maiorId + 1);

        System.out.println("✓ Chamados carregados: " + chamados.size());
    }

    private ChamadoManutencao criarChamadoComId(int id, String area, String descricao,
                                                StatusChamado status, double custo,
                                                long dataAberturaMs, long dataFechamentoMs) {
        Date dataAbertura = new Date(dataAberturaMs);
        Date dataFechamento = (dataFechamentoMs != -1) ? new Date(dataFechamentoMs) : null;

        //Usa o mtodo estático sem reflexão
        return ChamadoManutencao.restaurarDePersistencia(
                id, area, descricao, status, custo, dataAbertura, dataFechamento
        );
    }
}