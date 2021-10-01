package br.com.finchsolucoes.xgracco.domain.exception;

public class GoneException extends ValidationException{


    @Override
    public String getProperty() {
        return "gone";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
