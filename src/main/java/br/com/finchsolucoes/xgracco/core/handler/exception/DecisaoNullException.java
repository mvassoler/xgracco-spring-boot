package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Exceção para decisao nula.
 *
 * @author Paulo Marçon
 * @since 2.2.1
 */
public class DecisaoNullException extends RuntimeException {

    public DecisaoNullException() {}

    public DecisaoNullException(String message) {
        super(message);
    }

    public DecisaoNullException(String message, Throwable cause) {
        super(message, cause);
    }

}
