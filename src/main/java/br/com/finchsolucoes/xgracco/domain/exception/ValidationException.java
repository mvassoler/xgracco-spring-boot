package br.com.finchsolucoes.xgracco.domain.exception;

public abstract class ValidationException extends RuntimeException {
    public ValidationException() {

    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }

    public ValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
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
