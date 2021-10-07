package br.com.finchsolucoes.xgracco.core.handler.exception;

public class BadRequestException extends RuntimeException{

    public BadRequestException() { }

    public BadRequestException(String message) { super(message); }

    public BadRequestException (final String message, final Throwable cause) {
        super(message, cause);
    }


}
