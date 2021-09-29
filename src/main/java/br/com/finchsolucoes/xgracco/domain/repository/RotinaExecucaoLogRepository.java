package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.RotinaExecucaoLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório da entidade RotinaExecucaoLog.
 *
 * @author Renan Gigliotti
 * @since 2.0
 */
public interface RotinaExecucaoLogRepository extends JpaRepository<RotinaExecucaoLog, Long>,RotinaExecucaoLogJpaRepository {
}