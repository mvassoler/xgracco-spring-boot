package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Excessão para exclusão de um perfil
 *
 * @author Renan Peres
 * @since 2.1
 */

public class RemoverPerfilException extends ValidationException{
    private final Object[] objects;

    public RemoverPerfilException(Object[] objects) {
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
