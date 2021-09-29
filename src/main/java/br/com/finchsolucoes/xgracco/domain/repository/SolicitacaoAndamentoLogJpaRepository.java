package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.SolicitacaoAndamento;
import br.com.finchsolucoes.xgracco.domain.entity.SolicitacaoAndamentoLog;

import java.util.Collection;

public interface SolicitacaoAndamentoLogJpaRepository {

    Collection<SolicitacaoAndamentoLog> findBySolicitacaoAndamentoAndNumeroProcesso(SolicitacaoAndamento solicitacaoAndamento, String processoCnj);

}
