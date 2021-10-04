package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Exceção que indica qua pessoa está vinculada a um escritório.
 *
 * @author andre.baroni
 * @since 5.2.3
 */
public class PessoaVinculadaEscritorioException extends ValidationException {


    public PessoaVinculadaEscritorioException() {
    }

    public PessoaVinculadaEscritorioException(String message, Throwable cause) {
        super(message, cause);
    }

    public PessoaVinculadaEscritorioException(Throwable cause) {
        super(cause);
    }

    public PessoaVinculadaEscritorioException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public PessoaVinculadaEscritorioException(String message) {
        super(message);
    }

    @Override
    public String getProperty() {
        return "escritorio";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
