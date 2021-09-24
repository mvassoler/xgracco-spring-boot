package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Comarca;
import br.com.finchsolucoes.xgracco.domain.entity.Feriado;
import br.com.finchsolucoes.xgracco.domain.entity.Uf;
import br.com.finchsolucoes.xgracco.domain.enums.EnumArea;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Filtro de Feriado
 * <p>
 * Created by jordano on 25/05/2017.
 */
public class FeriadoFilter implements Filter<Feriado> {

    private String descricao;
    private Uf uf;
    private Integer dia;
    private Integer mes;
    private Integer ano;
    private EnumArea area;
    private Comarca comarca;

    public FeriadoFilter(String descricao, Uf uf, Integer dia, Integer mes, Integer ano, EnumArea area, Comarca comarca) {
        this.descricao = descricao;
        this.uf = uf;
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
        this.area = area;
        this.comarca = comarca;
    }

    public String getDescricao() {
        return descricao;
    }

    public Uf getUf() {
        return uf;
    }

    public Integer getDia() {
        return dia;
    }

    public Integer getMes() {
        return mes;
    }

    public Integer getAno() {
        return ano;
    }

    public EnumArea getArea() {
        return area;
    }

    public Comarca getComarca() {
        return comarca;
    }
}
