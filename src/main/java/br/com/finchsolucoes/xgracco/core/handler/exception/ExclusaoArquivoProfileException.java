package br.com.finchsolucoes.xgracco.core.handler.exception;

public class ExclusaoArquivoProfileException extends ValidationException {


    public ExclusaoArquivoProfileException() {
    }

    public ExclusaoArquivoProfileException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExclusaoArquivoProfileException(Throwable cause) {
        super(cause);
    }

    public ExclusaoArquivoProfileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ExclusaoArquivoProfileException(String message) {
        super(message);
    }

    @Override
    public String getProperty() {
        return "exclusao";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
