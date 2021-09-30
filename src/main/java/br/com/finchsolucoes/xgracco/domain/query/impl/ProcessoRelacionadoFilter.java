package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.ProcessoRelacionado;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

public class ProcessoRelacionadoFilter implements Filter<ProcessoRelacionado> {

    private Long idProcesso;

    private Long idRelacionado;

    public ProcessoRelacionadoFilter() {}

    public Long getIdProcesso() {
        return idProcesso;
    }

    public void setIdProcesso(Long idProcesso) {
        this.idProcesso = idProcesso;
    }

    public Long getIdRelacionado() {
        return idRelacionado;
    }

    public void setIdRelacionado(Long idRelacionado) {
        this.idRelacionado = idRelacionado;
    }
}
