package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Juros;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;

public interface JurosJpaRepository {
    List<Juros> find(Query<Juros> query);

    long count(Query<Juros> query);
}
