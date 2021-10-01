package br.com.finchsolucoes.xgracco.domain.exception;

public class ConflictException extends ValidationException {


    public ConflictException() {
    }

    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConflictException(Throwable cause) {
        super(cause);
    }

    public ConflictException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ConflictException(String message) {
        super(message);
    }

    @Override
    public String getProperty() {
        return "conflito";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
