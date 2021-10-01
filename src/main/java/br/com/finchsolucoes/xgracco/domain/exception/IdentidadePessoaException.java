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

    public IdentidadePessoaException(String message, Throwable cause, Object[] objects) {
        super(message, cause);
        this.objects = objects;
    }

    public IdentidadePessoaException(Throwable cause, Object[] objects) {
        super(cause);
        this.objects = objects;
    }

    public IdentidadePessoaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object[] objects) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.objects = objects;
    }

    public IdentidadePessoaException(String message, Object[] objects) {
        super(message);
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
