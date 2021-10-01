package br.com.finchsolucoes.xgracco.domain.exception;

public class TarefaVinculadaEsteiraException extends ValidationException{

    private final Object[] objects;

    public TarefaVinculadaEsteiraException(String tarefa, String carteira, String esteira) {
        this.objects = new Object[]{tarefa, carteira, esteira};
    }

    @Override
    public String getProperty() {
        return "id";
    }

    @Override
    public Object[] getObjects() {
        return objects;
    }

}
