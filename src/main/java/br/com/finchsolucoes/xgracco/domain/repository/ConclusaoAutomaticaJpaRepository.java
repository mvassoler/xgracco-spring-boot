package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.ConclusaoAutomatica;
import br.com.finchsolucoes.xgracco.domain.entity.FluxoTrabalho;
import br.com.finchsolucoes.xgracco.domain.entity.FluxoTrabalhoTarefa;
import br.com.finchsolucoes.xgracco.domain.entity.StatusFinal;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;
import java.util.Optional;

public interface ConclusaoAutomaticaJpaRepository {
    List<ConclusaoAutomatica> find(Query<ConclusaoAutomatica> query);

    long count(Query<ConclusaoAutomatica> query);

    Optional<ConclusaoAutomatica> findByFluxoTrabalhoAndTarefa(FluxoTrabalho fluxoTrabalho, FluxoTrabalhoTarefa fluxoTrabalhoTarefa, StatusFinal statusFinal);
}
