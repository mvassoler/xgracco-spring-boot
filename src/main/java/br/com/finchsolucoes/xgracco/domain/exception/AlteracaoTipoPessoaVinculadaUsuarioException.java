package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Exceção que indica que não é pissível alterar o tipo da pessoa que também é um usuário do sistema.
 *
 * @author andre.baroni
 * @since 5.2.3
 */
public class AlteracaoTipoPessoaVinculadaUsuarioException extends ValidationException {

    @Override
    public String getProperty() {
        return "tipo";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
