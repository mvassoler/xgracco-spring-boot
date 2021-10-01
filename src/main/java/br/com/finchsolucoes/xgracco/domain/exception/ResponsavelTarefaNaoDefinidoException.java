package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Exceção que indica a tarefa não tem um responsável definido.
 *
 * @author andre.baroni
 * @since 5.2.5
 */
public class ResponsavelTarefaNaoDefinidoException extends ValidationException {

    public ResponsavelTarefaNaoDefinidoException() {
    }

    public ResponsavelTarefaNaoDefinidoException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResponsavelTarefaNaoDefinidoException(Throwable cause) {
        super(cause);
    }

    public ResponsavelTarefaNaoDefinidoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ResponsavelTarefaNaoDefinidoException(String message) {
        super(message);
    }

    @Override
    public String getProperty() {
        return "responsavel";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
