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

    public TarefaCicloDeVidaException(String statusAtual) {
        this.statusAtual = statusAtual;
    }

    public TarefaCicloDeVidaException(String message, Throwable cause, String statusAtual) {
        super(message, cause);
        this.statusAtual = statusAtual;
    }

    public TarefaCicloDeVidaException(Throwable cause, String statusAtual) {
        super(cause);
        this.statusAtual = statusAtual;
    }

    public TarefaCicloDeVidaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String statusAtual) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.statusAtual = statusAtual;
    }

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
