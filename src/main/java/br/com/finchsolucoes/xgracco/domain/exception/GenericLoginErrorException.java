package br.com.finchsolucoes.xgracco.domain.exception;

public class GenericLoginErrorException extends ValidationException {

    private String message;

    public GenericLoginErrorException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getProperty() {
        return null;
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }

    @Override
    public String getMessage() {
        return message;
    }
}
