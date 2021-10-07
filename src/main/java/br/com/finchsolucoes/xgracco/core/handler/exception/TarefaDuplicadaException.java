package br.com.finchsolucoes.xgracco.core.handler.exception;

/**
 * Exceção lançada quando uma lista de tarefas é fornecida e existe duplicidade dentro da mesma.
 *
 * @author Roberto Amadeu Neto
 * @since 5.3.0
 */
public class TarefaDuplicadaException extends RuntimeException {

    private String descricao;

    public TarefaDuplicadaException(String descricao) {
        this.descricao = descricao;
    }

    public TarefaDuplicadaException(String message, Throwable cause, String descricao) {
        super(message, cause);
        this.descricao = descricao;
    }

    public TarefaDuplicadaException(Throwable cause, String descricao) {
        super(cause);
        this.descricao = descricao;
    }

    public TarefaDuplicadaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String descricao) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.descricao = descricao;
    }

    public TarefaDuplicadaException(String message, String descricao) {
        super(message);
        this.descricao = descricao;
    }

}
