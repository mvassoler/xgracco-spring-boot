package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.ProcessoTag;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

public class ProcessoTagFilter implements Filter<ProcessoTag> {

    private Long idProcesso;

    private Long idTag;

    private String processoUnico;

    private String nomeTag;

    public ProcessoTagFilter() {}

    public Long getIdProcesso() {
        return idProcesso;
    }

    public void setIdProcesso(Long idProcesso) {
        this.idProcesso = idProcesso;
    }

    public Long getIdTag() {
        return idTag;
    }

    public void setIdTag(Long idTag) {
        this.idTag = idTag;
    }

    public String getProcessoUnico() {
        return processoUnico;
    }

    public void setProcessoUnico(String processoUnico) {
        this.processoUnico = processoUnico;
    }

    public String getNomeTag() {
        return nomeTag;
    }

    public void setNomeTag(String nomeTag) {
        this.nomeTag = nomeTag;
    }
}
