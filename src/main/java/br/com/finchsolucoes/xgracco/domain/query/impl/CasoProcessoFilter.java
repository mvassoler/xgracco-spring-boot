package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.CasoProcesso;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

public class CasoProcessoFilter implements Filter<CasoProcesso> {

    private final Long casoProcessoPaiId;
    private final Long processoId;

    public CasoProcessoFilter(Long casoProcessoPaiId, Long processoId) {
        this.casoProcessoPaiId = casoProcessoPaiId;
        this.processoId = processoId;
    }

    public Long getCasoProcessoPaiId() {
        return casoProcessoPaiId;
    }

    public Long getProcessoId() {
        return processoId;
    }
}
