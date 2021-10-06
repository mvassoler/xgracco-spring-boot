package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Exceção que indica que existe (m) desdobramentos em carteira (s) diferente (s) do processo principal.
 *
 * @author andre.baroni
 * @since 5.2.0
 */
public class DesdobramentoEmOutraCarteiraException extends ValidationException {


    public DesdobramentoEmOutraCarteiraException() {
    }

    public DesdobramentoEmOutraCarteiraException(String message, Throwable cause) {
        super(message, cause);
    }

    public DesdobramentoEmOutraCarteiraException(Throwable cause) {
        super(cause);
    }

    public DesdobramentoEmOutraCarteiraException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public DesdobramentoEmOutraCarteiraException(String message) {
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
