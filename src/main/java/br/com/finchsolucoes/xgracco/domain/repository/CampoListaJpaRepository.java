package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Campo;
import br.com.finchsolucoes.xgracco.domain.entity.CampoLista;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;
import java.util.Optional;

public interface CampoListaJpaRepository {
    List<CampoLista> find(Query<CampoLista> query);

    long count(Query<CampoLista> query);

    Optional<CampoLista> findByCampoAndId(Campo campo, Long id);

    Optional<CampoLista> findByDescricaoAndCampo(String descricao, Campo campo);
}
