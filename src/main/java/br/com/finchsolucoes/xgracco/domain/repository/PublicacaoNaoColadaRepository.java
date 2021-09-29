package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.PublicacaoNaoColada;
import br.com.finchsolucoes.xgracco.domain.enums.EnumStatusPublicacaoNaoColada;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositório da entidade PublicacaoNaoColada
 * @author guilhermecamargo
 */
public interface PublicacaoNaoColadaRepository extends JpaRepository<PublicacaoNaoColada, Long>,PublicacaoNaoColadaJpaRepository {

}
