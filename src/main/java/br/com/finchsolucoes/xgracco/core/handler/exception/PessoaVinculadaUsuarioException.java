package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Exceção que indica qua pessoa está vinculada a um usuário.
 *
 * @author andre.baroni
 * @since 5.2.3
 */
public class PessoaVinculadaUsuarioException extends RuntimeException {

    public PessoaVinculadaUsuarioException() { }

    public PessoaVinculadaUsuarioException(String message) {
        super(message);
    }

    public PessoaVinculadaUsuarioException(String message, Throwable cause) {
        super(message, cause);
    }

}
