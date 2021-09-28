package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Uf;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;
import java.util.Optional;

public interface UfJpaRepository {

    List<Uf> find(Query<Uf> query);

    List<Uf> findAllCache();

    long count(Query<Uf> query);

    Optional<Uf> findBySigla(String sigla);
}
