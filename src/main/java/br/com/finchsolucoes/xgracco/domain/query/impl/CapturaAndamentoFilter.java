package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.CapturaAndamento;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Filtro da entidade {@link CapturaAndamento}
 *
 * @author andre.baroni
 */
public class CapturaAndamentoFilter implements Filter<CapturaAndamento> {

    private String descricao;
    private String processoUnico;

    public CapturaAndamentoFilter(String descricao, String processoUnico) {
        this.descricao = descricao;
        this.processoUnico = processoUnico;
    }

    public CapturaAndamentoFilter(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getProcessoUnico() {
        return processoUnico;
    }
}
