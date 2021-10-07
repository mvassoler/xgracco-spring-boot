package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Excessão para processos encerrados
 *
 * @author Bruno Thiago
 * @since 5.7.0.5
 */
public class PosicaoParteDuplicadaException extends RuntimeException {

    public PosicaoParteDuplicadaException() {}

    public PosicaoParteDuplicadaException(String message) {
        super(message);
    }

    public PosicaoParteDuplicadaException(String message, Throwable cause) {
        super(message, cause);
    }

}
