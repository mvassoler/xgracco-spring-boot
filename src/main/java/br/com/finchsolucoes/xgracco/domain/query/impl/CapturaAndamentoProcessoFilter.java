package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.CapturaAndamentoProcesso;
import br.com.finchsolucoes.xgracco.domain.query.Filter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

/**
 * Filtro da entidade {@link CapturaAndamentoProcesso}
 *
 * @author andre.baroni
 */
public class CapturaAndamentoProcessoFilter implements Filter<CapturaAndamentoProcesso> {

    @JsonProperty("processo.numero")
    private String numero;

    @JsonProperty("processo.tag")
    private Set<String> tags;

    @JsonProperty("capturaAndamento.descricao")
    private String capturaAndamentoDescricao;

    @JsonProperty("capturaAndamento.id")
    private Long capturaAndamentoId;

    @JsonProperty("somenteAtivos")
    private boolean somenteAtivos;

    private CapturaAndamentoProcessoFilter() {
        super();
        this.setSomenteAtivos(Boolean.FALSE);
    }

    public CapturaAndamentoProcessoFilter(Long capturaAndamentoId, boolean somenteAtivos) {
        this();
        this.setCapturaAndamentoId(capturaAndamentoId);
        this.setSomenteAtivos(somenteAtivos);
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public String getCapturaAndamentoDescricao() {
        return capturaAndamentoDescricao;
    }

    public void setCapturaAndamentoDescricao(String capturaAndamentoDescricao) {
        this.capturaAndamentoDescricao = capturaAndamentoDescricao;
    }

    public boolean isSomenteAtivos() {
        return somenteAtivos;
    }

    public void setSomenteAtivos(boolean somenteAtivos) {
        this.somenteAtivos = somenteAtivos;
    }

    public Long getCapturaAndamentoId() {
        return capturaAndamentoId;
    }

    public void setCapturaAndamentoId(Long capturaAndamentoId) {
        this.capturaAndamentoId = capturaAndamentoId;
    }
}
