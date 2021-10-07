package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Exceção que indica que existem desdobramento do processo ativos.
 *
 * @author andre.baroni
 * @since 5.2.0
 */
public class DesdobramentoPendenteException extends RuntimeException {

    public DesdobramentoPendenteException() { }

    public DesdobramentoPendenteException(String message) {
        super(message);
    }

    public DesdobramentoPendenteException(String message, Throwable cause) {
        super(message, cause);
    }

}
