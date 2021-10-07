package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Exceção para ID não nulo.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
public class IdNotNullException extends RuntimeException {

    public IdNotNullException() { }

    public IdNotNullException(String message) {
        super(message);
    }

    public IdNotNullException(String message, Throwable cause) {
        super(message, cause);
    }

}
