import java.io.Serializable;

class Chamado implements Serializable {
    private static final long serialVersionUID = 1L;
    private String status; // Aberto, Em andamento, Fechado

    public Chamado(String status) { this.status = status; }

    public String getStatus() { return status; }
}
