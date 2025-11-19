import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Apartamento {
    private final int numero;
    private final String bloco;
    private final int vagasGaragem;
    private List<Morador> moradores;

    public Apartamento(int numero, String bloco, int vagasGaragem){
        this.numero = numero;
        this.bloco = bloco;
        this.vagasGaragem = vagasGaragem;
        this.moradores = new ArrayList<>();
    }

    public boolean adicionarMorador(Morador m){
       //validação para impedir morador nulo
        if(m == null){
           return false;
       }
        //impedir que o morador esteja em outro apartamento
        if(m.getApartamento() != null && m.getApartamento() != this){
            return false;
        }
        //impedir duplicação
        if(moradores.contains(m)){
            return false;
        }
        moradores.add(m);
        return true;
    }

    public int getNumero() {
        return numero;
    }

    public String getBloco() {
        return bloco;
    }

    public int getVagasGaragem() {
        return vagasGaragem;
    }

    public List<Morador> getMoradores() {
        //retorna a lista mas impede a alteração dela por outra classe atraves do getMoradores.clear()
        return Collections.unmodifiableList(moradores);
    }
}
