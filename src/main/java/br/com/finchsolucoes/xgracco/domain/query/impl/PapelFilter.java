package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Papel;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * @author Thiago Fogar
 * @since 2.2
 */
public class PapelFilter implements Filter<Papel> {

    private final String descricao;

    public PapelFilter(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
