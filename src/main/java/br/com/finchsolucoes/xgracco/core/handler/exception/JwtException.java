package br.com.finchsolucoes.xgracco.core.handler.exception;

public class JwtException extends RuntimeException {

    public JwtException() {}

    public JwtException(String message) {
        super(message);
    }

    public JwtException(String message, Throwable cause) {
        super(message, cause);
    }

}
