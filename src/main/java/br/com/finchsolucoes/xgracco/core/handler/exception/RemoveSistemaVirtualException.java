package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * @author guicamargo
 */
public class RemoveSistemaVirtualException extends RuntimeException {

    public RemoveSistemaVirtualException() { }

    public RemoveSistemaVirtualException(String message) {
        super(message);
    }

    public RemoveSistemaVirtualException(String message, Throwable cause) {
        super(message, cause);
    }

}
