package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Exceção abstrata de validação.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
public abstract class ValidationException extends RuntimeException {

    public ValidationException() {

    }

    public ValidationException(String message) {
        super(message);
    }

    /**
     * Retorna a propriedade que foi violada.
     *
     * @return
     */
    public abstract String getProperty();

    /**
     * Retorna os objetos violados que serão interpolados na mensagem.
     *
     * @return
     */
    public abstract Object[] getObjects();
}
