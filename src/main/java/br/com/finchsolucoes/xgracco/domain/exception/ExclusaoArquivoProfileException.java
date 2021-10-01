package br.com.finchsolucoes.xgracco.domain.exception;

public class ExclusaoArquivoProfileException extends ValidationException {


    @Override
    public String getProperty() {
        return "exclusao";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
