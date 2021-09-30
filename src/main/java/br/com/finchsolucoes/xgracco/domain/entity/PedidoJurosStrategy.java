package br.com.finchsolucoes.xgracco.domain.entity;

import br.com.finchsolucoes.xgracco.legacy.beans.views.PedidoView;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by felipiberdun on 28/11/2016.
 */
public interface PedidoJurosStrategy extends Serializable {

    BigDecimal calcular(PedidoJuros pedido, PedidoView pedidoView);

}
