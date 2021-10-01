package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Exceção tratada para remoção de pessoa
 *
 * @author paulo.marcon
 * @since 5.0.0
 *
 */
public class RemovePessoaException extends ValidationException {

    @Override
    public String getProperty() {
        return "id";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
