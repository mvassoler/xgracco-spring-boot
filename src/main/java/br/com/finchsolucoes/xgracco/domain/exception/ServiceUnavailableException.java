package br.com.finchsolucoes.xgracco.domain.exception;

public class ServiceUnavailableException extends ValidationException{


    public ServiceUnavailableException() {
    }

    public ServiceUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceUnavailableException(Throwable cause) {
        super(cause);
    }

    public ServiceUnavailableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ServiceUnavailableException(String message) {
        super(message);
    }

    @Override
    public String getProperty() {
        return "service";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
