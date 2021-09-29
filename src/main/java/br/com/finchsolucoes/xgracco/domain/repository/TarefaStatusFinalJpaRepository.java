package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.FluxoTrabalho;
import br.com.finchsolucoes.xgracco.domain.entity.FluxoTrabalhoTarefa;
import br.com.finchsolucoes.xgracco.domain.entity.StatusFinal;
import br.com.finchsolucoes.xgracco.domain.entity.TarefaStatusFinal;
import br.com.finchsolucoes.xgracco.domain.enums.EnumStatusTarefa;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Repositório da entidade TarefaStatusFinal.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
public interface TarefaStatusFinalJpaRepository {


    Optional<TarefaStatusFinal> findByIdFetchAgendamentos(Long id);

    List<TarefaStatusFinal> findByIdStatusFinal(StatusFinal statusFinal);

    void removeByTarefa(FluxoTrabalhoTarefa fluxoTrabalhoTarefa);

    List<TarefaStatusFinal> findByTarefa(FluxoTrabalhoTarefa fluxoTrabalhoTarefa);

    List<TarefaStatusFinal> findByFluxoTrabalho(FluxoTrabalho fluxoTrabalho);


    Long countByFluxoTrabalhoTarefaAndStatusFinal(FluxoTrabalhoTarefa fluxoTrabalhoTarefa, StatusFinal statusFinal);

    List<TarefaStatusFinal> findByFluxoTrabalhoTarefaAndStatusFinalAndStatusFetchAgendamentos(FluxoTrabalhoTarefa fluxoTrabalhoTarefa,
                                                                                              StatusFinal statusFinal,
                                                                                              EnumStatusTarefa statusTarefa);
}
