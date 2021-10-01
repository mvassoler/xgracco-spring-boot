package br.com.finchsolucoes.xgracco.domain.exception;

public class TarefaEncerradaException extends ValidationException {

    private String tarefa;

    public TarefaEncerradaException(String tarefa) {
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
