package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * @author guicamargo
 */
public class RemoveSistemaVirtualException extends ValidationException {

    @Override
    public String getProperty() {
        return "id";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }

}
