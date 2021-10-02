package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Exceção que indica que existem solicitações pendentes para o processo.
 *
 * @author andre.baroni
 * @since 5.2.0
 */
public class SolicitacaoPendenteException extends ValidationException {

    public SolicitacaoPendenteException() {
    }

    public SolicitacaoPendenteException(String message, Throwable cause) {
        super(message, cause);
    }

    public SolicitacaoPendenteException(Throwable cause) {
        super(cause);
    }

    public SolicitacaoPendenteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public SolicitacaoPendenteException(String message) {
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
