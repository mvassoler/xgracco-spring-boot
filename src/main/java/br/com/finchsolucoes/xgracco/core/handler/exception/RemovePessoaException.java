package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Exceção tratada para remoção de pessoa
 *
 * @author paulo.marcon
 * @since 5.0.0
 *
 */
public class RemovePessoaException extends ValidationException {

    public RemovePessoaException() {
    }

    public RemovePessoaException(String message, Throwable cause) {
        super(message, cause);
    }

    public RemovePessoaException(Throwable cause) {
        super(cause);
    }

    public RemovePessoaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public RemovePessoaException(String message) {
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
