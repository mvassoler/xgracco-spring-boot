package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Exceção para papel de advogado responsável nao encontrado.
 *
 * @author Jordano
 * @since 2.2.10.6
 */
public class AdvogadoResponsavelException extends RuntimeException {

    public AdvogadoResponsavelException() {}

    public AdvogadoResponsavelException(String message) {
        super(message);
    }

    public AdvogadoResponsavelException(String message, Throwable cause) {
        super(message, cause);
    }

}
