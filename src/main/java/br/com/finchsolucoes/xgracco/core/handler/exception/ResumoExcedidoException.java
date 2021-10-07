package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Exceção utilizada ao exceder o limite de caracteres do resumo do processo.
 *
 * @author Roberto Amadeu Neto
 * @since 5.2.1
 */
public class ResumoExcedidoException extends RuntimeException {

    public ResumoExcedidoException() { }

    public ResumoExcedidoException(String message) {
        super(message);
    }

    public ResumoExcedidoException(String message, Throwable cause) {
        super(message, cause);
    }

}
