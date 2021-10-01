package br.com.finchsolucoes.xgracco.domain.exception;

public class GenericErrorException extends ValidationException {

    private Object[] objects;

    public GenericErrorException(Object[] objects) {
        this.objects = objects;
    }

    @Override
    public String getProperty() {
        return "task";
    }

    @Override
    public Object[] getObjects() {
        return objects;
    }
}
