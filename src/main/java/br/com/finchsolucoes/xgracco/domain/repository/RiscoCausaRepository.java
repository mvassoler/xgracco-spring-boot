package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.RiscoCausa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Interface repository da entidade RiscoCausa
 *
 * @author Paulo Marçon
 * @since 2.1
 */
public interface RiscoCausaRepository extends JpaRepository<RiscoCausa, Long>, RiscoCausaJpaRepository {


}
