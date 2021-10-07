package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Exceção que indica que existe (m) desdobramentos em carteira (s) diferente (s) do processo principal.
 *
 * @author andre.baroni
 * @since 5.2.0
 */
public class DesdobramentoEmOutraCarteiraException extends RuntimeException {

    public DesdobramentoEmOutraCarteiraException() { }

    public DesdobramentoEmOutraCarteiraException(String message) {
        super(message);
    }

    public DesdobramentoEmOutraCarteiraException(String message, Throwable cause) {
        super(message, cause);
    }

}
