package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Excessão para exclusão de um status final
 *
 * @author Raphael Moreira
 * @since 2.1
 */
public class RemoveStatusFinalException extends RuntimeException {

    private final Object[] objects;

    public RemoveStatusFinalException(Object[] objects) {
        this.objects = objects;
    }

    public RemoveStatusFinalException(String message, Throwable cause, Object[] objects) {
        super(message, cause);
        this.objects = objects;
    }

    public RemoveStatusFinalException(Throwable cause, Object[] objects) {
        super(cause);
        this.objects = objects;
    }

    public RemoveStatusFinalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object[] objects) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.objects = objects;
    }

    public RemoveStatusFinalException(String message, Object[] objects) {
        super(message);
        this.objects = objects;
    }

}
