package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Exceção lançada quando o status final fornecido não pertence à tarefa.
 *
 * @author Roberto Amadeu Neto
 * @since 5.3.0
 */
public class StatusFinalNaoPertenceTarefaException extends ValidationException {

    @Override
    public String getProperty() {
        return null;
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
