package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.PessoaDivisao;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Filtro da entidade Pessoa
 *
 * Created by jordano on 24/01/17.
 */
public class PessoaDivisaoFilter implements Filter<PessoaDivisao> {

    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public PessoaDivisaoFilter(String descricao) {
        this.descricao = descricao;
    }
}
