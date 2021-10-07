package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Exceção tratada para remoção de pessoa
 *
 * @author paulo.marcon
 * @since 5.0.0
 *
 */
public class RemovePessoaException extends RuntimeException {

    public RemovePessoaException() {}

    public RemovePessoaException(String message) {
        super(message);
    }

    public RemovePessoaException(String message, Throwable cause) {
        super(message, cause);
    }

}
