package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.RotinaExecucao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório da entidade RotinaExecucao.
 *
 * @author Renan Gigliotti
 * @since 2.0
 */
@Repository
public interface RotinaExecucaoRepository extends JpaRepository<RotinaExecucao, Long>,RotinaExecucaoJpaRepository {
}