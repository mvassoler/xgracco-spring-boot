package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Exceção para senhas não alterada.
 *
 * @author Rodolpho Couto
 * @since 0.1.0
 */
public class UnmodifiedPasswordException extends RuntimeException {

    public UnmodifiedPasswordException() {}

    public UnmodifiedPasswordException(String message) {
        super(message);
    }

    public UnmodifiedPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

}
