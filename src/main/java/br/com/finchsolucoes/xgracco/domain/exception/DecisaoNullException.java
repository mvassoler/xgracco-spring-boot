package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Exceção para decisao nula.
 *
 * @author Paulo Marçon
 * @since 2.2.1
 */
public class DecisaoNullException extends ValidationException {


    public DecisaoNullException() {
    }

    public DecisaoNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public DecisaoNullException(Throwable cause) {
        super(cause);
    }

    public DecisaoNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public DecisaoNullException(String message) {
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
