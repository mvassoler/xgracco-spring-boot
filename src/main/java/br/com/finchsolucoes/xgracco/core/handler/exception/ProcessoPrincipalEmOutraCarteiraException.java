package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Exceção que indica que o processo principal está em carteira diferente do processo (desdobramento).
 *
 * @author andre.baroni
 * @since 5.2.0
 */
public class ProcessoPrincipalEmOutraCarteiraException extends ValidationException {


    public ProcessoPrincipalEmOutraCarteiraException() {
    }

    public ProcessoPrincipalEmOutraCarteiraException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProcessoPrincipalEmOutraCarteiraException(Throwable cause) {
        super(cause);
    }

    public ProcessoPrincipalEmOutraCarteiraException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ProcessoPrincipalEmOutraCarteiraException(String message) {
        super(message);
    }

    @Override
    public String getProperty() {
        return "carteira";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
