package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Excessão para obrigatoriedade de operacional
 *
 * @author Jordano
 * @since 4.0.5
 */
public class OperacionalObrigatorioException extends RuntimeException {

    public OperacionalObrigatorioException() {}

    public OperacionalObrigatorioException(String message) {
        super(message);
    }

    public OperacionalObrigatorioException(String message, Throwable cause) {
        super(message, cause);
    }

}