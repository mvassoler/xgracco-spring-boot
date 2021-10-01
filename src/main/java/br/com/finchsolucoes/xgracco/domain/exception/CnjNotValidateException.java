package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Exceção para cnj inválido.
 *
 * @author paulomarcon
 * @since 2.2.0
 */
public class CnjNotValidateException extends ValidationException {


    public CnjNotValidateException() {
    }

    public CnjNotValidateException(String message, Throwable cause) {
        super(message, cause);
    }

    public CnjNotValidateException(Throwable cause) {
        super(cause);
    }

    public CnjNotValidateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public CnjNotValidateException(String message) {
        super(message);
    }

    @Override
    public String getProperty() {
        return "numero";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
