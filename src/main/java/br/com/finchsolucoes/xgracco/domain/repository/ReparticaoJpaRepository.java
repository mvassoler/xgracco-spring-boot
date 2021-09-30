package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Reparticao;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;

public interface ReparticaoJpaRepository {
    public List<Reparticao> findAllCache();

    List<Reparticao> find(final Query<Reparticao> query);

    long count(final Query<Reparticao> query);


}
