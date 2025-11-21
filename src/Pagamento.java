import javax.xml.crypto.Data;
import java.time.Instant;
import java.time.LocalDate;
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
    private boolean manutencao;



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

    public  boolean Atrasado(){
       if(!recebido()&& dataVencimento.toInstant().isBefore(Instant.from(LocalDate.now()))){
           return true;
       }
       return false;
    }

    public boolean recebido(){
        return dataPagamento!=null;
    }

    public boolean isManutencao(){return  manutencao;}

    public int getId() {return Id;}

    public String getMes() {return mes;}

    public double getValor() {return valor;}

    public Date getDataPagamento() {return dataPagamento;}

    public Date getDataVencimento() {return dataVencimento;}

    public Status getStatus() {return status;}

    public Morador getMorador() {return morador;}
}
