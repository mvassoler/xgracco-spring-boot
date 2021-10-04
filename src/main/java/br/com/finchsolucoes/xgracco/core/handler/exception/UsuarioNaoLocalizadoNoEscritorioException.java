package br.com.finchsolucoes.xgracco.core.handler.exception;

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

    public UsuarioNaoLocalizadoNoEscritorioException(String message, Throwable cause, String propriedade) {
        super(message, cause);
        this.propriedade = propriedade;
    }

    public UsuarioNaoLocalizadoNoEscritorioException(Throwable cause, String propriedade) {
        super(cause);
        this.propriedade = propriedade;
    }

    public UsuarioNaoLocalizadoNoEscritorioException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String propriedade) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.propriedade = propriedade;
    }

    public UsuarioNaoLocalizadoNoEscritorioException(String message, String propriedade) {
        super(message);
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