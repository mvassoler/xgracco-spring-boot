package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Exceção para entidade que não pode ser excluída.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
public class EntityCannotBeRemovedException extends ValidationException {


    public EntityCannotBeRemovedException() {
    }

    public EntityCannotBeRemovedException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityCannotBeRemovedException(Throwable cause) {
        super(cause);
    }

    public EntityCannotBeRemovedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public EntityCannotBeRemovedException(String message) {
        super(message);
    }

    @Override
    public String getProperty() {
        return "entidadenaopodeserremovida";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
