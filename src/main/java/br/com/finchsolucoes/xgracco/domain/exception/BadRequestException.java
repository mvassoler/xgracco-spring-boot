package br.com.finchsolucoes.xgracco.domain.exception;

public class BadRequestException extends ValidationException{


    @Override
    public String getProperty() {
        return "badrequest";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
