package br.com.finchsolucoes.xgracco.core.handler.exception;

public class ProcessoCamposObrigatoriosException extends ValidationException {


    public ProcessoCamposObrigatoriosException() {
    }

    public ProcessoCamposObrigatoriosException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProcessoCamposObrigatoriosException(Throwable cause) {
        super(cause);
    }

    public ProcessoCamposObrigatoriosException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ProcessoCamposObrigatoriosException(String message) {
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
