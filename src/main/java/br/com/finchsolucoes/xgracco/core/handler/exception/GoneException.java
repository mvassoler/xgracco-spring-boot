package br.com.finchsolucoes.xgracco.core.handler.exception;

public class GoneException extends ValidationException{


    public GoneException() {
    }

    public GoneException(String message, Throwable cause) {
        super(message, cause);
    }

    public GoneException(Throwable cause) {
        super(cause);
    }

    public GoneException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public GoneException(String message) {
        super(message);
    }

    @Override
    public String getProperty() {
        return "gone";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
