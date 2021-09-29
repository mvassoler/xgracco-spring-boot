package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.FluxoTrabalho;
import br.com.finchsolucoes.xgracco.domain.entity.Processo;
import br.com.finchsolucoes.xgracco.domain.entity.ProcessoCaseInstance;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ProcessoCaseInstanceJpaRepository {

    /**
     * Insere um novo case instance para o processo.
     *
     * @param processosCaseInstances
     */
    void create(Collection<ProcessoCaseInstance> processosCaseInstances);

    List<ProcessoCaseInstance> find(Query<ProcessoCaseInstance> query);

    Optional<ProcessoCaseInstance> findProcessoCaseInstanceAtivo(Processo processo, FluxoTrabalho fluxoTrabalho);
}
