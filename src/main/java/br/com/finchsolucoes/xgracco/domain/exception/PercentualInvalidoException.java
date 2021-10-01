package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Exceção que indica que a propriedade percentual deve ser maior ou igual a 0
 * e menor ou igual a 100.
 *
 * @author joao.guimaraes
 * @since 5.14.0
 */
public class PercentualInvalidoException extends ValidationException {

    @Override
    public String getProperty() {
        return "percentual";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
