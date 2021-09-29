package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Arquivo;
import br.com.finchsolucoes.xgracco.domain.entity.SolicitacaoAndamento;
import br.com.finchsolucoes.xgracco.domain.entity.SolicitacaoArquivo;
import br.com.finchsolucoes.xgracco.domain.entity.SolicitacaoLog;

import java.util.List;
import java.util.Optional;

public interface SolicitacaoArquivoJpaRepository {
    List<SolicitacaoArquivo> findBySolicitacaoBoomer(Long idSolicitacaoBoomer);

    List<SolicitacaoArquivo> findBySolicitacao(Long id);

    Optional<SolicitacaoArquivo> findBySolicitacaoAndArquivo(SolicitacaoLog solicitacao, Arquivo arquivo);



}
