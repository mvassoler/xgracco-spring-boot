package br.com.finchsolucoes.xgracco.domain.exception;

public class InternalServerErrorException  extends ValidationException{

    public InternalServerErrorException() {
    }

    public InternalServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public InternalServerErrorException(Throwable cause) {
        super(cause);
    }

    public InternalServerErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public InternalServerErrorException(String message) {
        super(message);
    }

    @Override
    public String getProperty() {
        return "interno";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
