package br.com.finchsolucoes.xgracco.domain.exception;

public class UnAuthorizedeException extends  ValidationException {


    public UnAuthorizedeException() {
    }

    public UnAuthorizedeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnAuthorizedeException(Throwable cause) {
        super(cause);
    }

    public UnAuthorizedeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public UnAuthorizedeException(String message) {
        super(message);
    }

    @Override
    public String getProperty() {
        return "unauthorized";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
