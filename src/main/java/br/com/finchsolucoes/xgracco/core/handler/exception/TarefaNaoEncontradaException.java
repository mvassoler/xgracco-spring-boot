package br.com.finchsolucoes.xgracco.core.handler.exception;

public class TarefaNaoEncontradaException extends RuntimeException {


    private String tarefa;

    public TarefaNaoEncontradaException(String tarefa) {
        this.tarefa = tarefa;
    }

    public TarefaNaoEncontradaException(String message, Throwable cause, String tarefa) {
        super(message, cause);
        this.tarefa = tarefa;
    }

    public TarefaNaoEncontradaException(Throwable cause, String tarefa) {
        super(cause);
        this.tarefa = tarefa;
    }

    public TarefaNaoEncontradaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String tarefa) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.tarefa = tarefa;
    }

    public TarefaNaoEncontradaException(String message, String tarefa) {
        super(message);
        this.tarefa = tarefa;
    }

}
