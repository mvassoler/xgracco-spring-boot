package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Decisao;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DecisaoJpaRepository {
    List<Decisao> find(Query<Decisao> query);

    long count(Query<Decisao> query);

    List<Decisao> findbyDescricao(String decisao);

    List<Decisao> findAllCache();
}
