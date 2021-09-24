package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.IndiceEconomico;
import br.com.finchsolucoes.xgracco.domain.entity.IndiceEconomicoVar;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Classe filtro para IndiceEconomicoVar
 *
 * @author Jordano
 * @since 2.2
 */
public class IndiceEconomicoVarFilter implements Filter<IndiceEconomicoVar> {

    private Calendar dataInicial;

    private Calendar dataFinal;

    private BigDecimal valor;

    private IndiceEconomico indiceEconomico;


    public IndiceEconomicoVarFilter(Calendar dataInicial, Calendar dataFinal, BigDecimal valor, IndiceEconomico indiceEconomico) {
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        this.valor = valor;
        this.indiceEconomico = indiceEconomico;
    }

    public Calendar getDataInicial() {
        return dataInicial;
    }

    public Calendar getDataFinal() {
        return dataFinal;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public IndiceEconomico getIndiceEconomico() {
        return indiceEconomico;
    }
}
