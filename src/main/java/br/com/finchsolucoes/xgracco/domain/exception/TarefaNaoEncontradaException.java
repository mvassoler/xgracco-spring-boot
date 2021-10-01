package br.com.finchsolucoes.xgracco.domain.exception;

public class TarefaNaoEncontradaException extends ValidationException {

    private String tarefa;

    public TarefaNaoEncontradaException(String tarefa) {
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
