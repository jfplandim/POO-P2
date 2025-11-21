import java.io.*;
import java.util.*;

public class ControleVisitante {
    private List<Visitante> visitantes;
    private final String ARQUIVO_VISITANTES = "visitantes.txt";
    private final List<Morador> moradoresDisponiveis;

    //construtor que recebe a lista de moradores para realizar a persistencia
    ControleVisitante(List<Morador> moradores){
        this.visitantes=new ArrayList<>();
        this.moradoresDisponiveis=moradores;
        carregarVisitantes();
    }
    //metodo registrar entrada, que é chamada pelo sistemaprincipal
    void registrarEntrada(Visitante novoVisitante){
        this.visitantes.add(novoVisitante);
        System.out.println("Entrada do visitante "+ novoVisitante.getNome()+ "registrada.") ;
    }
    //registra saida, que busca e atualiza o objeto
    boolean registrarSaida(String documentoVisitante){
        for(Visitante v : visitantes){
            //busca pelo doc e verifica se já saiu
            if (v.getDocumento().equals(documentoVisitante)&& v.getDataHoraSaida()==null){
                try{
                    v.registrarSaida();
                    System.out.println("Saída do visitante "+ v.getNome()+ "registrada.");
                    salvarVisitantes();
                    return true;
                } catch (CampoInvalidoException e){
                    System.err.println("Erro ao registrar saída: "+ e.getMessage());
                    return false;
                }
            }
        }
        System.out.println("Visitante com documento "+ documentoVisitante + " não encontrado.");
        return false;
    }
    //gera o relatorio das visitas
    void gerarRelatorioVisitas(){
        System.out.println("---Relatorio de Visitas---");
        if (visitantes.isEmpty()){
            System.out.println("Nenhuma visita registrada.");
            return;
        }
        for(Visitante v:visitantes){
            System.out.println(v.toString());
        }
    }

}
