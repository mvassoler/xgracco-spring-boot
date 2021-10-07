package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Exceção para ID não nulo.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
public class RemoveUserRunningMachineTaskException extends RuntimeException {


    private final Object[] objects;

    public RemoveUserRunningMachineTaskException(Object[] objects) {
        this.objects = objects;
    }

    public RemoveUserRunningMachineTaskException(String message, Throwable cause, Object[] objects) {
        super(message, cause);
        this.objects = objects;
    }

    public RemoveUserRunningMachineTaskException(Throwable cause, Object[] objects) {
        super(cause);
        this.objects = objects;
    }

    public RemoveUserRunningMachineTaskException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object[] objects) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.objects = objects;
    }

    public RemoveUserRunningMachineTaskException(String message, Object[] objects) {
        super(message);
        this.objects = objects;
    }

}
