package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Exceção para tarefas pendentes
 *
 * @author paulomarcon
 * @since 2.2.0
 */
public class PendingTasksException extends ValidationException {

    public PendingTasksException() {
    }

    public PendingTasksException(String message, Throwable cause) {
        super(message, cause);
    }

    public PendingTasksException(Throwable cause) {
        super(cause);
    }

    public PendingTasksException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public PendingTasksException(String message) {
        super(message);
    }

    @Override
    public String getProperty() {
        return "carteira";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
