package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * @author Thiago Fogar
 * @since
 */
public class InvalidIndiceEconomicoException extends ValidationException {


    public InvalidIndiceEconomicoException() {
    }

    public InvalidIndiceEconomicoException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidIndiceEconomicoException(Throwable cause) {
        super(cause);
    }

    public InvalidIndiceEconomicoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public InvalidIndiceEconomicoException(String message) {
        super(message);
    }

    @Override
    public String getProperty() {
        return "dataInicial";
    }

    @Override
    public Object[] getObjects() {
        return new Object[]{};
    }
}
