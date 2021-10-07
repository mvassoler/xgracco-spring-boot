package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Validação de datas que devem considerar dias úteis
 *
 * @author paulo.marcon
 * @since 5.0.0
 */
public class DiasUteisException extends RuntimeException {

    public DiasUteisException() { }

    public DiasUteisException(String message) {
        super(message);
    }

    public DiasUteisException(String message, Throwable cause) {
        super(message, cause);
    }

}
