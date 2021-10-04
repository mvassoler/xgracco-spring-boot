package br.com.finchsolucoes.xgracco.core.handler.exception;

public class NoFilaActiveException extends ValidationException {


    public NoFilaActiveException() {
    }

    public NoFilaActiveException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoFilaActiveException(Throwable cause) {
        super(cause);
    }

    public NoFilaActiveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public NoFilaActiveException(String message) {
        super(message);
    }

    @Override
    public String getProperty() {
        return "id";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
