package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.ConclusaoAutomatica;
import br.com.finchsolucoes.xgracco.domain.entity.ConclusaoAutomaticaTarefa;
import br.com.finchsolucoes.xgracco.domain.entity.FluxoTrabalhoTarefa;
import br.com.finchsolucoes.xgracco.domain.query.Filter;


public class ConclusaoAutomaticaTarefaFilter implements Filter<ConclusaoAutomaticaTarefa> {

    private final ConclusaoAutomatica conclusaoAutomatica;
    private final FluxoTrabalhoTarefa fluxoTrabalhoTarefa;

    public ConclusaoAutomaticaTarefaFilter(ConclusaoAutomatica conclusaoAutomatica, FluxoTrabalhoTarefa fluxoTrabalhoTarefa) {
        this.conclusaoAutomatica = conclusaoAutomatica;
        this.fluxoTrabalhoTarefa = fluxoTrabalhoTarefa;
    }

    public ConclusaoAutomatica getConclusaoAutomatica() {
        return conclusaoAutomatica;
    }

    public FluxoTrabalhoTarefa getFluxoTrabalhoTarefa() {
        return fluxoTrabalhoTarefa;
    }
}
