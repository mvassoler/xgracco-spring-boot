package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Caso;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;
import java.util.Optional;

public interface CasoJpaRepository {
    List<Caso> find(Query<Caso> query);

    long count(Query<Caso> query);

    Optional<Caso> findById(Long id);
}
