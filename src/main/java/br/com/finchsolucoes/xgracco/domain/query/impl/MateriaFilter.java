package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Materia;
import br.com.finchsolucoes.xgracco.domain.entity.Pratica;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Created by felipiberdun on 13/01/2017.
 */
public class MateriaFilter implements Filter<Materia> {

    private String descricao;
    private Pratica pratica;

    public MateriaFilter(String descricao, Pratica pratica) {
        this.descricao = descricao;
        this.pratica = pratica;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Pratica getPratica() {
        return pratica;
    }

    public void setPratica(Pratica pratica) {
        this.pratica = pratica;
    }

}
