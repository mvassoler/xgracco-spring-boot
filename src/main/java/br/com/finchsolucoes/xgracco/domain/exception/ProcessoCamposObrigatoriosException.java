package br.com.finchsolucoes.xgracco.domain.exception;

public class ProcessoCamposObrigatoriosException extends ValidationException {

    @Override
    public String getProperty() {
        return "id";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
