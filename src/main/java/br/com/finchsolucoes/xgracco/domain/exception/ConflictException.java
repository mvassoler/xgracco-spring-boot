package br.com.finchsolucoes.xgracco.domain.exception;

public class ConflictException extends ValidationException {


    @Override
    public String getProperty() {
        return "conflito";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
