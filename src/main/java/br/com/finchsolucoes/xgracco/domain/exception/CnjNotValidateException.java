package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Exceção para cnj inválido.
 *
 * @author paulomarcon
 * @since 2.2.0
 */
public class CnjNotValidateException extends ValidationException {

    @Override
    public String getProperty() {
        return "numero";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
