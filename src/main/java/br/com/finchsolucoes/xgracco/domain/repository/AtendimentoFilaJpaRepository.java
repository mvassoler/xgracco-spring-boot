package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.enums.EnumStatusTarefa;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTarefaStatus;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public interface AtendimentoFilaJpaRepository {
    List<AtendimentoFila> find(Query<AtendimentoFila> query, List<EnumStatusTarefa> enumStatusTarefaList, EnumTarefaStatus enumTarefaStatus);

    Optional<AtendimentoFila> findProximaTarefaDisponivel(Query<AtendimentoFila> query);

    Long count(Query<AtendimentoFila> query, List<EnumStatusTarefa> enumStatusTarefaList, EnumTarefaStatus enumTarefaStatus);

    Optional<AtendimentoFila> findDevolucaoByDadosBasicosTarefa(DadosBasicosTarefa dadosBasicosTarefa);

    Optional<AtendimentoFila> findByDadosBasicosTarefa(DadosBasicosTarefa dadosBasicosTarefa);

    Optional<DadosBasicosTarefa> findTarefaNaoConcluidaByUsuarioAndFila(Pessoa pessoa, Fila fila);

    Long countTarefasPrioritariasTratadasHoje(Fila fila);

    Long countTarefasPrioridadeConcluidasHoje(Long idFila);

    List<HistoricoFilaPessoa> consultaSolicitacoesConcluidasDataPessoaFila(Long idFila, Long idPessoa, Calendar dataInicio, Calendar dataFim);
}
