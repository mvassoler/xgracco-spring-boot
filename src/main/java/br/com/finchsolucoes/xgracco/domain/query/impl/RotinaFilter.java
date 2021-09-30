package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Rotina;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Filter da entidade Rotina.
 *
 * @author Renan Gigliotti
 * @since 2.1
 */
public class RotinaFilter implements Filter<Rotina> {

    private final Long id;
    private final String descricao;
    private final Boolean ativo;
    private Long capturaAndamentoId;

    public RotinaFilter(Long id, String descricao, Boolean ativo) {
        this.id = id;
        this.descricao = descricao;
        this.ativo = ativo;
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public Long getCapturaAndamentoId() {
        return capturaAndamentoId;
    }

    public void setCapturaAndamentoId(Long capturaAndamentoId) {
        this.capturaAndamentoId = capturaAndamentoId;
    }
}
