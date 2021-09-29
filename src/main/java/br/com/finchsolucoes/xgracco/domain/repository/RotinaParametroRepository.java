package br.com.finchsolucoes.xgracco.domain.repository;


import br.com.finchsolucoes.xgracco.domain.entity.RotinaParametro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório da entidade RotinaParametro.
 *
 * @author Renan Gigliotti
 * @since 2.0
 */
@Repository
public interface RotinaParametroRepository extends JpaRepository<RotinaParametro, Long>, RotinaParametroJpaRepository {
}