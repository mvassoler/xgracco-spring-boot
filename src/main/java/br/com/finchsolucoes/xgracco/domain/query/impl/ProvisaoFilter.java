package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Provisao;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Filtro de Provisão
 *
 * @author Paulo Marçon
 * @since 2.1.04
 */
public class ProvisaoFilter implements Filter<Provisao> {

    private Calendar data;
    private BigDecimal valorProvisao;
    private BigDecimal valorPedido;

    public ProvisaoFilter(Calendar data, BigDecimal valorProvisao, BigDecimal valorPedido) {
        this.data = data;
        this.valorProvisao = valorProvisao;
        this.valorPedido = valorPedido;
    }

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
    }

    public BigDecimal getValorProvisao() {
        return valorProvisao;
    }

    public void setValorProvisao(BigDecimal valorProvisao) {
        this.valorProvisao = valorProvisao;
    }

    public BigDecimal getValorPedido() {
        return valorPedido;
    }

    public void setValorPedido(BigDecimal valorPedido) {
        this.valorPedido = valorPedido;
    }
}
