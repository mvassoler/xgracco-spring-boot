package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Exceção para ID nulo.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
public class IdNullException extends RuntimeException {

    public IdNullException() {}

    public IdNullException(String message) {
        super(message);
    }

    public IdNullException(String message, Throwable cause) {
        super(message, cause);
    }

}
