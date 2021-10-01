package br.com.finchsolucoes.xgracco.domain.exception;

public class PreconditionFailedException extends  ValidationException{


    @Override
    public String getProperty() {
        return "precondition";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
