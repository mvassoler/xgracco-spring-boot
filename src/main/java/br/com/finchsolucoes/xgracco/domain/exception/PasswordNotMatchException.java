package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Exceção para senhas não correspondentes.
 *
 * @author Rodolpho Couto
 * @since 0.1.0
 */
public class PasswordNotMatchException extends ValidationException {

    @Override
    public String getProperty() {
        return "senha";
    }

    @Override
    public Object[] getObjects() {
        return new Object[]{};
    }
}
