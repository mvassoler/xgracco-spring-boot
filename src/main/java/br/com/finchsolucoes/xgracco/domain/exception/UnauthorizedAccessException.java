package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * @author Thiago Fogar
 * @since
 */
public class UnauthorizedAccessException extends ValidationException {

    public UnauthorizedAccessException() {
    }

    public UnauthorizedAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedAccessException(Throwable cause) {
        super(cause);
    }

    public UnauthorizedAccessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public UnauthorizedAccessException(String message) {
        super(message);
    }

    @Override
    public String getProperty() {
        return "";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }

}