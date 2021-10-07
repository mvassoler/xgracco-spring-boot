package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Exceção lançada quando o status final fornecido não pertence à tarefa.
 *
 * @author Roberto Amadeu Neto
 * @since 5.3.0
 */
public class StatusFinalNaoPertenceTarefaException extends RuntimeException {

    public StatusFinalNaoPertenceTarefaException() {}

    public StatusFinalNaoPertenceTarefaException(String message) {
        super(message);
    }

    public StatusFinalNaoPertenceTarefaException(String message, Throwable cause) {
        super(message, cause);
    }

}
