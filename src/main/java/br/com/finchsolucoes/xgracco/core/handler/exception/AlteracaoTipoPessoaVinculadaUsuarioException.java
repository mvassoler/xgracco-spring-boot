package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Exceção que indica que não é pissível alterar o tipo da pessoa que também é um usuário do sistema.
 *
 * @author andre.baroni
 * @since 5.2.3
 */
public class AlteracaoTipoPessoaVinculadaUsuarioException extends RuntimeException {

    public AlteracaoTipoPessoaVinculadaUsuarioException() { }

    public AlteracaoTipoPessoaVinculadaUsuarioException(String message) {
        super(message);
    }

    public AlteracaoTipoPessoaVinculadaUsuarioException(String message, Throwable cause) {
        super(message, cause);
    }

}
