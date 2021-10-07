package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Exceção para tarefas pendentes
 *
 * @author paulomarcon
 * @since 2.2.0
 */
public class PendingTasksException extends RuntimeException {

    public PendingTasksException() { }

    public PendingTasksException(String message) {
        super(message);
    }

    public PendingTasksException(String message, Throwable cause) {
        super(message, cause);
    }

}
