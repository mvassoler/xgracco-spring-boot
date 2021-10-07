package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Exceção que indica qua pessoa está vinculada a um escritório.
 *
 * @author andre.baroni
 * @since 5.2.3
 */
public class PessoaVinculadaEscritorioException extends RuntimeException {

    public PessoaVinculadaEscritorioException() {}

    public PessoaVinculadaEscritorioException(String message) {
        super(message);
    }

    public PessoaVinculadaEscritorioException(String message, Throwable cause) {
        super(message, cause);
    }

}
