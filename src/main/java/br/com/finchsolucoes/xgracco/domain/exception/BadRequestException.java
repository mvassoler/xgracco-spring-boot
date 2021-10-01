package br.com.finchsolucoes.xgracco.domain.exception;

public class BadRequestException extends ValidationException{

    public BadRequestException() {

    }

    public BadRequestException(String message) {
        super(message);
    }

    @Override
    public String getProperty() {
        return "badrequest";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
