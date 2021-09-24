package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.ProcessoGarantia;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Filtro de Garantia.
 *
 * @author Jordano
 * @since 2.2
 */
public class ProcessoGarantiaFilter implements Filter<ProcessoGarantia> {

    private final Long idInformacoesAdicionais;

    private final Long idTipoGarantia;

    public ProcessoGarantiaFilter(Long idInformacoesAdicionais, Long idTipoGarantia) {
        this.idInformacoesAdicionais = idInformacoesAdicionais;
        this.idTipoGarantia = idTipoGarantia;
    }

    public Long getIdInformacoesAdicionais() {
        return idInformacoesAdicionais;
    }

    public Long getIdTipoGarantia() {
        return idTipoGarantia;
    }
}
