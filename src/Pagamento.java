import java.io.Serializable;
import java.time.ZoneId;
import java.time.LocalDate;
import java.util.Date;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class Pagamento implements Serializable {
    private final int id;
    private double valor;
    private Date dataPagamento;
    private final Date dataVencimento;
    private Status status;
    private final Morador morador;
    private double multa;

    // Percentual de multa por atraso (2% por exemplo)
    private static final double PERCENTUAL_MULTA = 0.02;

    public enum Status {
        pago,
        pendente,
        atrasado,
        cancelado
    }


     //Construtor para criar novo pagamento
     // A data de vencimento é definida para o dia 10 do mês seguinte

    public Pagamento(Morador morador, int id, Double valor, Status status) {
        this.morador = morador;
        this.id = id;
        this.valor = valor;
        this.status = status; // ✅ CORRIGIDO: agora usa o status passado
        this.dataPagamento = null;
        this.multa = 0.0;

        //Define data de vencimento (dia 10 do próximo mês)
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1); // próximo mês
        cal.set(Calendar.DAY_OF_MONTH, 10); // dia 10
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        this.dataVencimento = cal.getTime();
    }


     //Aplica multa ao pagamento atrasado

    public void aplicarMulta() {
        if (this.multa == 0.0) { // Aplica multa apenas uma vez
            this.multa = this.valor * PERCENTUAL_MULTA;
            System.out.println("⚠ Multa de R$ " + String.format("%.2f", this.multa) +
                    " aplicada ao pagamento ID " + this.id);
        }
    }


     //Verifica se o pagamento está atrasado e atualiza o status

    public void verificarAtraso() {
        if (!recebido() && estaAtrasado()) {
            if (this.status != Status.atrasado) {
                this.status = Status.atrasado;
                aplicarMulta();
            }
        }
    }


      //Verifica se está atrasado (passou da data de vencimento)

    public boolean estaAtrasado() {
        Date hoje = new Date();
        return !recebido() && dataVencimento.before(hoje);
    }


      //Verifica se o pagamento foi recebido

    public boolean recebido() {
        return dataPagamento != null;
    }


      //Registra o pagamento

    public void registrarPagamento() throws CampoInvalidoException {
        if (recebido()) {
            throw new CampoInvalidoException("Pagamento já foi registrado anteriormente.");
        }
        this.dataPagamento = new Date();
        this.status = Status.pago;
        System.out.println("✓ Pagamento ID " + this.id + " registrado com sucesso!");
    }


     //Retorna o valor total (valor original + multa)

    public double getValorTotal() {
        return this.valor + this.multa;
    }

    // Getters
    public int getId() {
        return id;
    }

    public double getValor() {
        return valor;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public Status getStatus() {
        return status;
    }

    public Morador getMorador() {
        return morador;
    }

    public double getMulta() {
        return multa;
    }

    // Setter para status (necessário ao carregar de arquivo)
    public void setStatus(Status status) {
        this.status = status;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public void setMulta(double multa) {
        this.multa = multa;
    }

    @Override
    public String toString() {
        return "\n--- Pagamento ID " + id + " ---" +
                "\nMorador: " + morador.getNome() +
                "\nValor original: R$ " + String.format("%.2f", valor) +
                "\nMulta: R$ " + String.format("%.2f", multa) +
                "\nValor total: R$ " + String.format("%.2f", getValorTotal()) +
                "\nVencimento: " + dataVencimento +
                "\nPagamento: " + (dataPagamento != null ? dataPagamento : "Não pago") +
                "\nStatus: " + status +
                "\n----------------------------";
    }
}

