package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.TipoLogradouro;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Filtro de tipo de logradouro
 *
 * @author Felipi Berdun
 * @since 2.1
 */
public class TipoLogradouroFilter implements Filter<TipoLogradouro> {

    private String descricao;

    public TipoLogradouroFilter(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
