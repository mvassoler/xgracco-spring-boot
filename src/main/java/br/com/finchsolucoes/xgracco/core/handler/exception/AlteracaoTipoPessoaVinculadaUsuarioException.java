package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Exceção que indica que não é pissível alterar o tipo da pessoa que também é um usuário do sistema.
 *
 * @author andre.baroni
 * @since 5.2.3
 */
public class AlteracaoTipoPessoaVinculadaUsuarioException extends ValidationException {
    public AlteracaoTipoPessoaVinculadaUsuarioException() {
    }

    public AlteracaoTipoPessoaVinculadaUsuarioException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlteracaoTipoPessoaVinculadaUsuarioException(Throwable cause) {
        super(cause);
    }

    public AlteracaoTipoPessoaVinculadaUsuarioException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AlteracaoTipoPessoaVinculadaUsuarioException(String message) {
        super(message);
    }

    @Override
    public String getProperty() {
        return "tipo";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
