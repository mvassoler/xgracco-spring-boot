package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Excessão para exclusão de um perfil
 *
 * @author Renan Peres
 * @since 2.1
 */

public class RemoverPerfilException extends ValidationException{

    private final Object[] objects;

    public RemoverPerfilException(Object[] objects) {
        this.objects = objects;
    }

    public RemoverPerfilException(String message, Throwable cause, Object[] objects) {
        super(message, cause);
        this.objects = objects;
    }

    public RemoverPerfilException(Throwable cause, Object[] objects) {
        super(cause);
        this.objects = objects;
    }

    public RemoverPerfilException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object[] objects) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.objects = objects;
    }

    public RemoverPerfilException(String message, Object[] objects) {
        super(message);
        this.objects = objects;
    }

    @Override
    public String getProperty() {
        return "id";
    }

    @Override
    public Object[] getObjects() {
        return objects;
    }
}
