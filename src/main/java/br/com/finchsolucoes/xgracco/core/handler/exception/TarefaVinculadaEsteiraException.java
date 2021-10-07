package br.com.finchsolucoes.xgracco.core.handler.exception;

public class TarefaVinculadaEsteiraException extends RuntimeException{


    public TarefaVinculadaEsteiraException(Object[] objects) {
        this.objects = objects;
    }

    public TarefaVinculadaEsteiraException(String message, Throwable cause, Object[] objects) {
        super(message, cause);
        this.objects = objects;
    }

    public TarefaVinculadaEsteiraException(Throwable cause, Object[] objects) {
        super(cause);
        this.objects = objects;
    }

    public TarefaVinculadaEsteiraException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object[] objects) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.objects = objects;
    }

    public TarefaVinculadaEsteiraException(String message, Object[] objects) {
        super(message);
        this.objects = objects;
    }

    private final Object[] objects;

    public TarefaVinculadaEsteiraException(String tarefa, String carteira, String esteira) {
        this.objects = new Object[]{tarefa, carteira, esteira};
    }

}
