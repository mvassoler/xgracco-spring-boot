package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.FluxoTrabalho;
import br.com.finchsolucoes.xgracco.domain.entity.FluxoTrabalhoTarefa;
import br.com.finchsolucoes.xgracco.domain.entity.Tarefa;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Repositório da entidade Tarefa.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
public interface TarefaRepository extends Serializable {

    /**
     * Consulta as tarefas do fluxo de trabalho.
     *
     * @return
     */
    List<FluxoTrabalhoTarefa> findByFluxoTrabalho(FluxoTrabalho fluxoTrabalho, Query<Tarefa> query);

    List<FluxoTrabalhoTarefa> findByFluxoTrabalhoNoLimit(FluxoTrabalho fluxoTrabalho, Query<Tarefa> query);

    /**
     * Consulta o total de tarefas do fluxo de trabalho.
     *
     * @param query
     * @return
     */
    long countByFluxoTrabalho(FluxoTrabalho fluxoTrabalho, Query<Tarefa> query);

    /**
     * Insere uma nova tarefa.
     *
     * @param tarefa
     */
    void create(Tarefa tarefa);

    /**
     * Insere novas tarefas.
     *
     * @param tarefas
     */
    void create(Collection<Tarefa> tarefas);

    /**
     * Altera uma tarefa.
     *
     * @param tarefa
     */
    void update(Tarefa tarefa);

    /**
     * Remove uma tarefa.
     *
     * @param tarefa
     */
    void remove(Tarefa tarefa);

    Optional<Tarefa> findById(Long idTarefa);

    List<Tarefa> find(Query<Tarefa> query);

    long count(Query<Tarefa> query);

    Long validarInativacaoTarefa(Tarefa tarefa);

    /**
     * Retorna uma tarefa através do fluxo trabalho tarefa.
     *
     * @return um optional de {@link Tarefa}.
     */
    Optional<Tarefa> findByFluxoTrabalhoTarefa(FluxoTrabalhoTarefa fluxoTrabalhoTarefa);

    /**
     * Retorna uma tarefa través de seu nome.
     *
     * @param nome nome a ser filtrado.
     * @return um optional de {@link Tarefa};
     */
    Optional<Tarefa> findByNome(String nome);
}
