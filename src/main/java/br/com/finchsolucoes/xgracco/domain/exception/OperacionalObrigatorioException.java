package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Excessão para obrigatoriedade de operacional
 *
 * @author Jordano
 * @since 4.0.5
 */
public class OperacionalObrigatorioException extends ValidationException {
    public OperacionalObrigatorioException() {
    }

    public OperacionalObrigatorioException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperacionalObrigatorioException(Throwable cause) {
        super(cause);
    }

    public OperacionalObrigatorioException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public OperacionalObrigatorioException(String message) {
        super(message);
    }

    @Override
    public String getProperty() {
        return "operacional";
    }

    @Override
    public Object[] getObjects() {
        return new Object[]{};
    }
}