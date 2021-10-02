package br.com.finchsolucoes.xgracco.core.handler.exception;

public class IndiceEconomicoConflictException extends ValidationException {


    private final Object[] objects;

    public IndiceEconomicoConflictException(Object[] objects) {
        this.objects = objects;
    }

    public IndiceEconomicoConflictException(String message, Throwable cause, Object[] objects) {
        super(message, cause);
        this.objects = objects;
    }

    public IndiceEconomicoConflictException(Throwable cause, Object[] objects) {
        super(cause);
        this.objects = objects;
    }

    public IndiceEconomicoConflictException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object[] objects) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.objects = objects;
    }

    public IndiceEconomicoConflictException(String message, Object[] objects) {
        super(message);
        this.objects = objects;
    }

    @Override
    public String getProperty() {
        return "descricao";
    }

    @Override
    public Object[] getObjects() {
        return objects;
    }

}
