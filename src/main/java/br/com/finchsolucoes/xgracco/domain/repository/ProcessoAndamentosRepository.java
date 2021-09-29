package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Processo;
import br.com.finchsolucoes.xgracco.domain.entity.ProcessoAndamentos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Repositório da entidade {@link ProcessoAndamentos}
 *
 * @author andre.baroni
 */
@Repository
public interface ProcessoAndamentosRepository extends JpaRepository<ProcessoAndamentos, Long>,ProcessoAndamentosJpaRepository {


}
