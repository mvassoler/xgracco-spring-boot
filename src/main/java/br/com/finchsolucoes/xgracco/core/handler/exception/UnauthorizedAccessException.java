package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * @author Thiago Fogar
 * @since
 */
public class UnauthorizedAccessException extends RuntimeException {

    public UnauthorizedAccessException() {  }

    public UnauthorizedAccessException(String message) {
        super(message);
    }

    public UnauthorizedAccessException(String message, Throwable cause) {
        super(message, cause);
    }

}