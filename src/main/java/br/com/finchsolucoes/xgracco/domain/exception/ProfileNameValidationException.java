package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Exceção para nomes de diretórios iguais.
 *
 * @author paulomarcon
 * @since 2.2.3
 */
public class ProfileNameValidationException extends ValidationException {

    public ProfileNameValidationException() {
    }

    public ProfileNameValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProfileNameValidationException(Throwable cause) {
        super(cause);
    }

    public ProfileNameValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ProfileNameValidationException(String message) {
        super(message);
    }


    @Override
    public String getProperty() {
        return "nome";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }

}
