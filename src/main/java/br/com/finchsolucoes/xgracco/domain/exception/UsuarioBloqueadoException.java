package br.com.finchsolucoes.xgracco.domain.exception;

import br.com.finchsolucoes.xgracco.domain.entity.Usuario;

/**
 * Exceção para usuário bloqueado.
 *
 * @author Roberto Amadeu Neto.
 * @since 5.2.0
 */
public class UsuarioBloqueadoException extends ValidationException {

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
