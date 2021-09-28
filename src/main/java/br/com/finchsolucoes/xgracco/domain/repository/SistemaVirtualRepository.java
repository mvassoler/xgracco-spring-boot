package br.com.finchsolucoes.xgracco.domain.repository;


import br.com.finchsolucoes.xgracco.domain.entity.SistemaVirtual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório da entidade SistemaVirtual.
 *
 * @author guicamargo
 */
@Repository
public interface SistemaVirtualRepository extends JpaRepository<SistemaVirtual, Long>,SistemaVirtualJpaRepository {


}
