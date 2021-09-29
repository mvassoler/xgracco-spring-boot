package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Processo;
import br.com.finchsolucoes.xgracco.domain.entity.ProcessoRelacionado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository da entidade ProcessoRelacionado
 *
 */
public interface ProcessoRelacionadoRepository extends JpaRepository<ProcessoRelacionado, Long>,ProcessoRelacionadoJpaRepository {



}
