package br.com.finchsolucoes.xgracco.domain.exception;

public class JwtException extends ValidationException {

    @Override
    public String getProperty() {
        return "jwt";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
