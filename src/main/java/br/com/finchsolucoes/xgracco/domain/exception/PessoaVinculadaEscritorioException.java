package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Exceção que indica qua pessoa está vinculada a um escritório.
 *
 * @author andre.baroni
 * @since 5.2.3
 */
public class PessoaVinculadaEscritorioException extends ValidationException {

    @Override
    public String getProperty() {
        return "escritorio";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
