import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class AreaComum {
    private String nome;
    private int limiteHoras;
    private List<Reserva> reservas;

    // Construtor
    public AreaComum(String nome, int limiteHoras) {
        this.nome = nome;
        this.limiteHoras = limiteHoras;
        this.reservas = new ArrayList<>();
    }
    public String getNome() {
        return nome;
    }

}