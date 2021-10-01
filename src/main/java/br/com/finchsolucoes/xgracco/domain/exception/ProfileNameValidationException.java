package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Exceção para nomes de diretórios iguais.
 *
 * @author paulomarcon
 * @since 2.2.3
 */
public class ProfileNameValidationException extends ValidationException {

    @Override
    public String getProperty() {
        return "nome";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }

}
