package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.CapturaAndamento;
import br.com.finchsolucoes.xgracco.domain.entity.CapturaAndamentoProcesso;
import br.com.finchsolucoes.xgracco.domain.entity.Processo;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import com.querydsl.jpa.impl.JPAQuery;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CapturaAndamentoProcessoJpaRepository {

    List<CapturaAndamentoProcesso> find(Query<CapturaAndamentoProcesso> query);

    long count(Query<CapturaAndamentoProcesso> query);

    Long findQuantidadeTotalProcesso(CapturaAndamento capturaAndamento);

    Collection<CapturaAndamentoProcesso> findAtivosByGrupoCapturaAndamento(CapturaAndamento capturaAndamento);

    Collection<CapturaAndamentoProcesso> findByGrupoCapturaAndamento(CapturaAndamento capturaAndamento);

    Optional<CapturaAndamentoProcesso> findByGrupoCapturaAndamentoAndProcesso(CapturaAndamento capturaAndamento, Processo processo);

    void applyUserVisualizationFilter(JPAQuery jpaQuery);
}
