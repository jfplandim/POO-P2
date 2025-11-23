import java.io.*;
import java.util.*;

public class ControleVisitante {
    private List<Visitante> visitantes;
    private final String ARQUIVO_VISITANTES = "visitantes.txt";
    private final List<Morador> moradoresDisponiveis;

    //construtor que recebe a lista de moradores para realizar a persistencia
    public ControleVisitante(List<Morador> moradores) {
        this.visitantes = new ArrayList<>();
        this.moradoresDisponiveis = moradores;
        carregarVisitantes();
    }

    //metodo registrar entrada, que é chamada pelo sistemaprincipal
    public void registrarEntrada(Visitante novoVisitante) {
        this.visitantes.add(novoVisitante);
        System.out.println(" Entrada do visitante " + novoVisitante.getNome() + "registrada.");
        salvarVisitantes();
    }

    //registra saida, que busca e atualiza o objeto
    public boolean registrarSaida(String documentoVisitante) {
        for (Visitante v : visitantes) {
            //busca pelo doc e verifica se já saiu
            if (v.getDocumento().equals(documentoVisitante) && v.getDataHoraSaida() == null) {
                try {
                    v.registrarSaida();
                    System.out.println("Saída do visitante " + v.getNome() + "registrada.");
                    salvarVisitantes();
                    return true;
                } catch (CampoInvalidoException e) {
                    System.err.println("Erro ao registrar saída: " + e.getMessage());
                    return false;
                }
            }
        }
        System.out.println("Visitante com documento " + documentoVisitante + " não encontrado.");
        return false;
    }

    //gera o relatorio das visitas
    public void gerarRelatorioVisitas() {
        System.out.println("\n=== RELATÓRIO DE VISITAS ===");

        if (visitantes.isEmpty()) {
            System.out.println("Nenhuma visita registrada.");
            return;
        }

        long visitantesNoCondominio = visitantes.stream()
                .filter(v -> v.getDataHoraSaida() == null)
                .count();

        System.out.println("Total de visitas: " + visitantes.size());
        System.out.println("Visitantes no condomínio: " + visitantesNoCondominio);
        System.out.println("========================================");

        for (Visitante v : visitantes) {
            System.out.println(v.toString());
            System.out.println("========================================");
        }
    }

    public void salvarVisitantes() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARQUIVO_VISITANTES))) {
            for (Visitante v : visitantes) {
                // Formato: NOME;DOCUMENTO;TELEFONE;DOC_MORADOR;ENTRADA_MS;SAIDA_MS
                // Metodo de persistencia, transforma os objetos da memoria e grava no visitantes.txt
                String docMorador = v.getMoradorVisitado().getDocumento();
                long entradaMs = v.getDataHoraEntrada().getTime();
                long saidaMs = (v.getDataHoraSaida() != null) ? v.getDataHoraSaida().getTime() : -1;
                String linha = String.format("%s;%s;%s;%s;%d;%d", v.getNome(), v.getDocumento(), v.getTelefone(), docMorador, entradaMs, saidaMs);
                writer.println(linha);
            }
            System.out.println("Dados de visitantes salvos.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar visitantes: " + e.getMessage());
        }
    }

    private void carregarVisitantes() {
        File arquivo = new File(ARQUIVO_VISITANTES);
        if (!arquivo.exists()) return;

        this.visitantes.clear();

        try (Scanner scanner = new Scanner(arquivo)) {
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                String[] dados = linha.split(";");

                if (dados.length != 6) continue;
                try {
                    long entradaMs = Long.parseLong(dados[4]);
                    long saidaMs = Long.parseLong(dados[5]);

                    Date entrada = new Date(entradaMs);
                    Date saida = (saidaMs != -1) ? new Date(saidaMs) : null;

                    Morador moradorVisitado = buscarMoradorPorDocumento(dados[3]);

                    if (moradorVisitado != null) {
                        Visitante v = new Visitante(dados[0], dados[1], dados[2], moradorVisitado, entrada, saida);
                        this.visitantes.add(v);
                    } else {
                        System.err.println("Morador visitado com documento: " + dados[3] + " não encontrado.");
                    }

                } catch (Exception e) {
                    System.err.println("Erro ao ler arquivo" + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.print("Erro ao carregar linha: " + e.getMessage());
        }
    }

    private Morador buscarMoradorPorDocumento(String documento) {
        //implementação feita com a parte 1
        //busca na lista "moradoresDisponiveis" e retorna morador
        if (this.moradoresDisponiveis != null) {
            for (Morador m : moradoresDisponiveis) {
                if (m.getDocumento().equals(documento)) {
                    return m;
                }
            }
        }
            return null;
    }
}

