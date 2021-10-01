package br.com.finchsolucoes.xgracco.domain.exception;

public class RemoveEscritorioException extends ValidationException{


    public RemoveEscritorioException() {
    }

    public RemoveEscritorioException(String message, Throwable cause) {
        super(message, cause);
    }

    public RemoveEscritorioException(Throwable cause) {
        super(cause);
    }

    public RemoveEscritorioException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public RemoveEscritorioException(String message) {
        super(message);
    }

    @Override
    public String getProperty() {
        return "id";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
