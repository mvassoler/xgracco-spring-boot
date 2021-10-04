package br.com.finchsolucoes.xgracco.core.handler.exception;

public class ForbidenException extends  ValidationException{

    public ForbidenException() {
    }

    public ForbidenException(final String message) {
        super(message);
    }

    @Override
    public String getProperty() {
        return null;
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }

    public ForbidenException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ForbidenException(Throwable cause) {
        super(cause);
    }

    public ForbidenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
