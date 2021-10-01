package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Exceção para conflito de ID.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
public class IdConflictException extends ValidationException {

    @Override
    public String getProperty() {
        return "id";
    }

    @Override
    public Object[] getObjects() {
        return new Object[]{};
    }
}
