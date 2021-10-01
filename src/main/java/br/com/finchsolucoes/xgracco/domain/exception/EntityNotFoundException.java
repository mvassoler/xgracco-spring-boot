package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Exceção entidade não encontrada.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
public class EntityNotFoundException extends ValidationException {


    public EntityNotFoundException() {
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFoundException(Throwable cause) {
        super(cause);
    }

    public EntityNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getProperty() {
        return null;
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
