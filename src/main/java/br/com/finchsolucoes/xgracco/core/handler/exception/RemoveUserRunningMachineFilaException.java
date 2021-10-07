package br.com.finchsolucoes.xgracco.core.handler.exception;

public class RemoveUserRunningMachineFilaException extends RuntimeException {

    private final Object[] objects;

    public RemoveUserRunningMachineFilaException(Object[] objects) {
        this.objects = objects;
    }

    public RemoveUserRunningMachineFilaException(String message, Throwable cause, Object[] objects) {
        super(message, cause);
        this.objects = objects;
    }

    public RemoveUserRunningMachineFilaException(Throwable cause, Object[] objects) {
        super(cause);
        this.objects = objects;
    }

    public RemoveUserRunningMachineFilaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object[] objects) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.objects = objects;
    }

    public RemoveUserRunningMachineFilaException(String message, Object[] objects) {
        super(message);
        this.objects = objects;
    }

}
