package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.*;

public interface DadosBasicosTarefaJpaRepository {
    List<DadosBasicosTarefa> find(Query<DadosBasicosTarefa> query);

    Optional<DadosBasicosTarefa> findById(Long id);

    Long countTarefasPendentesPorFilaDevolucao(Fila fila, boolean isFiltrarTempoVisao);

    Long countTarefasPendentesPorFilaAtendimento(Fila fila, boolean isFiltrarTempoVisao, boolean isCountTarefasDevolvidas);

    List<DadosBasicosTarefa> findTarefasBy(Query<DadosBasicosTarefa> query);

    long countTarefasBy(Query<DadosBasicosTarefa> query);

    long countTarefasConcluidas(Query<DadosBasicosTarefa> query);

    List<DadosBasicosTarefa> findByPendentesByFluxoTrabalhoTarefa(FluxoTrabalhoTarefa ftt);

    Long countTarefasPendentesPorUsuario(Long idUsuario);

    Map<Calendar, Long> mapDatacountTarefasConcluidasDataPessoa(Long idPessoa, Calendar dataInicio, Calendar dataFim);

    List<Object[]> countTopProdutividadePorPessoa(List<Long> carteiras, Long top, Calendar dataInicio, Calendar dataFim, List<Long> idEscritorio);

    Long countTarefasConcluidasPorCarteiras(Set<Carteira> carteiras);

    Long countTarefasPendentesPorCarteiras(Set<Carteira> carteiras);

    Map<Escritorio, Long> countTarefasConcluidasPorEscritorio(Set<Carteira> carteiras, Calendar dataInicial, Calendar dataFinal);

    List<DadosBasicosTarefa> findByEscritorioAndCarteiras(Usuario usuario, List<Long> idsEscritorios);

    List<?> retornaTotalSolicitacoesPorDia(Fila fila, Calendar dataInicio, Calendar dataFim);

    List<?> retornaTotalSolicitacoesConcluidasPorDia(Long idFila, Calendar dataInicio, Calendar dataFim);

    long count(Query<DadosBasicosTarefa> query);

    List<DadosBasicosTarefa> findByProcesso(Processo processo);

    Long countTarefasPendentesPorProcesso(Long idProcesso);

    List<DadosBasicosTarefa> findTarefasPendentesPorUsuarioEProcesso(Long idUsuario, Long idProcesso);

    void deleteById(Long id);

    List<DadosBasicosTarefa> findPorContratoClausulaEProcesso(ContratoClausula clausula, Processo processo, Calendar dataInicio, Calendar dataFim);

    Long countPendentesByProcessoAndFluxoTrabalhoTarefa(Processo processo, FluxoTrabalhoTarefa fluxoTrabalhoTarefa);

    List<DadosBasicosTarefa> findPendentesByProcessoAndFluxoTrabalhoTarefa(Processo processo, FluxoTrabalhoTarefa fluxoTrabalhoTarefa);

    List<DadosBasicosTarefa> findVencendoPorNotificacaoFluxo(Query<DadosBasicosTarefa> query);

    Long countVencendoPorNotificacaoFluxo(Query<DadosBasicosTarefa> query);
}
