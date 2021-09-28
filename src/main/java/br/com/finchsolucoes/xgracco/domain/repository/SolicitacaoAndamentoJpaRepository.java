package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.SolicitacaoAndamento;

import java.util.Optional;

public interface SolicitacaoAndamentoJpaRepository {
    Optional<SolicitacaoAndamento> findBySolicitacao(String solicitacao);
}
