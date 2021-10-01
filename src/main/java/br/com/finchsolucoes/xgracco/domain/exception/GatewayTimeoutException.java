package br.com.finchsolucoes.xgracco.domain.exception;

public class GatewayTimeoutException extends  RuntimeException{

    public GatewayTimeoutException() {

    }

    public GatewayTimeoutException(final String message) {
        super(message);
    }

    public GatewayTimeoutException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
