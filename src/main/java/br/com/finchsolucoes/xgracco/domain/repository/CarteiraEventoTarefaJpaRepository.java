package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.CarteiraEventoTarefa;
import br.com.finchsolucoes.xgracco.domain.entity.FluxoTrabalhoTarefa;
import br.com.finchsolucoes.xgracco.domain.entity.Tarefa;

import java.util.Collection;
import java.util.List;

public interface CarteiraEventoTarefaJpaRepository {
    List<CarteiraEventoTarefa> findByTarefas(Collection<Tarefa> tarefas);

    List<CarteiraEventoTarefa> findByTarefa(FluxoTrabalhoTarefa fluxoTrabalhoTarefa);
}
