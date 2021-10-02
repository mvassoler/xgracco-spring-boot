package br.com.finchsolucoes.xgracco.core.handler.exception;

public class PayloadTooLargeException extends ValidationException{


    public PayloadTooLargeException() {
    }

    public PayloadTooLargeException(String message, Throwable cause) {
        super(message, cause);
    }

    public PayloadTooLargeException(Throwable cause) {
        super(cause);
    }

    public PayloadTooLargeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public PayloadTooLargeException(String message) {
        super(message);
    }

    @Override
    public String getProperty() {
        return "payload";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
