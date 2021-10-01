package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * @author Jordano
 *
 * @since
 */
public class AuditoriaSemRegistrosException extends ValidationException {

    public AuditoriaSemRegistrosException() {
    }

    public AuditoriaSemRegistrosException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuditoriaSemRegistrosException(Throwable cause) {
        super(cause);
    }

    public AuditoriaSemRegistrosException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AuditoriaSemRegistrosException(String message) {
        super(message);
    }

    @Override
    public String getProperty() {
        return "decisao";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}