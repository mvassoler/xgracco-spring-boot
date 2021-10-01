package br.com.finchsolucoes.xgracco.domain.exception;

import br.com.finchsolucoes.xgracco.domain.entity.Usuario;

/**
 * Exceção para usuário bloqueado.
 *
 * @author Roberto Amadeu Neto.
 * @since 5.2.0
 */
public class UsuarioBloqueadoException extends ValidationException {
    public UsuarioBloqueadoException() {
    }

    public UsuarioBloqueadoException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsuarioBloqueadoException(Throwable cause) {
        super(cause);
    }

    public UsuarioBloqueadoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public UsuarioBloqueadoException(String message) {
        super(message);
    }

    //    public UsuarioBloqueadoException(Usuario usuario) {
//        super(Util.retornaMensagem("exception.unprocessableEntity.UsuarioBloqueadoException", usuario.getNomeRazaoSocial()));
//    }

    @Override
    public String getProperty() {
        return "bloqueado";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
