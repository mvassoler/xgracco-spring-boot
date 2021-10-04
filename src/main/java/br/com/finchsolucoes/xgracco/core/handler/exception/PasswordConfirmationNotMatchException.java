package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Exceção para senhas não correspondentes.
 *
 * @author Rodolpho Couto
 * @since 0.1.0
 */
public class PasswordConfirmationNotMatchException extends ValidationException {

    public PasswordConfirmationNotMatchException() {
    }

    public PasswordConfirmationNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordConfirmationNotMatchException(Throwable cause) {
        super(cause);
    }

    public PasswordConfirmationNotMatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public PasswordConfirmationNotMatchException(String message) {
        super(message);
    }

    @Override
    public String getProperty() {
        return "senha";
    }

    @Override
    public Object[] getObjects() {
        return new Object[]{};
    }
}
