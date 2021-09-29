package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.ReferenciaHonorarios;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Repositório da entidade Referencia Honorario.
 *
 * @author Jordano
 * @since 2.1
 */
public interface ReferenciaHonorarioRepository extends JpaRepository<ReferenciaHonorarios, Long>,ReferenciaHonorarioJpaRepository {


}
