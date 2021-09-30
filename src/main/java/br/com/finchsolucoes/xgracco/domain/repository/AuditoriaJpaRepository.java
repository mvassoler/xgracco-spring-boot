package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.legacy.beans.interfaces.EntidadeAuditada;

import java.util.List;

public interface AuditoriaJpaRepository<T extends EntidadeAuditada> {
    List find(Query query);
}
