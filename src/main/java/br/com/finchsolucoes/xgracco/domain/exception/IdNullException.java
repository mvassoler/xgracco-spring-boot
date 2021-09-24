package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Exceção para ID nulo.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
public class IdNullException extends ValidationException {

    @Override
    public String getProperty() {
        return "id";
    }

    @Override
    public Object[] getObjects() {
        return new Object[]{};
    }
}
