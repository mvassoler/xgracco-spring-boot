package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Exceção para tarefas pendentes
 *
 * @author paulomarcon
 * @since 2.2.0
 */
public class PendingTasksException extends ValidationException {

    @Override
    public String getProperty() {
        return "carteira";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
