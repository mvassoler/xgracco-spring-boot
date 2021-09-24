package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.SistemaVirtual;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

public class SistemaVirtualFilter implements Filter<SistemaVirtual>{

    private final String descricao;

    public SistemaVirtualFilter(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
