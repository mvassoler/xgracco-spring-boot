package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.PublicacaoNaoColadaAcao;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;
import java.util.Optional;

public interface PublicacaoNaoColadaAcaoJpaRepository {

    Optional<PublicacaoNaoColadaAcao> findByPublicacaoNaoColada(Long id);
    List<PublicacaoNaoColadaAcao> find(Query<PublicacaoNaoColadaAcao> query);
    long count(Query<PublicacaoNaoColadaAcao> query);
}
