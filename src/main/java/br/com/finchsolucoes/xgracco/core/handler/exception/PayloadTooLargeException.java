package br.com.finchsolucoes.xgracco.core.handler.exception;

public class PayloadTooLargeException extends RuntimeException{

    public PayloadTooLargeException() { }

    public PayloadTooLargeException(String message) {
        super(message);
    }

    public PayloadTooLargeException(String message, Throwable cause) {
        super(message, cause);
    }

}
