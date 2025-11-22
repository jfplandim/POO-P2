import java.util.ArrayList;
import java.util.List;

public class Morador extends Pessoa {
    private final int id;
    private int quantidadePets;
    private Apartamento apartamento;
    private List<Reserva> reservas;
    private List<Pagamento> pagamentos;

    public Morador(int id, String nome, String documento, String telefone, int quantidadePets) throws CampoInvalidoException {
        super(nome, documento, telefone);
        if (quantidadePets < 0) {
            throw new CampoInvalidoException("Quantidade de pets não pode ser negativa.");
        }
        this.id=id;
        this.quantidadePets=quantidadePets;
        this.apartamento=null;
        this.reservas = new ArrayList<>();
        this.pagamentos = new ArrayList<>();
    }

    public void setApartamento(Apartamento apartamento) {
        this.apartamento = apartamento;
    }

    public Apartamento getApartamento() {
        return apartamento;
    }

    public int getId() {
        return id;
    }

    public int getQuantidadePets() {
        return quantidadePets;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public void setQuantidadePets(int quantidadePets) throws CampoInvalidoException {
        if (quantidadePets < 0){
            throw new CampoInvalidoException("A quantidade de pets não pode ser negativa.");
        }
        this.quantidadePets = quantidadePets;
    }
}
