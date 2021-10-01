package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Excessão para exclusão de um status final
 *
 * @author Raphael Moreira
 * @since 2.1
 */
public class RemoveTagProcessoException extends ValidationException {

    private final Object[] objects;

    public RemoveTagProcessoException(Object[] objects) {
        this.objects = objects;
    }

    @Override
    public String getProperty() {
        return "id";
    }

    @Override
    public Object[] getObjects() {
        return objects;
    }
}
