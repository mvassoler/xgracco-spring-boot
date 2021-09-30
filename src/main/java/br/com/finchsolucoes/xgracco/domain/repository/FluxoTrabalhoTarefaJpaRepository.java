package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;
import java.util.Optional;

public interface FluxoTrabalhoTarefaJpaRepository {
    long count(FluxoTrabalho fluxoTrabalho, Query<FluxoTrabalhoTarefa> query);

    Optional<FluxoTrabalhoTarefa> findTarefaPublicacaoByFluxo(FluxoTrabalho fluxoTrabalho);

    Optional<FluxoTrabalhoTarefa> findById(Long id);

    List<FluxoTrabalhoTarefa> find(FluxoTrabalho fluxoTrabalho, Query<FluxoTrabalhoTarefa> query);

    void create(FluxoTrabalhoTarefa fluxoTrabalhoTarefa);

    void remove(FluxoTrabalhoTarefa fluxoTrabalhoTarefa);

    Optional<FluxoTrabalhoTarefa> findByFluxoTrabalhoAndTarefaFetchStatusFinais(FluxoTrabalho fluxoTrabalho, Long idFluxoTrabalhoTarefa);

    Optional<FluxoTrabalhoTarefa> findByFluxoTrabalhoAndIdFetchStatusFinaisAgendamentos(FluxoTrabalho fluxoTrabalho, Long id);

    List<FluxoTrabalhoTarefa> findByEsteiraAndCarteira(Esteira esteira, Carteira carteira, String tarefa);

    void removeByFluxoTrabalho(FluxoTrabalho fluxoTrabalho);

    void update(FluxoTrabalhoTarefa fluxoTrabalhoTarefa);

    Optional<FluxoTrabalhoTarefa> findByFluxoAndTarefa(FluxoTrabalho fluxoTrabalho, Tarefa tarefa);

    Optional<FluxoTrabalhoTarefa> findByFluxoAndTarefa(FluxoTrabalho fluxoTrabalho, Tarefa tarefa, Boolean ativo);

    void updateAtivo(FluxoTrabalhoTarefa fluxoTrabalhoTarefa, boolean b);

    List<FluxoTrabalhoTarefa> findByFluxoTrabalho(FluxoTrabalho fluxoTrabalho);

    Optional<FluxoTrabalhoTarefa> findByDadosBasicosTarefa(DadosBasicosTarefa dadosBasicosTarefa);

    List<FluxoTrabalhoTarefa> findByUsuarioNotificacao(Usuario usuario);
}
