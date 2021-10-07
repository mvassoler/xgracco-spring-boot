package br.com.finchsolucoes.xgracco.core.handler.exception;

public class RemoveIndiceEconomicoException extends RuntimeException{

    private final Object[] objects;

    public RemoveIndiceEconomicoException(Object[] objects) {
        this.objects = objects;
    }

    public RemoveIndiceEconomicoException(String message, Throwable cause, Object[] objects) {
        super(message, cause);
        this.objects = objects;
    }

    public RemoveIndiceEconomicoException(Throwable cause, Object[] objects) {
        super(cause);
        this.objects = objects;
    }

    public RemoveIndiceEconomicoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object[] objects) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.objects = objects;
    }

    public RemoveIndiceEconomicoException(String message, Object[] objects) {
        super(message);
        this.objects = objects;
    }

}
