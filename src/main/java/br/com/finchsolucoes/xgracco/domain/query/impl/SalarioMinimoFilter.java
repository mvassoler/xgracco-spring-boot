package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.SalarioMinimo;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

import java.math.BigDecimal;

/**
 * Filtro da Salario Minimo
 * <p>
 * Created by Jordano on 24/04/2018.
 */
public class SalarioMinimoFilter implements Filter<SalarioMinimo> {

    private Integer ano;
    private BigDecimal valor;

    public SalarioMinimoFilter(Integer ano, BigDecimal valor) {
        this.ano = ano;
        this.valor = valor;
    }

    public Integer getAno() {
        return ano;
    }

    public BigDecimal getValor() {
        return valor;
    }
}
