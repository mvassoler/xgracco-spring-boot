package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * @author Thiago Fogar
 * @since
 */
public class ArquivoNaoEncontradoException extends ValidationException {

    public ArquivoNaoEncontradoException() {
    }

    public ArquivoNaoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArquivoNaoEncontradoException(Throwable cause) {
        super(cause);
    }

    public ArquivoNaoEncontradoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ArquivoNaoEncontradoException(String message) {
        super(message);
    }

    @Override
    public String getProperty() {
        return "decisao";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}