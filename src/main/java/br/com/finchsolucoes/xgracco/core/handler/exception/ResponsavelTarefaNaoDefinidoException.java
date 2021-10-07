package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Exceção que indica a tarefa não tem um responsável definido.
 *
 * @author andre.baroni
 * @since 5.2.5
 */
public class ResponsavelTarefaNaoDefinidoException extends RuntimeException {

    public ResponsavelTarefaNaoDefinidoException() {}

    public ResponsavelTarefaNaoDefinidoException(String message) {
        super(message);
    }

    public ResponsavelTarefaNaoDefinidoException(String message, Throwable cause) {
        super(message, cause);
    }

}
