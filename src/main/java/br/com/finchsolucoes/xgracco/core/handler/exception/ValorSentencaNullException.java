package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Exceção para valor de sentença nulo.
 *
 * @author Paulo Marçon
 * @since 2.2.1
 */
public class ValorSentencaNullException extends RuntimeException{

    public ValorSentencaNullException() { }

    public ValorSentencaNullException(String message) {
        super(message);
    }

    public ValorSentencaNullException(String message, Throwable cause) {
        super(message, cause);
    }

}
