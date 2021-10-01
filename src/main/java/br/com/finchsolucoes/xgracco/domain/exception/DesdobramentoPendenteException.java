package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Exceção que indica que existem desdobramento do processo ativos.
 *
 * @author andre.baroni
 * @since 5.2.0
 */
public class DesdobramentoPendenteException extends ValidationException {

    public DesdobramentoPendenteException() {
    }

    public DesdobramentoPendenteException(String message, Throwable cause) {
        super(message, cause);
    }

    public DesdobramentoPendenteException(Throwable cause) {
        super(cause);
    }

    public DesdobramentoPendenteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public DesdobramentoPendenteException(String message) {
        super(message);
    }

    @Override
    public String getProperty() {
        return "status";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
