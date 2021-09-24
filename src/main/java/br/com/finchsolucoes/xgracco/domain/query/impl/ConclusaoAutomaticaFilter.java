package br.com.finchsolucoes.xgracco.domain.query.impl;

import br.com.finchsolucoes.xgracco.domain.entity.ConclusaoAutomatica;
import br.com.finchsolucoes.xgracco.domain.entity.FluxoTrabalho;
import br.com.finchsolucoes.xgracco.domain.entity.FluxoTrabalhoTarefa;
import br.com.finchsolucoes.xgracco.domain.query.Filter;

/**
 * Filtro da conclusão automática.
 *
 * @author thiago.fogar
 * @since 4.0.2
 */
public class ConclusaoAutomaticaFilter implements Filter<ConclusaoAutomatica> {

    private final FluxoTrabalho fluxoTrabalho;
    private final FluxoTrabalhoTarefa fluxoTrabalhoTarefa;

    public ConclusaoAutomaticaFilter(FluxoTrabalhoTarefa fluxoTrabalhoTarefa, FluxoTrabalho fluxoTrabalho) {
        this.fluxoTrabalhoTarefa = fluxoTrabalhoTarefa;
        this.fluxoTrabalho = fluxoTrabalho;
    }

    public FluxoTrabalhoTarefa getFluxoTrabalhoTarefa() {
        return fluxoTrabalhoTarefa;
    }

    public FluxoTrabalho getFluxoTrabalho() {
        return fluxoTrabalho;
    }
}
