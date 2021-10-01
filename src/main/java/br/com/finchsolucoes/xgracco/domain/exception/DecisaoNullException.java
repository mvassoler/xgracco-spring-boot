package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Exceção para decisao nula.
 *
 * @author Paulo Marçon
 * @since 2.2.1
 */
public class DecisaoNullException extends ValidationException {

    @Override
    public String getProperty() {
        return "decisao";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
