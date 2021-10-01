package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * @author Thiago Fogar
 * @since
 */
public class UnauthorizedAccessException extends ValidationException {

    @Override
    public String getProperty() {
        return "";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }

}