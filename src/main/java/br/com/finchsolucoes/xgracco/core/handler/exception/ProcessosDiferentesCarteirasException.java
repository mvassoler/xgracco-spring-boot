package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Exceção lançada quando os processos fornecidos fazem parte de diferentes carteiras.
 *
 * @author Roberto Amadeu Neto
 * @since 5.3.0
 */
public class ProcessosDiferentesCarteirasException extends RuntimeException {

    public ProcessosDiferentesCarteirasException() {}

    public ProcessosDiferentesCarteirasException(String message) {
        super(message);
    }

    public ProcessosDiferentesCarteirasException(String message, Throwable cause) {
        super(message, cause);
    }

}
