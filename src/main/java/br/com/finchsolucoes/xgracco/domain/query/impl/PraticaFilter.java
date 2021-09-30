package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Pratica;
import br.com.finchsolucoes.xgracco.domain.enums.EnumArea;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoProcesso;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Created by felipiberdun on 16/01/2017.
 */
public class PraticaFilter implements Filter<Pratica> {

    private String descricao;
    private EnumTipoProcesso tipoProcesso;
    private EnumArea area;

    public PraticaFilter(String descricao, EnumTipoProcesso tipoProcesso, EnumArea area) {
        this.descricao = descricao;
        this.tipoProcesso = tipoProcesso;
        this.area = area;
    }

    public String getDescricao() {
        return descricao;
    }

    public EnumTipoProcesso getTipoProcesso() {
        return tipoProcesso;
    }

    public EnumArea getArea() {
        return area;
    }

}