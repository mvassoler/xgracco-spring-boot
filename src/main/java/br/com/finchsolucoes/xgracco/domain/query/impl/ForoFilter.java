package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Comarca;
import br.com.finchsolucoes.xgracco.domain.entity.Foro;
import br.com.finchsolucoes.xgracco.domain.enums.EnumInstancia;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoJustica;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Created by paulomarcon
 */
public class ForoFilter implements Filter<Foro> {

    private String descricao;
    private EnumInstancia instancia;
    private EnumTipoJustica tipoJustica;
    private Comarca comarca;

    public ForoFilter(String descricao, EnumInstancia instancia, EnumTipoJustica tipoJustica, Comarca comarca) {
        this.descricao = descricao;
        this.instancia = instancia;
        this.tipoJustica = tipoJustica;
        this.comarca = comarca;
    }

    public String getDescricao() {
        return descricao;
    }

    public EnumInstancia getInstancia() {
        return instancia;
    }

    public EnumTipoJustica getTipoJustica() {
        return tipoJustica;
    }

    public Comarca getComarca() {
        return comarca;
    }
}
