package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.RotinaExecucaoLog;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;

public interface RotinaExecucaoLogJpaRepository {
    List<RotinaExecucaoLog> find(Query<RotinaExecucaoLog> query);
    long count(Query<RotinaExecucaoLog> query);
}
