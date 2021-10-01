package br.com.finchsolucoes.xgracco.domain.exception;

public class UnAuthorizedeException extends  ValidationException {


    @Override
    public String getProperty() {
        return "unauthorized";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
