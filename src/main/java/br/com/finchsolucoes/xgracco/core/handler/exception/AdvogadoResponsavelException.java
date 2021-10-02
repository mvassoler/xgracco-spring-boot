package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Exceção para papel de advogado responsável nao encontrado.
 *
 * @author Jordano
 * @since 2.2.10.6
 */
public class AdvogadoResponsavelException extends ValidationException {

    public AdvogadoResponsavelException() {
    }

    public AdvogadoResponsavelException(String message, Throwable cause) {
        super(message, cause);
    }

    public AdvogadoResponsavelException(Throwable cause) {
        super(cause);
    }

    public AdvogadoResponsavelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AdvogadoResponsavelException(String message) {
        super(message);
    }

    @Override
    public String getProperty() {
        return "advogadoResponsavel";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
