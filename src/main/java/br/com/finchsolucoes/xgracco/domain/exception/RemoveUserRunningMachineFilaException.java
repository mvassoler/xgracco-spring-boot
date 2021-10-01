package br.com.finchsolucoes.xgracco.domain.exception;

public class RemoveUserRunningMachineFilaException extends ValidationException {

    private final Object[] objects;

    public RemoveUserRunningMachineFilaException(Object[] objects) {
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
