package br.com.finchsolucoes.xgracco.domain.exception;

import br.com.finchsolucoes.xgracco.domain.entity.Fila;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Objects;

/**
 * Indica que a tarefa está vinculada a uma fila e portanto não é possível remove-la da esteira.
 *
 * @author andre.baroni
 * @since 5.2.0
 */
public class TarefaVinculadaAFilaException extends ValidationException {

    private final List<Fila> filas;

    public TarefaVinculadaAFilaException(List<Fila> filas) {
        super();
        this.filas = filas;
    }

    @Override
    public String getProperty() {
        return "tarefa";
    }

    @Override
    public Object[] getObjects() {
        String descricaoFilas = "";

        if (Objects.nonNull(this.filas)) {
            descricaoFilas = this.filas
                    .stream()
                    .map(fila -> fila.getDescricao().concat(", "))
                    .reduce(String::concat)
                    .get();
        }

        //retorna a string obtida ignorando os dois ultimos caracteres que serão ", ".
        return Lists.newArrayList(descricaoFilas.substring(0, descricaoFilas.length() - 2)).toArray();
    }
}
