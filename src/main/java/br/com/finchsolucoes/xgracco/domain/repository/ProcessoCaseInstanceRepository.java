package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.FluxoTrabalho;
import br.com.finchsolucoes.xgracco.domain.entity.Processo;
import br.com.finchsolucoes.xgracco.domain.entity.ProcessoCaseInstance;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Repositório da entidade ProcessoCaseInstance.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
@Repository
public interface ProcessoCaseInstanceRepository extends JpaRepository<ProcessoCaseInstance,Long>,ProcessoCaseInstanceJpaRepository {



}
