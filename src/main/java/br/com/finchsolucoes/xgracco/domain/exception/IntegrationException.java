package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Exceção para métodos de integração com Gracco.
 *
 * @author Marcelo Aguiar
 * @since 2.1
 */
public class IntegrationException extends RuntimeException {

    public IntegrationException(String message) {
        super(message);
    }
}
