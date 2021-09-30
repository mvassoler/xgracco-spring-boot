package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.Urgencia;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Created by deivy.spaulonci
 */
public class UrgenciaFilter implements Filter<Urgencia> {
    private String descricao;

    public UrgenciaFilter(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
