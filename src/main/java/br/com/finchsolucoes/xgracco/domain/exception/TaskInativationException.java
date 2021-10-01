package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Inativação de tarefa
 *
 * @author paulo.marcon
 * @since 5.0.0
 */
public class TaskInativationException extends ValidationException {


    public TaskInativationException() {
    }

    public TaskInativationException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskInativationException(Throwable cause) {
        super(cause);
    }

    public TaskInativationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public TaskInativationException(String message) {
        super(message);
    }

    @Override
    public String getProperty() {
        return "id";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
