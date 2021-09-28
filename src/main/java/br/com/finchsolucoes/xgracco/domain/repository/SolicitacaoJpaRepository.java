package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.SolicitacaoLog;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;
import java.util.Optional;

public interface SolicitacaoJpaRepository {

    Optional<SolicitacaoLog> findBySolicitacaoBoomer(Long idSolicitacao);

    List<SolicitacaoLog> findByProcesso(Long idProcesso);

    List<SolicitacaoLog> findSolicitacoesProcesso(Query<SolicitacaoLog> query);

    long countSolicitacoesProcessoTutela(Query<SolicitacaoLog> query);
}
