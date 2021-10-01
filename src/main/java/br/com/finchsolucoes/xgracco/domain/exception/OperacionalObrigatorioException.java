package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Excessão para obrigatoriedade de operacional
 *
 * @author Jordano
 * @since 4.0.5
 */
public class OperacionalObrigatorioException extends ValidationException {

    @Override
    public String getProperty() {
        return "operacional";
    }

    @Override
    public Object[] getObjects() {
        return new Object[]{};
    }
}