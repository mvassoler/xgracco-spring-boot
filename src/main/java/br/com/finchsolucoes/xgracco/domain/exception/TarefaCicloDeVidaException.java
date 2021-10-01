package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * * Ciclo de Vida da Tarefa:
 *
 *      * De -> Para
 *      * PENDENTE -> DEVOLVIDA | CONCLUÍDA
 *      * DEVOLVIDA -> PENDENTE | CONCLUÍDA | HIBERNADA
 *      * HIBERNADA -> DEVOLVIDA | CONCLUÍDA
 */
public class TarefaCicloDeVidaException extends ValidationException {

    private String statusAtual;
    private String statusNovo;

    public TarefaCicloDeVidaException(String statusAtual, String statusNovo) {
        this.statusAtual = statusAtual;
        this.statusNovo = statusNovo;
    }

    @Override
    public String getProperty() {
        return "status";
    }

    @Override
    public Object[] getObjects() {
        return new Object[]{statusAtual, statusNovo};
    }
}
