package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Exceção que indica que o processo principal está em carteira diferente do processo (desdobramento).
 *
 * @author andre.baroni
 * @since 5.2.0
 */
public class ProcessoPrincipalEmOutraCarteiraException extends RuntimeException {

    public ProcessoPrincipalEmOutraCarteiraException() {}

    public ProcessoPrincipalEmOutraCarteiraException(String message) {
        super(message);
    }

    public ProcessoPrincipalEmOutraCarteiraException(String message, Throwable cause) {
        super(message, cause);
    }

}
