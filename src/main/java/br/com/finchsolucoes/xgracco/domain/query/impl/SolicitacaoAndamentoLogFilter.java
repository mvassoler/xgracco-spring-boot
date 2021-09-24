package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.SolicitacaoAndamentoLog;
import br.com.finchsolucoes.xgracco.domain.query.Filter;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Filtro da entidade {@link SolicitacaoAndamentoLog}.
 *
 * @author andre.baroni
 */
public class SolicitacaoAndamentoLogFilter implements Filter<SolicitacaoAndamentoLog> {

    @JsonProperty("capturaAndamento.id")
    private Long capturaAndamentoId;

    @JsonProperty("solicitacaoAndamento.id")
    private Long solicitacaoAndamentoId;

    @JsonProperty("processo.numero")
    private String processoNumero;

    @JsonProperty("processo.processoUnico")
    private String processoUnico;

    public SolicitacaoAndamentoLogFilter() {
        super();
    }

    public Long getCapturaAndamentoId() {
        return capturaAndamentoId;
    }

    public void setCapturaAndamentoId(Long capturaAndamentoId) {
        this.capturaAndamentoId = capturaAndamentoId;
    }

    public Long getSolicitacaoAndamentoId() {
        return solicitacaoAndamentoId;
    }

    public void setSolicitacaoAndamentoId(Long solicitacaoAndamentoId) {
        this.solicitacaoAndamentoId = solicitacaoAndamentoId;
    }

    public String getProcessoNumero() {
        return processoNumero;
    }

    public void setProcessoNumero(String processoNumero) {
        this.processoNumero = processoNumero;
    }

    public String getProcessoUnico() {
        return processoUnico;
    }

    public void setProcessoUnico(String processoUnico) {
        this.processoUnico = processoUnico;
    }
}
