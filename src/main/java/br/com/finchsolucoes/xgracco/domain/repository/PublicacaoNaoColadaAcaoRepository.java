package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.PublicacaoNaoColadaAcao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author guilhermecamargo
 */
public interface PublicacaoNaoColadaAcaoRepository extends JpaRepository<PublicacaoNaoColadaAcao, Long>,PublicacaoNaoColadaAcaoJpaRepository {


}
