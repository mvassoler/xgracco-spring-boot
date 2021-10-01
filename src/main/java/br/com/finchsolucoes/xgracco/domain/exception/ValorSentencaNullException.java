package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Exceção para valor de sentença nulo.
 *
 * @author Paulo Marçon
 * @since 2.2.1
 */
public class ValorSentencaNullException extends ValidationException

{

    public ValorSentencaNullException() {
    }

    public ValorSentencaNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValorSentencaNullException(Throwable cause) {
        super(cause);
    }

    public ValorSentencaNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ValorSentencaNullException(String message) {
        super(message);
    }

    @Override
    public String getProperty() {
        return "valorSentenca";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
