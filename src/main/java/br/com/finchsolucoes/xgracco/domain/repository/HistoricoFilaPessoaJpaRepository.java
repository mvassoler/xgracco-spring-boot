package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.DadosBasicosTarefa;
import br.com.finchsolucoes.xgracco.domain.entity.HistoricoFilaPessoa;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public interface HistoricoFilaPessoaJpaRepository {
    List<HistoricoFilaPessoa> find(Query<HistoricoFilaPessoa> query);

    long count(Query<HistoricoFilaPessoa> query);

    Long countTarefasTratadasPorFilaDevolucao(Long idFila, Long idPessoa, Calendar dataInicio, Calendar dataFim);

    Optional<HistoricoFilaPessoa> findByDadosBasicosTarefa(DadosBasicosTarefa dadosBasicosTarefa);
}
