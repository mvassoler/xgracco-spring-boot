package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;

import java.util.Collection;
import java.util.List;

public interface FilaTarefaJpaRepository {
    List<FilaTarefa> findByFila(Fila fila);

    List<FilaTarefa> findByTarefas(Collection<Tarefa> tarefas);

    List<Fila> findByEsteiraAndTarefa(Esteira esteira, FluxoTrabalhoTarefa fluxoTrabalhoTarefa);
}
