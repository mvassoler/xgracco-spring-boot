package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Foro;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;

public interface ForoJpaRepository {
    List<Foro> find(Query<Foro> query);

    List<Foro> findAllCache();

    long count(Query<Foro> query);
}
