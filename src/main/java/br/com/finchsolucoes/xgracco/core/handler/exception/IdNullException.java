package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Exceção para ID nulo.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
public class IdNullException extends ValidationException {

    public IdNullException() {
    }

    public IdNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public IdNullException(Throwable cause) {
        super(cause);
    }

    public IdNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public IdNullException(String message) {
        super(message);
    }

    @Override
    public String getProperty() {
        return "id";
    }

    @Override
    public Object[] getObjects() {
        return new Object[]{};
    }
}
