package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Exceção para entidade que não pode ser excluída.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
public class EntityCannotBeRemovedException extends ValidationException {


    @Override
    public String getProperty() {
        return "entidadenaopodeserremovida";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
