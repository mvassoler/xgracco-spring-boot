package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.PedidoIndice;
import br.com.finchsolucoes.xgracco.domain.entity.PedidoJuros;
import br.com.finchsolucoes.xgracco.domain.entity.Processo;
import br.com.finchsolucoes.xgracco.domain.entity.ProcessoPedido;

import java.math.BigDecimal;
import java.util.List;

public interface ProcessoPedidoJpaRepository {
    List<PedidoIndice> findIndices(ProcessoPedido processoPedido);

    List<PedidoJuros> findJuros(ProcessoPedido processoPedido);

    List<ProcessoPedido> getPedidosProcesso(Long idProcesso);

    List<Processo> findProcessoByProcessoPedido();

    List<ProcessoPedido> findPedidosFetchCenario();

    void updateValorProvisao(Long idPedido, BigDecimal novoValorProvisao);

    BigDecimal sumProvisionamentoPedidosByIdProcesso(Long idProcesso);

    BigDecimal sumValorPedidosByIdProcesso(Long idProcesso);
}
