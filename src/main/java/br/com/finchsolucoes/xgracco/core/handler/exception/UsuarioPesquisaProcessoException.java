package br.com.finchsolucoes.xgracco.core.handler.exception;

public class UsuarioPesquisaProcessoException extends RuntimeException {

    public UsuarioPesquisaProcessoException() {}

    public UsuarioPesquisaProcessoException(String message) {
        super(message);
    }

    public UsuarioPesquisaProcessoException(String message, Throwable cause) {
        super(message, cause);
    }

}
