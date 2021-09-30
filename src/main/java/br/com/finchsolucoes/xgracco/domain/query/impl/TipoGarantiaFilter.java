package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.TipoGarantia;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Created by jordano on 26/01/17.
 */
public class TipoGarantiaFilter implements Filter<TipoGarantia> {

    private String descricao;

    Boolean ativo;

    public TipoGarantiaFilter(String descricao, Boolean ativo) {
        this.descricao = descricao;
        this.ativo = ativo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Boolean getAtivo() {
        return ativo;
    }
}
