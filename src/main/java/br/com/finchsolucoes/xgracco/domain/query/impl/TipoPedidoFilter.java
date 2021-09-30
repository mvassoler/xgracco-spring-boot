package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.TipoPedido;
import br.com.finchsolucoes.xgracco.domain.enums.EnumArea;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Filtro de TipoPedido
 *
 * Created by jordano on 25/01/17.
 */
public class TipoPedidoFilter implements Filter<TipoPedido> {

    private String descricao;
    private EnumArea area;

    public TipoPedidoFilter(String descricao, EnumArea area) {
        this.descricao = descricao;
        this.area = area;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public EnumArea getArea() {
        return area;
    }

    public void setArea(EnumArea area) {
        this.area = area;
    }
}
