package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Exceção que indica qua pessoa está vinculada a um usuário.
 *
 * @author andre.baroni
 * @since 5.2.3
 */
public class PessoaVinculadaUsuarioException extends ValidationException {
    public PessoaVinculadaUsuarioException() {
    }

    public PessoaVinculadaUsuarioException(String message, Throwable cause) {
        super(message, cause);
    }

    public PessoaVinculadaUsuarioException(Throwable cause) {
        super(cause);
    }

    public PessoaVinculadaUsuarioException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public PessoaVinculadaUsuarioException(String message) {
        super(message);
    }

    @Override
    public String getProperty() {
        return "usuarioSistema";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
