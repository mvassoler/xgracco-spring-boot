package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.DadosBasicosTarefa;
import br.com.finchsolucoes.xgracco.domain.entity.Processo;
import br.com.finchsolucoes.xgracco.domain.entity.Publicacao;

import java.util.Optional;

public interface PublicacaoJpaRepository {
    Optional<Publicacao> findByIdPublicacao(Long idPublicacao);

    Optional<Publicacao> findByDadosBasicosTarefa(final DadosBasicosTarefa dadosBasicosTarefa);

    Long existsPublicacaoByIdPubAndProcesso(Long idPublicacaoIntegracao, Processo processo);
}
