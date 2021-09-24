package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.ProcessoDespesaTipo;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Filtro de ProcessoDespesaTipo
 *
 * @author Jordano
 * @since 2.1
 */
public class ProcessoDespesaTipoFilter implements Filter<ProcessoDespesaTipo> {

    private String descricao;
    private Boolean reenbolsavel;

    public ProcessoDespesaTipoFilter(String descricao, Boolean reenbolsavel) {
        this.descricao = descricao;
        this.reenbolsavel = reenbolsavel;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getReenbolsavel() {
        return reenbolsavel;
    }

    public void setReenbolsavel(Boolean reenbolsavel) {
        this.reenbolsavel = reenbolsavel;
    }
}
