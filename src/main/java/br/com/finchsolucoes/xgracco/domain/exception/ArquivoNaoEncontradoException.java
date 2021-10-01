package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * @author Thiago Fogar
 * @since
 */
public class ArquivoNaoEncontradoException extends ValidationException {

    @Override
    public String getProperty() {
        return "decisao";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}