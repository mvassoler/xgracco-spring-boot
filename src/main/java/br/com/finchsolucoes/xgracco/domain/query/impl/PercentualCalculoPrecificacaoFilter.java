package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.PercentualCalculoPrecificacao;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

import java.math.BigDecimal;

/**
 *
 * Filter da entidade PercentualCalculoProvisao
 * @author guilhermecamargo
 */
public class PercentualCalculoPrecificacaoFilter implements Filter<PercentualCalculoPrecificacao> {

    private final String descricao;

    private BigDecimal percentual;


    public PercentualCalculoPrecificacaoFilter(String descricao, BigDecimal percentual){
        this.descricao = descricao;
        this.percentual = percentual;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getPercentual() {
        return percentual;
    }
}
