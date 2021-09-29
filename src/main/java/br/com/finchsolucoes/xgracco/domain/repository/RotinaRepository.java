package br.com.finchsolucoes.xgracco.domain.repository;


import br.com.finchsolucoes.xgracco.domain.entity.Rotina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório da entidade Rotina.
 *
 * @author Renan Gigliotti
 * @since 2.0
 */
@Repository
public interface RotinaRepository extends JpaRepository<Rotina, Long>,RotinaJpaRepository {
}