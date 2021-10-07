package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * @author Jordano
 *
 * @since
 */
public class AuditoriaSemRegistrosException extends RuntimeException {

    public AuditoriaSemRegistrosException() { }

    public AuditoriaSemRegistrosException(String message) {
        super(message);
    }

    public AuditoriaSemRegistrosException(String message, Throwable cause) {
        super(message, cause);
    }

}