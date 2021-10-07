package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * @author Thiago Fogar
 * @since
 */
public class ArquivoNaoEncontradoException extends RuntimeException {

    public ArquivoNaoEncontradoException() {}

    public ArquivoNaoEncontradoException(String message) {
        super(message);
    }

    public ArquivoNaoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }

}