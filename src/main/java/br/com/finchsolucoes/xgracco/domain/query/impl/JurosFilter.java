package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Juros;
import br.com.finchsolucoes.xgracco.domain.enums.EnumJuros;
import br.com.finchsolucoes.xgracco.domain.enums.EnumPeriodicidadeJuros;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Filtro de juros.
 *
 * @author Jordano
 * @since 2.1
 */
public class JurosFilter implements Filter<Juros> {

    private final String descricao;
    private final EnumPeriodicidadeJuros periodicidadeJurosCorrecao;
    private final EnumJuros tipoJuros;


    public JurosFilter(String descricao, EnumPeriodicidadeJuros periodicidadeJurosCorrecao, EnumJuros tipoJuros) {
        this.descricao = descricao;
        this.periodicidadeJurosCorrecao = periodicidadeJurosCorrecao;
        this.tipoJuros = tipoJuros;
    }

    public String getDescricao() {
        return descricao;
    }

    public EnumPeriodicidadeJuros getPeriodicidadeJurosCorrecao() {
        return periodicidadeJurosCorrecao;
    }

    public EnumJuros getTipoJuros() {
        return tipoJuros;
    }

}
