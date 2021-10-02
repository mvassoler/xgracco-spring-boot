package br.com.finchsolucoes.xgracco.core.handler.exception;

public class GatewayTimeoutException extends  ValidationException{


    public GatewayTimeoutException() {

    }

    public GatewayTimeoutException(final String message) {
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

    public GatewayTimeoutException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public GatewayTimeoutException(Throwable cause) {
        super(cause);
    }

    public GatewayTimeoutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
