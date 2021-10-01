package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Exceção para ID não nulo.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
public class IdNotNullException extends ValidationException {

    public IdNotNullException() {
    }

    public IdNotNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public IdNotNullException(Throwable cause) {
        super(cause);
    }

    public IdNotNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public IdNotNullException(String message) {
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
