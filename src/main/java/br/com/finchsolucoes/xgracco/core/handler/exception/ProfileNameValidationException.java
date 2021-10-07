package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Exceção para nomes de diretórios iguais.
 *
 * @author paulomarcon
 * @since 2.2.3
 */
public class ProfileNameValidationException extends RuntimeException {

    public ProfileNameValidationException() { }

    public ProfileNameValidationException(String message) {
        super(message);
    }

    public ProfileNameValidationException(String message, Throwable cause) {
        super(message, cause);
    }

}
