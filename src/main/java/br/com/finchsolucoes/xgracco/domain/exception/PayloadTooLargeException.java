package br.com.finchsolucoes.xgracco.domain.exception;

public class PayloadTooLargeException extends ValidationException{


    @Override
    public String getProperty() {
        return "payload";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
