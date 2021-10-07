package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Exceção para usuário bloqueado.
 *
 * @author Roberto Amadeu Neto.
 * @since 5.2.0
 */
public class UsuarioBloqueadoException extends RuntimeException {

    public UsuarioBloqueadoException() {}

    public UsuarioBloqueadoException(String message) {
        super(message);
    }

    public UsuarioBloqueadoException(String message, Throwable cause) {
        super(message, cause);
    }

}
