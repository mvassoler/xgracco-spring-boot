package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Exceção que indica qua pessoa está vinculada a um usuário.
 *
 * @author andre.baroni
 * @since 5.2.3
 */
public class PessoaVinculadaUsuarioException extends ValidationException {

    @Override
    public String getProperty() {
        return "usuarioSistema";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
