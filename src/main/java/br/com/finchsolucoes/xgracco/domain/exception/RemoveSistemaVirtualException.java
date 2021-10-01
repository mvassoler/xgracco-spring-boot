package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * @author guicamargo
 */
public class RemoveSistemaVirtualException extends ValidationException {

    public RemoveSistemaVirtualException() {
    }

    public RemoveSistemaVirtualException(String message, Throwable cause) {
        super(message, cause);
    }

    public RemoveSistemaVirtualException(Throwable cause) {
        super(cause);
    }

    public RemoveSistemaVirtualException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public RemoveSistemaVirtualException(String message) {
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
