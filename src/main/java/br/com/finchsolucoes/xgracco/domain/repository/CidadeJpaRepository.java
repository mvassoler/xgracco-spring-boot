package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Cidade;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;

public interface CidadeJpaRepository {
    List<Cidade> find(Query<Cidade> query);

    long count(Query<Cidade> query);
}
