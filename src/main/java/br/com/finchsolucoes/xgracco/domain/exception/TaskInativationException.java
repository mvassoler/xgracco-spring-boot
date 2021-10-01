package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Inativação de tarefa
 *
 * @author paulo.marcon
 * @since 5.0.0
 */
public class TaskInativationException extends ValidationException {

    @Override
    public String getProperty() {
        return "id";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
