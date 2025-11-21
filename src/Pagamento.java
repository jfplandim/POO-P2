import javax.xml.crypto.Data;
import java.util.Date;

public class Pagamento {
    private final int Id;
    private final String mes;
    private double valor;
    private  Date dataPagamento;
    private final Date dataVencimento;
    private final Status status;
    private  Morador morador;
    private double multa;

    public enum Status{
        pago,
        pendente,
        atrasado,
        cancelado
    }

    public Pagamento( Morador morador,int Id,Double valor,String mes,Date dataPagamento,Date dataVencimento,Status status ){
        this.morador=morador;
        this.Id=Id;
        this.valor=valor;
        this.mes=mes;
        this.dataVencimento=dataVencimento;

        this.dataPagamento=null;
        this.status=Status.pendente;
        this.multa=0.0;
    }

    public void Multar(){
       this.multa+=multa;
    }

    public boolean Atrasado(Date hoje){
        if(this.status==Status.pago){
            return false;
        }
        return hoje.after(this.dataVencimento);//O metodo after() compara as datas e verifica se a data atual e postarior a data de vencimento.

    }

    public int getId() {return Id;}

    public String getMes() {return mes;}

    public double getValor() {return valor;}

    public Date getDataPagamento() {return dataPagamento;}

    public Date getDataVencimento() {return dataVencimento;}

    public Status getStatus() {return status;}

    public Morador getMorador() {return morador;}
}
