package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.FluxoTrabalhoTarefa;
import br.com.finchsolucoes.xgracco.domain.entity.GrupoAgendamento;
import br.com.finchsolucoes.xgracco.domain.entity.ModeloAgendamento;
import br.com.finchsolucoes.xgracco.domain.entity.Tarefa;

import java.util.Collection;
import java.util.List;

public interface ModeloAgendamentoJpaRepository {
    List<ModeloAgendamento> findByTarefas(Collection<Tarefa> tarefas);

    List<ModeloAgendamento> findByGrupoAgendamento(GrupoAgendamento grupoAgendamento);

    List<ModeloAgendamento> findByTarefa(FluxoTrabalhoTarefa fluxoTrabalhoTarefa);
}
