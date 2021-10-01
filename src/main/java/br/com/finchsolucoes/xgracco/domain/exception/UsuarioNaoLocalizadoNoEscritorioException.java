package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Excessão para usuario em escritório
 *
 * @author Jordano
 * @since 4.0.5
 */
public class UsuarioNaoLocalizadoNoEscritorioException extends ValidationException {

    String propriedade;

    public UsuarioNaoLocalizadoNoEscritorioException(String propriedade) {
        this.propriedade = propriedade;
    }

    @Override
    public String getProperty() {
        return propriedade;
    }

    @Override
    public Object[] getObjects() {
        return new Object[]{};
    }
}