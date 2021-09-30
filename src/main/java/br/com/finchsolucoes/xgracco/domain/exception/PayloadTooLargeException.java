package br.com.finchsolucoes.xgracco.domain.exception;

public class PayloadTooLargeException extends RuntimeException{

    public PayloadTooLargeException() {

    }

    public PayloadTooLargeException(final String message) {
        super(message);
    }

    public PayloadTooLargeException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
