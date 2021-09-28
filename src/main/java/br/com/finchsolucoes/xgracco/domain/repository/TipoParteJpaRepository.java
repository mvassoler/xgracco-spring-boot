package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.TipoParte;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;

public interface TipoParteJpaRepository {

    List<TipoParte> find(Query<TipoParte> query);

    long count(Query<TipoParte> query);

    List<TipoParte> findAll();
}
