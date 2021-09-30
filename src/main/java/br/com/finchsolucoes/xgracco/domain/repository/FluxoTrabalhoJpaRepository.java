package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Carteira;
import br.com.finchsolucoes.xgracco.domain.entity.FluxoTrabalho;
import br.com.finchsolucoes.xgracco.domain.entity.FluxoTrabalhoTarefa;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;
import java.util.Optional;

public interface FluxoTrabalhoJpaRepository {
    List<FluxoTrabalho> find(Query<FluxoTrabalho> query);

    long count(Query<FluxoTrabalho> query);

    Optional<FluxoTrabalho> findByCarteira(Carteira carteira);

    void updateDeployId(FluxoTrabalho fluxoTrabalho, String deployId);

    Optional<FluxoTrabalho> findByProcessoCaseInstanceId(String caseInstanceId);

    Optional<FluxoTrabalho> findByFluxoTrabalhoTarefa(FluxoTrabalhoTarefa fluxoTrabalhoTarefa);
}
