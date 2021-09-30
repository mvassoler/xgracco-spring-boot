package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Agenda;
import br.com.finchsolucoes.xgracco.domain.entity.DadosBasicosTarefa;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public interface AgendaJpaRepository {
    List<Agenda> find(Query<Agenda> query);

    long count(Query<Agenda> query);

    Optional<Agenda> findByDadosBasicosTarefa(DadosBasicosTarefa dadosBasicosTarefa);

    void removeByDadosBasicosTarefa(DadosBasicosTarefa dadosBasicosTarefa);

    Long countAgendamentosVencidosPorUsuario(Calendar dataPrazo, Long idUsuario);

    Long countAgendamentosEntreDatasPorUsuario(Long idUsuario, Calendar dataInicio, Calendar dataFim);

    Long countAgendamentosFuturos(Long idUsuario, Calendar dataInicio);

    Long countAgendamentoEntreDatasPorEscritorioCarteiras(Calendar dataInicial, Calendar dataFinal, List<Long> carteiras, List<Long> idsEscritorio);
}
