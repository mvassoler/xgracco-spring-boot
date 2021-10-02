package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Exceção lançada quando as tarefas fornecidos fazem parte de diferentes fluxos de trabalho.
 *
 * @author Roberto Amadeu Neto
 * @since 5.3.0
 */
public class TarefasDiferentesFluxosTrabalhoException extends ValidationException {


    public TarefasDiferentesFluxosTrabalhoException() {
    }

    public TarefasDiferentesFluxosTrabalhoException(String message, Throwable cause) {
        super(message, cause);
    }

    public TarefasDiferentesFluxosTrabalhoException(Throwable cause) {
        super(cause);
    }

    public TarefasDiferentesFluxosTrabalhoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public TarefasDiferentesFluxosTrabalhoException(String message) {
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
