package br.com.finchsolucoes.xgracco.core.handler.exception;

public class UsuarioPesquisaProcessoException extends ValidationException {

    public UsuarioPesquisaProcessoException() {
    }

    public UsuarioPesquisaProcessoException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsuarioPesquisaProcessoException(Throwable cause) {
        super(cause);
    }

    public UsuarioPesquisaProcessoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public UsuarioPesquisaProcessoException(String message) {
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
