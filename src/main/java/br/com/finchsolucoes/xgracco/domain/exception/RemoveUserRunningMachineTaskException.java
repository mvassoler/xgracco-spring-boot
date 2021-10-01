package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Exceção para ID não nulo.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
public class RemoveUserRunningMachineTaskException extends ValidationException {

    private final Object[] objects;

    public RemoveUserRunningMachineTaskException(Object[] objects) {
        this.objects = objects;
    }

    @Override
    public String getProperty() {
        return "pessoas";
    }

    @Override
    public Object[] getObjects() {
        return objects;
    }
}
