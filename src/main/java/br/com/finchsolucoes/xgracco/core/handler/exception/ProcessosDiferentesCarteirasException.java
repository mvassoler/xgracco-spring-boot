package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Exceção lançada quando os processos fornecidos fazem parte de diferentes carteiras.
 *
 * @author Roberto Amadeu Neto
 * @since 5.3.0
 */
public class ProcessosDiferentesCarteirasException extends ValidationException {

    public ProcessosDiferentesCarteirasException() {
    }

    public ProcessosDiferentesCarteirasException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProcessosDiferentesCarteirasException(Throwable cause) {
        super(cause);
    }

    public ProcessosDiferentesCarteirasException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ProcessosDiferentesCarteirasException(String message) {
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
