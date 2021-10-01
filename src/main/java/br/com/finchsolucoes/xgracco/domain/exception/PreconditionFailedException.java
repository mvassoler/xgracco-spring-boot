package br.com.finchsolucoes.xgracco.domain.exception;

public class PreconditionFailedException extends  ValidationException{


    public PreconditionFailedException() {
    }

    public PreconditionFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public PreconditionFailedException(Throwable cause) {
        super(cause);
    }

    public PreconditionFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public PreconditionFailedException(String message) {
        super(message);
    }

    @Override
    public String getProperty() {
        return "precondition";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
