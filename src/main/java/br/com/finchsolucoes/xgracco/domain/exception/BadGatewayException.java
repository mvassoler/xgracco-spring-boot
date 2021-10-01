package br.com.finchsolucoes.xgracco.domain.exception;

public class BadGatewayException extends ValidationException {

    public BadGatewayException() {

    }

    public BadGatewayException(final String message) {
        super(message);
    }

    @Override
    public String getProperty() {
        return "badgateway";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }

    public BadGatewayException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
