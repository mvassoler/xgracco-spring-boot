package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Exceção lançada quando o status final fornecido não pertence à tarefa.
 *
 * @author Roberto Amadeu Neto
 * @since 5.3.0
 */
public class StatusFinalNaoPertenceTarefaException extends ValidationException {


    public StatusFinalNaoPertenceTarefaException() {
    }

    public StatusFinalNaoPertenceTarefaException(String message, Throwable cause) {
        super(message, cause);
    }

    public StatusFinalNaoPertenceTarefaException(Throwable cause) {
        super(cause);
    }

    public StatusFinalNaoPertenceTarefaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public StatusFinalNaoPertenceTarefaException(String message) {
        super(message);
    }

    @Override
    public String getProperty() {
        return null;
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
