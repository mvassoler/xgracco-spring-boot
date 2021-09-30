package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.GrupoCampo;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;

public interface GrupoCampoJpaRepository {
    List<GrupoCampo> find(Query<GrupoCampo> query);

    long count(Query<GrupoCampo> query);

    List<GrupoCampo> findAll();
}
