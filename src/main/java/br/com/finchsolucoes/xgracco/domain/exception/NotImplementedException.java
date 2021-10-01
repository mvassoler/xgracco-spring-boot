package br.com.finchsolucoes.xgracco.domain.exception;

public class NotImplementedException extends ValidationException{


    @Override
    public String getProperty() {
        return "naoimplementado";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
