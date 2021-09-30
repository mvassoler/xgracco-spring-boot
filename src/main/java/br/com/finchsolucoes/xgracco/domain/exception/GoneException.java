package br.com.finchsolucoes.xgracco.domain.exception;

public class GoneException extends RuntimeException{

    public GoneException() {

    }

    public GoneException(final String message) {
        super(message);
    }

    public GoneException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
