package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Cidade;
import br.com.finchsolucoes.xgracco.domain.entity.Uf;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Filtro de cidade
 *
 * @author Felipi Berdun
 * @since 2.1
 */
public class CidadeFilter implements Filter<Cidade> {

    private String descricao;
    private Uf uf;

    public CidadeFilter(String descricao, Uf uf) {
        this.descricao = descricao;
        this.uf = uf;
    }

    public String getDescricao() {
        return descricao;
    }

    public Uf getUf() {
        return uf;
    }

}
