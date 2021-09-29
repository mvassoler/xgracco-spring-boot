package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.RotinaExecucao;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;

public interface RotinaExecucaoJpaRepository {
    List<RotinaExecucao> find(Query<RotinaExecucao> query);
    long count(Query<RotinaExecucao> query);
}
