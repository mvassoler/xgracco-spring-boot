package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Excessão no cpf/cnpj da entidade Pessoa.
 *
 * @author guilhermecamargo
 */
public class IdentidadePessoaException extends ValidationException {

    private final Object[] objects;

    public IdentidadePessoaException(Object[] objects) {
        this.objects = objects;
    }

    @Override
    public String getProperty() {
        return "id";
    }

    @Override
    public Object[] getObjects() {
        return objects;
    }
}
