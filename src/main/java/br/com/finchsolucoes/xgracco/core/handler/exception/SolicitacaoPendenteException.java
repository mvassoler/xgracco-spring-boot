package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Exceção que indica que existem solicitações pendentes para o processo.
 *
 * @author andre.baroni
 * @since 5.2.0
 */
public class SolicitacaoPendenteException extends RuntimeException {

    public SolicitacaoPendenteException() {}

    public SolicitacaoPendenteException(String message) {
        super(message);
    }

    public SolicitacaoPendenteException(String message, Throwable cause) {
        super(message, cause);
    }

}
