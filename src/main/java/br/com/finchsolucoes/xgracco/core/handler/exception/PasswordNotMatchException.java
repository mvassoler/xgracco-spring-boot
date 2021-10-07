package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Exceção para senhas não correspondentes.
 *
 * @author Rodolpho Couto
 * @since 0.1.0
 */
public class PasswordNotMatchException extends RuntimeException {

    public PasswordNotMatchException() {}

    public PasswordNotMatchException(String message) {
        super(message);
    }

    public PasswordNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }

}
