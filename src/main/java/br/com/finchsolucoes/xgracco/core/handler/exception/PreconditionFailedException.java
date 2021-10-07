package br.com.finchsolucoes.xgracco.core.handler.exception;

public class PreconditionFailedException extends  RuntimeException{

    public PreconditionFailedException() {}

    public PreconditionFailedException(String message) {
        super(message);
    }

    public PreconditionFailedException(String message, Throwable cause) {
        super(message, cause);
    }

}
