package br.com.finchsolucoes.xgracco.domain.exception;

public class AlterarCarteiraException extends ValidationException {


    public AlterarCarteiraException() {
    }

    public AlterarCarteiraException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlterarCarteiraException(Throwable cause) {
        super(cause);
    }

    public AlterarCarteiraException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AlterarCarteiraException(String message) {
        super(message);
    }

    @Override
    public String getProperty() {
        return "";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }

}
