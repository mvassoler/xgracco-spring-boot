package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Exceção que indica que não é pissível alterar o tipo da pessoa que está vinculada a um escritório.
 *
 * @author andre.baroni
 * @since 5.2.3
 */
public class AlteracaoTipoPessoaVinculadaEscritorioException extends RuntimeException {

    public AlteracaoTipoPessoaVinculadaEscritorioException() { }

    public AlteracaoTipoPessoaVinculadaEscritorioException(String message) {
        super(message);
    }

    public AlteracaoTipoPessoaVinculadaEscritorioException(String message, Throwable cause) {
        super(message, cause);
    }

}
