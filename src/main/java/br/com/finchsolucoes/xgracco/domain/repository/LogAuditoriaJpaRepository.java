package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.LogAuditoria;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;

public interface LogAuditoriaJpaRepository {
    List<LogAuditoria> find(Query<LogAuditoria> query);

    long count(Query<LogAuditoria> query);
}
