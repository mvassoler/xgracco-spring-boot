package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * @author Jordano
 *
 * @since
 */
public class AuditoriaSemRegistrosException extends ValidationException {

    @Override
    public String getProperty() {
        return "decisao";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}