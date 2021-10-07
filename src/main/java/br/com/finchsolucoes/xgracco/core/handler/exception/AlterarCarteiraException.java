package br.com.finchsolucoes.xgracco.core.handler.exception;

public class AlterarCarteiraException extends RuntimeException {

    public AlterarCarteiraException() {}

    public AlterarCarteiraException(String message) {
        super(message);
    }

    public AlterarCarteiraException(String message, Throwable cause) {
        super(message, cause);
    }

}
