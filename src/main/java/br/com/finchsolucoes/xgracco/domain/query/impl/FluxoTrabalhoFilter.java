package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.FluxoTrabalho;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Filtro de fluxo de trabalho.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
public class FluxoTrabalhoFilter implements Filter<FluxoTrabalho> {

    private final String descricao;

    public FluxoTrabalhoFilter(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
