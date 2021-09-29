package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.ProcessoPedidoCenarios;

import java.util.Optional;

public interface ProcessoPedidoCenarioJpaRepository {
    Optional<ProcessoPedidoCenarios> findCenarioByPedido(Long idCenario);
}
