package br.com.finchsolucoes.xgracco.core.handler.exception;

public class FileNameValidationException extends ValidationException {

    private final Object[] objects;

    public FileNameValidationException(Object... objects) {
        super(returnMensageValidation(objects));
        this.objects = objects;
    }

    public FileNameValidationException(String message, Throwable cause, Object[] objects) {
        super(message, cause);
        this.objects = objects;
    }

    public FileNameValidationException(Throwable cause, Object[] objects) {
        super(cause);
        this.objects = objects;
    }

    public FileNameValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object[] objects) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.objects = objects;
    }

    public FileNameValidationException(String message, Object[] objects) {
        super(message);
        this.objects = objects;
    }

    @Override
    public String getProperty() {
        return "id";
    }

    @Override
    public Object[] getObjects() {
        return objects;
    }

    private static String returnMensageValidation(Object... objects){
        StringBuilder msg = new StringBuilder();
        for (Object o : objects) {
            msg.append(o);
            msg.append(" ");
        }
        return msg.toString();
    }

}
