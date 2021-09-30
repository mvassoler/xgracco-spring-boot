package br.com.finchsolucoes.xgracco.domain.exception;

public class UnAuthorizedeException extends  RuntimeException {

    public UnAuthorizedeException() {

    }

    public UnAuthorizedeException(final String message) {
        super(message);
    }

    public UnAuthorizedeException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
