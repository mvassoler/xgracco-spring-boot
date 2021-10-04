package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Validação de datas que devem considerar dias úteis
 *
 * @author paulo.marcon
 * @since 5.0.0
 */
public class DiasUteisException extends ValidationException {


    public DiasUteisException() {
    }

    public DiasUteisException(String message, Throwable cause) {
        super(message, cause);
    }

    public DiasUteisException(Throwable cause) {
        super(cause);
    }

    public DiasUteisException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public DiasUteisException(String message) {
        super(message);
    }

    @Override
    public String getProperty() {
        return "";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
