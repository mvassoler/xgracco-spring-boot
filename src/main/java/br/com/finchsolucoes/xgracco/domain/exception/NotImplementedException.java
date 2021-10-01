package br.com.finchsolucoes.xgracco.domain.exception;

public class NotImplementedException extends ValidationException{


    public NotImplementedException() {
    }

    public NotImplementedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotImplementedException(Throwable cause) {
        super(cause);
    }

    public NotImplementedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public NotImplementedException(String message) {
        super(message);
    }

    @Override
    public String getProperty() {
        return "naoimplementado";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
