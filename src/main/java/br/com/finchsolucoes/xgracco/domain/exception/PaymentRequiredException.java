package br.com.finchsolucoes.xgracco.domain.exception;

public class PaymentRequiredException extends RuntimeException{

    public PaymentRequiredException() {
    }

    public PaymentRequiredException(final String message) {
        super(message);
    }

    public PaymentRequiredException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
