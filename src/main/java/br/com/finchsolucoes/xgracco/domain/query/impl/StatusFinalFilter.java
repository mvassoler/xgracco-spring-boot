package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.StatusFinal;
import br.com.finchsolucoes.xgracco.domain.enums.EnumStatusTarefa;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Filtro de status final.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
public class StatusFinalFilter implements Filter<StatusFinal> {

    private final String descricao;
    private final EnumStatusTarefa statusTarefa;

    public StatusFinalFilter(String descricao) {
        this.descricao = descricao;
        this.statusTarefa = null;
    }

    public StatusFinalFilter(String descricao, EnumStatusTarefa statusTarefa) {
        this.descricao = descricao;
        this.statusTarefa = statusTarefa;
    }

    public String getDescricao() {
        return descricao;
    }

    public EnumStatusTarefa getStatusTarefa() {
        return statusTarefa;
    }
}
