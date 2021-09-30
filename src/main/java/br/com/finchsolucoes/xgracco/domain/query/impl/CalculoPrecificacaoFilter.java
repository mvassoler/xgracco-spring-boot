package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.CalculoPrecificacao;
import br.com.finchsolucoes.xgracco.domain.enums.EnumMes;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

import java.math.BigDecimal;

/**
 * Filter para a entidade CalculoPrecificacao
 * @author guilhermecamargo
 */
public class CalculoPrecificacaoFilter implements Filter<CalculoPrecificacao> {

    private BigDecimal valorMediaMensal;
    private EnumMes mes;
    private Integer ano;
    private Boolean rotinaExecutada;

    public CalculoPrecificacaoFilter(BigDecimal valorMediaMensal, EnumMes mes, Integer ano, Boolean rotinaExecutada) {
        this.valorMediaMensal = valorMediaMensal;
        this.mes = mes;
        this.ano = ano;
        this.rotinaExecutada = rotinaExecutada;
    }

    public BigDecimal getValorMediaMensal() {
        return valorMediaMensal;
    }

    public EnumMes getMes() {
        return mes;
    }

    public Integer getAno() {
        return ano;
    }

    public Boolean getRotinaExecutada() {
        return rotinaExecutada;
    }
}
