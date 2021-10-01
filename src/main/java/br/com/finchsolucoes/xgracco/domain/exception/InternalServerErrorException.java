package br.com.finchsolucoes.xgracco.domain.exception;

public class InternalServerErrorException  extends ValidationException{


    @Override
    public String getProperty() {
        return "interno";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
