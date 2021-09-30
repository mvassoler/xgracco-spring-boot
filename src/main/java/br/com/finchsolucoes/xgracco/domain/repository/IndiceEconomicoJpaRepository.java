package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.IndiceEconomico;
import br.com.finchsolucoes.xgracco.domain.entity.PedidoIndice;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoIndiceEconomico;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;
import java.util.Optional;

public interface IndiceEconomicoJpaRepository {
    List<IndiceEconomico> find(Query<IndiceEconomico> query);

    long count(Query<IndiceEconomico> query);

    void update(IndiceEconomico entity);

    List<PedidoIndice> validarIndice(Long id);

    Long validaTipoIndiceExistente(EnumTipoIndiceEconomico tipoIndiceEconomico, Long idIndice);

    Optional<IndiceEconomico> findByTipoIndiceAndDescricao(String descricao, EnumTipoIndiceEconomico tipoIndiceEconomico);

    Optional<IndiceEconomico> findByDescricao(String descricao);
}
