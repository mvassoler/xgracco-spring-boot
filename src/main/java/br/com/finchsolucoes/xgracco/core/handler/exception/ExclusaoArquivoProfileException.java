package br.com.finchsolucoes.xgracco.core.handler.exception;

public class ExclusaoArquivoProfileException extends RuntimeException {

    public ExclusaoArquivoProfileException() {}

    public ExclusaoArquivoProfileException(String message) {
        super(message);
    }

    public ExclusaoArquivoProfileException(String message, Throwable cause) {
        super(message, cause);
    }

}
