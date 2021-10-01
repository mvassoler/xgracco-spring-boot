package br.com.finchsolucoes.xgracco.domain.exception;

public class JwtException extends ValidationException {

    public JwtException() {
    }

    public JwtException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtException(Throwable cause) {
        super(cause);
    }

    public JwtException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public JwtException(String message) {
        super(message);
    }

    @Override
    public String getProperty() {
        return "jwt";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
