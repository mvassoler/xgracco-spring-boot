package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Exceção para métodos de integração com Gracco.
 *
 * @author Marcelo Aguiar
 * @since 2.1
 */
public class IntegrationException extends ValidationException {

    public IntegrationException() {
    }

    public IntegrationException(String message, Throwable cause) {
        super(message, cause);
    }

    public IntegrationException(Throwable cause) {
        super(cause);
    }

    public IntegrationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public IntegrationException(String message) {
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
