package br.com.finchsolucoes.xgracco.domain.exception;

public class IndiceEconomicoConflictException extends ValidationException {

    private final Object[] objects;

    public IndiceEconomicoConflictException(Object[] objects) {
        this.objects = objects;
    }

    @Override
    public String getProperty() {
        return "descricao";
    }

    @Override
    public Object[] getObjects() {
        return objects;
    }

}
