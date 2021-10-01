package br.com.finchsolucoes.xgracco.domain.exception;

public class RemoveIndiceEconomicoException extends ValidationException{
    private final Object[] objects;

    public RemoveIndiceEconomicoException(Object[] objects) {
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
