package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.PublicacaoNaoColadaAcao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author guilhermecamargo
 */
@Repository
public interface PublicacaoNaoColadaAcaoRepository extends JpaRepository<PublicacaoNaoColadaAcao, Long>,PublicacaoNaoColadaAcaoJpaRepository {


}
