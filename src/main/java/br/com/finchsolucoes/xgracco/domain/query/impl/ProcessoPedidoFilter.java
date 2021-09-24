package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.ProcessoPedido;
import br.com.finchsolucoes.xgracco.domain.entity.RiscoCausa;
import br.com.finchsolucoes.xgracco.domain.entity.TipoPedido;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Filtro de Pedido
 * <p>
 * Created by jordano on 03/08/17.
 */
public class ProcessoPedidoFilter implements Filter<ProcessoPedido> {

    private String descricao;
    private TipoPedido tipoPedido;
    private RiscoCausa riscoCausa;

    public ProcessoPedidoFilter(String descricao, TipoPedido tipoPedido, RiscoCausa riscoCausa) {
        this.descricao = descricao;
        this.tipoPedido = tipoPedido;
        this.riscoCausa = riscoCausa;
    }

    public String getDescricao() {
        return descricao;
    }

    public TipoPedido getTipoPedido() {
        return tipoPedido;
    }

    public RiscoCausa getRiscoCausa() {
        return riscoCausa;
    }
}
