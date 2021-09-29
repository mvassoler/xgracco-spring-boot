package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.CalculoPrecificacaoExecucaoLog;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;
import java.util.Optional;

public interface CalculoPrecificacaoExecucaoLogJpaRepository {
    List<CalculoPrecificacaoExecucaoLog> find(Query<CalculoPrecificacaoExecucaoLog> query);

    long count(Query<CalculoPrecificacaoExecucaoLog> query);

    Optional<CalculoPrecificacaoExecucaoLog> findByIdAndIdCalculo(Long idLog, Long idCalculo);
}
