package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.ReferenciaHonorarios;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Created by jordano on 30/01/17.
 */
public class ReferenciaHonorarioFilter implements Filter<ReferenciaHonorarios> {

    private String descricao;

    public ReferenciaHonorarioFilter(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
