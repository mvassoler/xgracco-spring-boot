package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.GrupoCampoCarteira;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;

public interface GrupoCampoCarteiraJpaRepository {
    List<GrupoCampoCarteira> find(Query<GrupoCampoCarteira> query);

    long count(Query<GrupoCampoCarteira> query);

    List<GrupoCampoCarteira> findAll(Query<GrupoCampoCarteira> query);

    void create(GrupoCampoCarteira grupoCampoCarteira);
}
