package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.DadosBasicosTarefa;
import br.com.finchsolucoes.xgracco.domain.entity.FluxoTrabalhoTarefa;
import br.com.finchsolucoes.xgracco.domain.entity.StatusFinal;
import br.com.finchsolucoes.xgracco.domain.entity.TarefaStatusFinal;
import br.com.finchsolucoes.xgracco.domain.enums.EnumStatusTarefa;

import java.util.List;
import java.util.Optional;

public interface StatusFinalJpaRepository {

    Optional<StatusFinal> findByTarefaStatusFinal(TarefaStatusFinal tarefaStatusFinal);

    Optional<StatusFinal> findByDescricao(String descricao);

    List<TarefaStatusFinal> findByDadosBasicosTarefa(DadosBasicosTarefa dadosBasicosTarefa, EnumStatusTarefa statusTarefa);

    List<StatusFinal> findByFluxoTrabalhoTarefa(FluxoTrabalhoTarefa fluxoTrabalhoTarefa, Integer statusTarefa);
}
