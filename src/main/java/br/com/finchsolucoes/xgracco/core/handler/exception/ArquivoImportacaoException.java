package br.com.finchsolucoes.xgracco.core.handler.exception;

public class ArquivoImportacaoException extends RuntimeException {

    public ArquivoImportacaoException() {}

    public ArquivoImportacaoException(String message) {
        super(message);
    }

    public ArquivoImportacaoException(String message, Throwable cause) {
        super(message, cause);
    }

}
