package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Exceção para conflito de ID.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
public class IdConflictException extends RuntimeException {

    public IdConflictException() { }

    public IdConflictException(String message) {
        super(message);
    }

    public IdConflictException(String message, Throwable cause) {
        super(message, cause);
    }


}
