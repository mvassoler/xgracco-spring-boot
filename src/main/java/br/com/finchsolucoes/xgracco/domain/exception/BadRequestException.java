package br.com.finchsolucoes.xgracco.domain.exception;

public class BadRequestException extends ValidationException{

    public BadRequestException() {
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadRequestException(Throwable cause) {
        super(cause);
    }

    public BadRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public BadRequestException(String message) {
        super(message);
    }

    @Override
    public String getProperty() {
        return "badrequest";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
