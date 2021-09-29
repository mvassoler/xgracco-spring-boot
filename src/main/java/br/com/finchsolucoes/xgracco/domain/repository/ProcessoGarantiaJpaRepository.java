package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Processo;
import br.com.finchsolucoes.xgracco.domain.entity.ProcessoGarantia;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;
import java.util.Optional;

public interface ProcessoGarantiaJpaRepository {
    List<ProcessoGarantia> find(Processo processo, Query<ProcessoGarantia> query);

    Long count(Processo processo, Query<ProcessoGarantia> query);

    Optional<ProcessoGarantia> findById(Processo processo, Long id);
}
