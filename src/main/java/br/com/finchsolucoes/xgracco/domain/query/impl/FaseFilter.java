package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Fase;
import br.com.finchsolucoes.xgracco.domain.entity.Pratica;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoProcesso;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Created by felipiberdun on 16/01/2017.
 */
public class FaseFilter implements Filter<Fase> {

    private String descricao;
    private EnumTipoProcesso tipoProcesso;
    private Pratica pratica;

    public FaseFilter(String descricao, EnumTipoProcesso tipoProcesso, Pratica pratica) {
        this.descricao = descricao;
        this.tipoProcesso = tipoProcesso;
        this.pratica = pratica;
    }

    public String getDescricao() {
        return descricao;
    }

    public EnumTipoProcesso getTipoProcesso() {
        return tipoProcesso;
    }

    public Pratica getPratica() {
        return pratica;
    }

}