package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.RiscoCausa;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Filtro da entidade RiscoCausa
 *
 * @author Paulo Marçon
 * @since 2.1
 */
public class RiscoCausaFilter implements Filter<RiscoCausa> {

    private String descricao;

    public RiscoCausaFilter(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
