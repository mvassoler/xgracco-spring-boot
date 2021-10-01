package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * @author guilhermecamargo
 */
public class UpdateFilaException extends ValidationException {

    @Override
    public String getProperty() {
        return "id";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }

}
