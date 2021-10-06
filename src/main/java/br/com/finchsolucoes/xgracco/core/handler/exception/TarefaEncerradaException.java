package br.com.finchsolucoes.xgracco.core.handler.exception;

public class TarefaEncerradaException extends ValidationException {


    private String tarefa;

    public TarefaEncerradaException(String tarefa) {
        this.tarefa = tarefa;
    }

    public TarefaEncerradaException(String message, Throwable cause, String tarefa) {
        super(message, cause);
        this.tarefa = tarefa;
    }

    public TarefaEncerradaException(Throwable cause, String tarefa) {
        super(cause);
        this.tarefa = tarefa;
    }

    public TarefaEncerradaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String tarefa) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.tarefa = tarefa;
    }

    public TarefaEncerradaException(String message, String tarefa) {
        super(message);
        this.tarefa = tarefa;
    }

    @Override
    public String getProperty() {
        return tarefa;
    }

    @Override
    public Object[] getObjects() {
        return new Object[]{tarefa};
    }
}
