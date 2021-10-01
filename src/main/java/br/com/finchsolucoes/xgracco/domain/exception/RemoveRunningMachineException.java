package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Excessão para exclusão de uma esteira
 *
 * @author Raphael Moreira
 * @since 2.1
 */
public class RemoveRunningMachineException extends ValidationException {


    private final Object[] objects;

    public RemoveRunningMachineException(Object[] objects) {
        this.objects = objects;
    }

    public RemoveRunningMachineException(String message, Throwable cause, Object[] objects) {
        super(message, cause);
        this.objects = objects;
    }

    public RemoveRunningMachineException(Throwable cause, Object[] objects) {
        super(cause);
        this.objects = objects;
    }

    public RemoveRunningMachineException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object[] objects) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.objects = objects;
    }

    public RemoveRunningMachineException(String message, Object[] objects) {
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
