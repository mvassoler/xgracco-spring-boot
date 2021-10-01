package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Exceção para conflito de ID.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
public class IdConflictException extends ValidationException {


    public IdConflictException() {
    }

    public IdConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public IdConflictException(Throwable cause) {
        super(cause);
    }

    public IdConflictException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public IdConflictException(String message) {
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
