package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.CapturaAndamento;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;

public interface CapturaAndamentoJpaRepository {
    List<CapturaAndamento> find(Query<CapturaAndamento> query);

    boolean exists(CapturaAndamento capturaAndamento);

    long count(Query<CapturaAndamento> query);
}
