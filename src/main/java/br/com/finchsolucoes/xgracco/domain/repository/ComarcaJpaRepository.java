package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Comarca;
import br.com.finchsolucoes.xgracco.domain.entity.Uf;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;
import java.util.Optional;

public interface ComarcaJpaRepository {
    List<Comarca> find(Query<Comarca> query);

    List<Comarca> findAllCache();

    long count(Query<Comarca> query);

    List<Comarca> findComarcaByDescricaoAndUf(String descricao, Uf uf);

    Optional<Comarca> findById(Long id);

    Optional<Comarca> findComarcaByDescricaoAndDescricaoUF(String comarca, String uf);
}
