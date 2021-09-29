package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Fase;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FaseJpaRepository {
    List<Fase> find(Query<Fase> query);

    long count(Query<Fase> query);
}
