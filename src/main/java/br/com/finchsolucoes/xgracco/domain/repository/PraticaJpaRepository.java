package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Pratica;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;

public interface PraticaJpaRepository {
    List<Pratica> find(Query<Pratica> query);

    List<Pratica> findAllCache();

    long count(Query<Pratica> query);
}
