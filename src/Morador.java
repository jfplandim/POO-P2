import java.util.ArrayList;
import java.util.List;

public class Morador extends Pessoa{
    private final int id;
    private int quantidadePets;
    private final Apartamento apartamento;
    private List<Reserva> reservas;
    private List<Pagamento> pagamentos;

    public Morador(int id, String nome, String documento, String telefone, int quantidadePets,
                    Apartamento apartamento) throws CampoInvalidoException{
        super(nome, documento, telefone);
        //validação do apartamento, este não pode ser nulo
        if(apartamento == null){
            throw new CampoInvalidoException("Apartamento não pode ser nulo.");
        }
        this.id=id;
        this.quantidadePets=quantidadePets;
        this.apartamento=apartamento;
        this.reservas = new ArrayList<>();
        this.pagamentos = new ArrayList<>();
        //adicionar morador ao apartamento automaticamente
        if(!apartamento.adicionarMorador(this)){
            throw new CampoInvalidoException("Não foi possível adicionar o morador ao apartamento");
        }
    }



}
