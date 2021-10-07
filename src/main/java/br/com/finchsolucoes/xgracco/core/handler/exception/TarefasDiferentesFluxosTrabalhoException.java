package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Exceção lançada quando as tarefas fornecidos fazem parte de diferentes fluxos de trabalho.
 *
 * @author Roberto Amadeu Neto
 * @since 5.3.0
 */
public class TarefasDiferentesFluxosTrabalhoException extends RuntimeException {

    public TarefasDiferentesFluxosTrabalhoException() { }

    public TarefasDiferentesFluxosTrabalhoException(String message) {
        super(message);
    }

    public TarefasDiferentesFluxosTrabalhoException(String message, Throwable cause) {
        super(message, cause);
    }

}
