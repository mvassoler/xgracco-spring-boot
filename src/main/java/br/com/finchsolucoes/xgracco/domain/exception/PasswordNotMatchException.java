package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Exceção para senhas não correspondentes.
 *
 * @author Rodolpho Couto
 * @since 0.1.0
 */
public class PasswordNotMatchException extends ValidationException {

    public PasswordNotMatchException() {
    }

    public PasswordNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordNotMatchException(Throwable cause) {
        super(cause);
    }

    public PasswordNotMatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public PasswordNotMatchException(String message) {
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
