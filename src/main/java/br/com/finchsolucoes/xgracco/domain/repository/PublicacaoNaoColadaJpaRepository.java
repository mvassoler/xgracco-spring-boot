package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.PublicacaoNaoColada;
import br.com.finchsolucoes.xgracco.domain.enums.EnumStatusPublicacaoNaoColada;

import java.util.Optional;

public interface PublicacaoNaoColadaJpaRepository {
    Long updateStatus(Long id, EnumStatusPublicacaoNaoColada status);

    Optional<PublicacaoNaoColada> findByCodigoRelacional(Long codigoRelacional);

    Long updateStatusAndMotivo(Long id, EnumStatusPublicacaoNaoColada status, String motivo);

    Long countPublicacoesNaoColadasMenu();
}
