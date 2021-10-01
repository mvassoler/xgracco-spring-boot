package br.com.finchsolucoes.xgracco.domain.exception;

public class RemoverMateriaException extends ValidationException{
    private final Object[] objects;

    public RemoverMateriaException(Object[] objects) {
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
