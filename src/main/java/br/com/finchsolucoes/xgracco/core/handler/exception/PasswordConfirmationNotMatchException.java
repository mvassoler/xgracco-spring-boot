package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Exceção para senhas não correspondentes.
 *
 * @author Rodolpho Couto
 * @since 0.1.0
 */
public class PasswordConfirmationNotMatchException extends RuntimeException {

    public PasswordConfirmationNotMatchException() {}

    public PasswordConfirmationNotMatchException(String message) {
        super(message);
    }

    public PasswordConfirmationNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }

}
