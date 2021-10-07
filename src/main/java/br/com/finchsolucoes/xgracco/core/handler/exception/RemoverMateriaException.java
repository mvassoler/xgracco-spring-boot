package br.com.finchsolucoes.xgracco.core.handler.exception;

public class RemoverMateriaException extends RuntimeException{


    private final Object[] objects;

    public RemoverMateriaException(Object[] objects) {
        this.objects = objects;
    }

    public RemoverMateriaException(String message, Throwable cause, Object[] objects) {
        super(message, cause);
        this.objects = objects;
    }

    public RemoverMateriaException(Throwable cause, Object[] objects) {
        super(cause);
        this.objects = objects;
    }

    public RemoverMateriaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object[] objects) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.objects = objects;
    }

    public RemoverMateriaException(String message, Object[] objects) {
        super(message);
        this.objects = objects;
    }

}
