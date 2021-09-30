package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.SistemaVirtual;
import br.com.finchsolucoes.xgracco.domain.entity.SistemaVirtualUf;
import br.com.finchsolucoes.xgracco.domain.entity.Uf;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

public class SistemaVirtualUfFilter implements Filter<SistemaVirtualUf> {

    private SistemaVirtual sistemaVirtual;

    private Uf uf;

    public SistemaVirtualUfFilter(SistemaVirtual sistemaVirtual, Uf uf) {
        this.sistemaVirtual = sistemaVirtual;
        this.uf = uf;
    }

    public SistemaVirtual getSistemaVirtual() {
        return sistemaVirtual;
    }

    public Uf getUf() {
        return uf;
    }
}
