package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Exceção para cnj inválido.
 *
 * @author paulomarcon
 * @since 2.2.0
 */
public class CnjNotValidateException extends RuntimeException {

    public CnjNotValidateException() { }

    public CnjNotValidateException(String message) {
        super(message);
    }

    public CnjNotValidateException(String message, Throwable cause) {
        super(message, cause);
    }

}
