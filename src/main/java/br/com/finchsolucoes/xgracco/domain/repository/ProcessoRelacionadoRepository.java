package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Processo;
import br.com.finchsolucoes.xgracco.domain.entity.ProcessoRelacionado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository da entidade ProcessoRelacionado
 *
 */
@Repository
public interface ProcessoRelacionadoRepository extends JpaRepository<ProcessoRelacionado, Long>,ProcessoRelacionadoJpaRepository {



}
