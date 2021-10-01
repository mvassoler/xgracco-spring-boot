package br.com.finchsolucoes.xgracco.domain.exception;

public class GenericErrorException extends ValidationException {

    private Object[] objects;

    public GenericErrorException(Object[] objects) {
        this.objects = objects;
    }

    public GenericErrorException(String message, Throwable cause, Object[] objects) {
        super(message, cause);
        this.objects = objects;
    }

    public GenericErrorException(Throwable cause, Object[] objects) {
        super(cause);
        this.objects = objects;
    }

    public GenericErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object[] objects) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.objects = objects;
    }

    public GenericErrorException(String message, Object[] objects) {
        super(message);
        this.objects = objects;
    }

    @Override
    public String getProperty() {
        return "task";
    }

    @Override
    public Object[] getObjects() {
        return objects;
    }
}
