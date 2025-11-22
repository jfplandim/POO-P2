public abstract class Pessoa {
    private final String nome;
    private final String documento;
    private final String telefone;


    public Pessoa(String nome, String documento, String telefone) throws CampoInvalidoException {
        //verificações para nao adicionar nome, telefone e documento invalidos
        //caso seja nulo ou seja digitado apenas espaço
        //faz a verifação e logo em seguida atribui
        if(nome == null || nome.trim().isEmpty()){
            throw new CampoInvalidoException("Nome não pode ser vazio");
        }
        this.nome = nome;

        if (documento == null || documento.trim().isEmpty()) {
            throw new CampoInvalidoException("Documento não pode ser vazio");
        }
        this.documento=documento;

        if (telefone == null || telefone.trim().isEmpty()) {
            throw new CampoInvalidoException("Telefone não pode ser vazio");
        }
        this.telefone=telefone;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getDocumento() {
        return documento;
    }
}
