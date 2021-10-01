package br.com.finchsolucoes.xgracco.domain.exception;

/**
 * Exceção lançada quando uma lista de tarefas é fornecida e existe duplicidade dentro da mesma.
 *
 * @author Roberto Amadeu Neto
 * @since 5.3.0
 */
public class TarefaDuplicadaException extends ValidationException {

    private String descricao;

    public TarefaDuplicadaException(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String getProperty() {
        return descricao;
    }

    @Override
    public Object[] getObjects() {
        return new Object[]{descricao};
    }
}
