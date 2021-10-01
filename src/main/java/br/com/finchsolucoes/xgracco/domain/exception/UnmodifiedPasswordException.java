package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Exceção para senhas não alterada.
 *
 * @author Rodolpho Couto
 * @since 0.1.0
 */
public class UnmodifiedPasswordException extends ValidationException {


    public UnmodifiedPasswordException() {
    }

    public UnmodifiedPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnmodifiedPasswordException(Throwable cause) {
        super(cause);
    }

    public UnmodifiedPasswordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public UnmodifiedPasswordException(String message) {
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
