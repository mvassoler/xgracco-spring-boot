package br.com.finchsolucoes.xgracco.domain.exception;

public class UsuarioPesquisaProcessoException extends ValidationException {

    @Override
    public String getProperty() {
        return "";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }

}
