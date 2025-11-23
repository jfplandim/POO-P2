import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RelatorioFinanceiro {

    private ControleFinanceiro controleFinanceiro;

    public RelatorioFinanceiro(ControleFinanceiro controleFinanceiro) {
        this.controleFinanceiro = controleFinanceiro;
    }


     //Gera relatório financeiro completo no console

    public void gerarRelatorioConsole() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║     RELATÓRIO FINANCEIRO DO CONDOMÍNIO        ║");
        System.out.println("╚════════════════════════════════════════════════╝");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        System.out.println("Data de geração: " + sdf.format(new Date()));

        // Receitas
        double totalRecebido = calcularTotalRecebido();
        double totalPendente = calcularTotalPendente();
        double totalAtrasado = calcularTotalAtrasado();
        double totalMultas = calcularTotalMultas();

        System.out.println("\n┌─── RECEITAS ───────────────────────────────┐");
        System.out.println("│ Pagamentos recebidos: R$ " + String.format("%10.2f", totalRecebido) + "   │");
        System.out.println("│ Pagamentos pendentes: R$ " + String.format("%10.2f", totalPendente) + "   │");
        System.out.println("│ Pagamentos atrasados: R$ " + String.format("%10.2f", totalAtrasado) + "   │");
        System.out.println("│ Total em multas:      R$ " + String.format("%10.2f", totalMultas) + "   │");
        System.out.println("└────────────────────────────────────────────┘");

        // Despesas
        double totalManutencao = calcularTotalManutencao();

        System.out.println("\n┌─── DESPESAS ───────────────────────────────┐");
        System.out.println("│ Gastos com manutenção: R$ " + String.format("%9.2f", totalManutencao) + "   │");
        System.out.println("└────────────────────────────────────────────┘");

        // Balanço
        double saldo = totalRecebido - totalManutencao;

        System.out.println("\n┌─── BALANÇO ────────────────────────────────┐");
        System.out.println("│ Saldo atual:          R$ " + String.format("%10.2f", saldo) + "   │");
        System.out.println("└────────────────────────────────────────────┘");

        // Inadimplência
        int totalMoradores = contarMoradoresUnicos();
        int moradoresInadimplentes = contarMoradoresInadimplentes();
        double taxaInadimplencia = totalMoradores > 0 ?
                (moradoresInadimplentes * 100.0 / totalMoradores) : 0;

        System.out.println("\n┌─── INADIMPLÊNCIA ──────────────────────────┐");
        System.out.println("│ Total de moradores:        " + String.format("%3d", totalMoradores) + "          │");
        System.out.println("│ Moradores inadimplentes:   " + String.format("%3d", moradoresInadimplentes) + "          │");
        System.out.println("│ Taxa de inadimplência:   " + String.format("%5.2f%%", taxaInadimplencia) + "        │");
        System.out.println("└────────────────────────────────────────────┘");

        // Detalhamento de chamados
        System.out.println("\n┌─── CHAMADOS DE MANUTENÇÃO ─────────────────┐");
        int totalChamados = controleFinanceiro.getChamados().size();
        long abertos = controleFinanceiro.getChamados().stream()
                .filter(c -> c.getStatus() == StatusChamado.ABERTO)
                .count();
        long emAndamento = controleFinanceiro.getChamados().stream()
                .filter(c -> c.getStatus() == StatusChamado.EM_ANDAMENTO)
                .count();
        long fechados = controleFinanceiro.getChamados().stream()
                .filter(c -> c.getStatus() == StatusChamado.FECHADO)
                .count();

        System.out.println("│ Total de chamados:         " + String.format("%3d", totalChamados) + "          │");
        System.out.println("│ Abertos:                   " + String.format("%3d", abertos) + "          │");
        System.out.println("│ Em andamento:              " + String.format("%3d", emAndamento) + "          │");
        System.out.println("│ Fechados:                  " + String.format("%3d", fechados) + "          │");
        System.out.println("└────────────────────────────────────────────┘");

        System.out.println("\n════════════════════════════════════════════════\n");
    }


     //Salva relatório em arquivo TXT

    public void salvarRelatorioTXT(String arquivo) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        bw.write("=================================================\n");
        bw.write("    RELATÓRIO FINANCEIRO DO CONDOMÍNIO\n");
        bw.write("    Condomínio Vista Alegre\n");
        bw.write("=================================================\n");
        bw.write("Data: " + sdf.format(new Date()) + "\n\n");

        // Receitas
        bw.write("--- RECEITAS ---\n");
        bw.write(String.format("Recebidos:  R$ %10.2f\n", calcularTotalRecebido()));
        bw.write(String.format("Pendentes:  R$ %10.2f\n", calcularTotalPendente()));
        bw.write(String.format("Atrasados:  R$ %10.2f\n", calcularTotalAtrasado()));
        bw.write(String.format("Multas:     R$ %10.2f\n", calcularTotalMultas()));
        double totalReceitas = calcularTotalRecebido() + calcularTotalPendente() + calcularTotalAtrasado();
        bw.write(String.format("TOTAL:      R$ %10.2f\n\n", totalReceitas));

        // Despesas
        bw.write("--- DESPESAS ---\n");
        bw.write(String.format("Manutenção: R$ %10.2f\n\n", calcularTotalManutencao()));

        // Balanço
        double saldo = calcularTotalRecebido() - calcularTotalManutencao();
        bw.write("--- BALANÇO ---\n");
        bw.write(String.format("Saldo:      R$ %10.2f\n\n", saldo));

        // Inadimplência
        int totalMoradores = contarMoradoresUnicos();
        int moradoresInadimplentes = contarMoradoresInadimplentes();
        double taxaInadimplencia = totalMoradores > 0 ?
                (moradoresInadimplentes * 100.0 / totalMoradores) : 0;

        bw.write("--- INADIMPLÊNCIA ---\n");
        bw.write(String.format("Total de moradores:      %3d\n", totalMoradores));
        bw.write(String.format("Moradores inadimplentes: %3d\n", moradoresInadimplentes));
        bw.write(String.format("Taxa de inadimplência:   %.2f%%\n\n", taxaInadimplencia));

        // Detalhamento de chamados fechados
        bw.write("--- CHAMADOS DE MANUTENÇÃO (FECHADOS) ---\n");
        boolean temChamados = false;
        for (ChamadoManutencao chamado : controleFinanceiro.getChamados()) {
            if (chamado.getStatus() == StatusChamado.FECHADO) {
                bw.write(String.format("ID %d - %s: R$ %.2f\n",
                        chamado.getId(),
                        chamado.getAreaAfetada(),
                        chamado.getCusto()));
                temChamados = true;
            }
        }
        if (!temChamados) {
            bw.write("Nenhum chamado fechado.\n");
        }

        // Lista de inadimplentes
        bw.write("\n--- LISTA DE INADIMPLENTES ---\n");
        boolean temInadimplente = false;
        for (Pagamento p : controleFinanceiro.getPagamentos()) {
            if (p.getStatus() == Pagamento.Status.atrasado) {
                bw.write(String.format("• %s (Doc: %s) - R$ %.2f (Multa: R$ %.2f)\n",
                        p.getMorador().getNome(),
                        p.getMorador().getDocumento(),
                        p.getValor(),
                        p.getMulta()));
                temInadimplente = true;
            }
        }
        if (!temInadimplente) {
            bw.write("Nenhum inadimplente.\n");
        }

        bw.write("\n=================================================\n");
        bw.write("Relatório gerado automaticamente pelo sistema.\n");
        bw.write("=================================================\n");
        bw.close();

        System.out.println("✓ Relatório salvo em: " + arquivo);
    }

    // ============================================================
    // MÉTODOS AUXILIARES DE CÁLCULO
    // ============================================================

    private double calcularTotalRecebido() {
        return controleFinanceiro.getPagamentos().stream()
                .filter(p -> p.getStatus() == Pagamento.Status.pago)
                .mapToDouble(Pagamento::getValorTotal)
                .sum();
    }

    private double calcularTotalPendente() {
        return controleFinanceiro.getPagamentos().stream()
                .filter(p -> p.getStatus() == Pagamento.Status.pendente)
                .mapToDouble(Pagamento::getValor)
                .sum();
    }

    private double calcularTotalAtrasado() {
        return controleFinanceiro.getPagamentos().stream()
                .filter(p -> p.getStatus() == Pagamento.Status.atrasado)
                .mapToDouble(Pagamento::getValorTotal) // Inclui multa
                .sum();
    }

    private double calcularTotalMultas() {
        return controleFinanceiro.getPagamentos().stream()
                .mapToDouble(Pagamento::getMulta)
                .sum();
    }

    private double calcularTotalManutencao() {
        return controleFinanceiro.getChamados().stream()
                .filter(c -> c.getStatus() == StatusChamado.FECHADO)
                .mapToDouble(ChamadoManutencao::getCusto)
                .sum();
    }

    private int contarMoradoresUnicos() {
        return (int) controleFinanceiro.getPagamentos().stream()
                .map(p -> p.getMorador().getDocumento())
                .distinct()
                .count();
    }

    private int contarMoradoresInadimplentes() {
        return (int) controleFinanceiro.getPagamentos().stream()
                .filter(p -> p.getStatus() == Pagamento.Status.atrasado)
                .map(p -> p.getMorador().getDocumento())
                .distinct()
                .count();
    }
}