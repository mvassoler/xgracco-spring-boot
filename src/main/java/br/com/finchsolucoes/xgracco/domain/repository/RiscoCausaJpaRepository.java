package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.RiscoCausa;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;
import java.util.Optional;

public interface RiscoCausaJpaRepository {
    Optional<RiscoCausa> findByDescricao(String descricao);
    List<RiscoCausa> find(Query<RiscoCausa> query);
    long count(Query<RiscoCausa> query);

}
