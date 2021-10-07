package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * @author guilhermecamargo
 */
public class UpdateFilaException extends RuntimeException {

    public UpdateFilaException() {}

    public UpdateFilaException(String message) {
        super(message);
    }

    public UpdateFilaException(String message, Throwable cause) {
        super(message, cause);
    }

}
