package br.com.finchsolucoes.xgracco.domain.exception;

public class NotImplementedException extends RuntimeException{

    public NotImplementedException() {

    }

    public NotImplementedException(final String message) {
        super(message);
    }

    public NotImplementedException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
