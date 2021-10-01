package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Exceção para valor de sentença nulo.
 *
 * @author Paulo Marçon
 * @since 2.2.1
 */
public class ValorSentencaNullException extends ValidationException {


    @Override
    public String getProperty() {
        return "valorSentenca";
    }

    @Override
    public Object[] getObjects() {
        return new Object[0];
    }
}
