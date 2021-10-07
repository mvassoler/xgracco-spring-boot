package br.com.finchsolucoes.xgracco.core.handler.exception;

public class ProcessoCamposObrigatoriosException extends RuntimeException {

    public ProcessoCamposObrigatoriosException() {}

    public ProcessoCamposObrigatoriosException(String message) {
        super(message);
    }

    public ProcessoCamposObrigatoriosException(String message, Throwable cause) {
        super(message, cause);
    }

}
