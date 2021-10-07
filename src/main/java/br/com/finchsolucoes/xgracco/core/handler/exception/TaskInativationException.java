package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Inativação de tarefa
 *
 * @author paulo.marcon
 * @since 5.0.0
 */
public class TaskInativationException extends RuntimeException {

    public TaskInativationException() {}

    public TaskInativationException(String message) {
        super(message);
    }

    public TaskInativationException(String message, Throwable cause) {
        super(message, cause);
    }

}
