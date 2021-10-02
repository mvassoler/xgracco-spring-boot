package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Excessão para processos encerrados
 *
 * @author Raphael Moreira
 * @since 2.1
 */
public class ProcessoEncerradoException extends ValidationException {


    public ProcessoEncerradoException() {
    }

    public ProcessoEncerradoException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProcessoEncerradoException(Throwable cause) {
        super(cause);
    }

    public ProcessoEncerradoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ProcessoEncerradoException(String message) {
        super(message);
    }

    @Override
    public String getProperty() {
        return "id";
    }

    @Override
    public Object[] getObjects() {
        return new Object[]{};
    }
}