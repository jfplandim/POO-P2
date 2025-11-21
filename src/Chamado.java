public class Chamado {
    private  final String descricao;
    private String status;

    //classe de apoio para a classe controleFinanceiro.
    public Chamado(String descricao){
        this.descricao=descricao;
        this.status="aberto";
    }

    public String getDescricao(){return descricao;}
    public String getStatus(){return status;}

    public void fecharChamado(){
        this.status="fechado";
    }



}
