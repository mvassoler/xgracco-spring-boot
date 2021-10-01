package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Exceção que indica que não é pissível alterar o tipo da pessoa que está vinculada a um escritório.
 *
 * @author andre.baroni
 * @since 5.2.3
 */
public class AlteracaoTipoPessoaVinculadaEscritorioException extends ValidationException {

    public AlteracaoTipoPessoaVinculadaEscritorioException() {
    }

    public AlteracaoTipoPessoaVinculadaEscritorioException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlteracaoTipoPessoaVinculadaEscritorioException(Throwable cause) {
        super(cause);
    }

    public AlteracaoTipoPessoaVinculadaEscritorioException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AlteracaoTipoPessoaVinculadaEscritorioException(String message) {
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
