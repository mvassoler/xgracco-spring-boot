package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Excessão para processos encerrados
 *
 * @author Raphael Moreira
 * @since 2.1
 */
public class ProcessoEncerradoException extends RuntimeException {

    public ProcessoEncerradoException() { }

    public ProcessoEncerradoException(String message) {
        super(message);
    }

    public ProcessoEncerradoException(String message, Throwable cause) {
        super(message, cause);
    }

}