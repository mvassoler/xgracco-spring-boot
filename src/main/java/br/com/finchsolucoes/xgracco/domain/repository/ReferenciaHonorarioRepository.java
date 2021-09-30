package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.ReferenciaHonorarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repositório da entidade Referencia Honorario.
 *
 * @author Jordano
 * @since 2.1
 */
@Repository
public interface ReferenciaHonorarioRepository extends JpaRepository<ReferenciaHonorarios, Long>,ReferenciaHonorarioJpaRepository {


}
