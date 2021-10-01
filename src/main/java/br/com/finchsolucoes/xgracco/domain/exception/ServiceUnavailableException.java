package br.com.finchsolucoes.xgracco.domain.exception;

public class ServiceUnavailableException extends ValidationException{


    @Override
    public String getProperty() {
        return "service";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
