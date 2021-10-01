package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Exceção entidade não encontrada.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException() {
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

}
