package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Comarca;
import br.com.finchsolucoes.xgracco.domain.entity.Pratica;
import br.com.finchsolucoes.xgracco.domain.entity.Reparticao;
import br.com.finchsolucoes.xgracco.domain.enums.EnumArea;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Created by felipiberdun on 17/01/2017.
 */
public class ReparticaoFilter implements Filter<Reparticao> {

    private String descricao;
    private EnumArea area;
    private Pratica pratica;
    private Comarca comarca;

    public ReparticaoFilter(String descricao, EnumArea area, Pratica pratica, Comarca comarca) {
        this.descricao = descricao;
        this.area = area;
        this.pratica = pratica;
        this.comarca = comarca;
    }

    public String getDescricao() {
        return descricao;
    }

    public EnumArea getArea() {
        return area;
    }

    public Pratica getPratica() {
        return pratica;
    }

    public Comarca getComarca() {
        return comarca;
    }

}
