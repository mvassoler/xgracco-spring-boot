package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Exceção que indica que a propriedade percentual deve ser maior ou igual a 0
 * e menor ou igual a 100.
 *
 * @author joao.guimaraes
 * @since 5.14.0
 */
public class PercentualInvalidoException extends RuntimeException {

    public PercentualInvalidoException() {}

    public PercentualInvalidoException(String message) {
        super(message);
    }

    public PercentualInvalidoException(String message, Throwable cause) {
        super(message, cause);
    }

}
