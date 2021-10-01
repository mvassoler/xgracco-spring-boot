package br.com.finchsolucoes.xgracco.domain.exception;

public class GenericLoginErrorException extends ValidationException {

    private String message;

    public GenericLoginErrorException(String message) {
        super(message);
        this.message = message;
    }

    public GenericLoginErrorException(String message, Throwable cause, String message1) {
        super(message, cause);
        this.message = message1;
    }

    public GenericLoginErrorException(Throwable cause, String message) {
        super(cause);
        this.message = message;
    }

    public GenericLoginErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String message1) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.message = message1;
    }

    public GenericLoginErrorException(String message, String message1) {
        super(message);
        this.message = message1;
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
