package br.com.finchsolucoes.xgracco.core.handler.exception;

public class ArquivoImportacaoException extends ValidationException {
    public ArquivoImportacaoException() {
    }

    public ArquivoImportacaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArquivoImportacaoException(Throwable cause) {
        super(cause);
    }

    public ArquivoImportacaoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ArquivoImportacaoException(String message) {
        super(message);
    }

    @Override
    public String getProperty() {
        return null;
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
