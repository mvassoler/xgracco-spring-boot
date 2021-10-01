package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * @author Thiago Fogar
 * @since
 */
public class InvalidIndiceEconomicoException extends ValidationException {

    @Override
    public String getProperty() {
        return "dataInicial";
    }

    @Override
    public Object[] getObjects() {
        return new Object[]{};
    }
}
