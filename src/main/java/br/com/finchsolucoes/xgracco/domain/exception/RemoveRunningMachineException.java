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

    @Override
    public String getProperty() {
        return "id";
    }

    @Override
    public Object[] getObjects() {
        return objects;
    }
}
