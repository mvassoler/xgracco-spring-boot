package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Posicao;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;

public interface PosicaoJpaRepository {
    List<Posicao> find(Query<Posicao> query);

    List<Posicao> findAllCache();

    long count(Query<Posicao> query);
}
