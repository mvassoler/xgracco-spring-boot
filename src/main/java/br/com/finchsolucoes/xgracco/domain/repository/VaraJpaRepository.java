package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Vara;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;
import java.util.Optional;

public interface VaraJpaRepository {
    List<Vara> findAllCache();

    Optional<Vara> findByDescricao(String descricao);
    List<Vara> find(Query<Vara> query);
}
