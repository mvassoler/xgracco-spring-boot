package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.TipoParte;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Filtro de TipoParteRequest.
 *
 * @author Renan Gigliotti
 * @since 2.1
 */
public class TipoParteFilter implements Filter<TipoParte> {

    private final String descricao;

    public TipoParteFilter(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
