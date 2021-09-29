package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.TipoPedido;
import br.com.finchsolucoes.xgracco.domain.enums.EnumArea;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;
import java.util.Optional;

public interface TipoPedidoJpaRepository {

    List<TipoPedido> find(Query<TipoPedido> query);

    long count(Query<TipoPedido> query);

    Optional<TipoPedido> findByDescricaoAndArea(String descricao, EnumArea area);
}
