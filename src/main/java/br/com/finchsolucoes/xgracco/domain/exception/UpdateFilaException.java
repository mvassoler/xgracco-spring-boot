package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * @author guilhermecamargo
 */
public class UpdateFilaException extends ValidationException {


    public UpdateFilaException() {
    }

    public UpdateFilaException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateFilaException(Throwable cause) {
        super(cause);
    }

    public UpdateFilaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public UpdateFilaException(String message) {
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
