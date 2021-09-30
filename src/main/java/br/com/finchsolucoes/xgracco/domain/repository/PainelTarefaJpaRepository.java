package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.FluxoTrabalhoTarefa;
import br.com.finchsolucoes.xgracco.domain.entity.Painel;
import br.com.finchsolucoes.xgracco.domain.entity.PainelTarefa;
import br.com.finchsolucoes.xgracco.domain.entity.Tarefa;

import java.util.Collection;
import java.util.List;

public interface PainelTarefaJpaRepository {
    List<PainelTarefa> findByTarefas(Collection<Tarefa> tarefas);

    List<PainelTarefa> findTarefas(Painel painel);

    List<PainelTarefa> findByTarefa(FluxoTrabalhoTarefa fluxoTrabalhoTarefa);
}
