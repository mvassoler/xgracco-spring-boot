package br.com.finchsolucoes.xgracco.core.handler.exception;

public class NoFilaActiveException extends RuntimeException {

    public NoFilaActiveException() {}

    public NoFilaActiveException(String message) {
        super(message);
    }

    public NoFilaActiveException(String message, Throwable cause) {
        super(message, cause);
    }

}
