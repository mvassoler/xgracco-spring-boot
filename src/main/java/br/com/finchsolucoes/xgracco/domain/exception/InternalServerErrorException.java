package br.com.finchsolucoes.xgracco.domain.exception;

public class InternalServerErrorException  extends RuntimeException{

    public InternalServerErrorException() {

    }

    public InternalServerErrorException(final String message) {
        super(message);
    }

    public InternalServerErrorException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
