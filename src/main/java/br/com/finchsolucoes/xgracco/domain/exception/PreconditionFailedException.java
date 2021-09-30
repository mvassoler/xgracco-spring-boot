package br.com.finchsolucoes.xgracco.domain.exception;

public class PreconditionFailedException extends  RuntimeException{

    public PreconditionFailedException() {

    }

    public PreconditionFailedException(final String message) {
        super(message);
    }

    public PreconditionFailedException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
