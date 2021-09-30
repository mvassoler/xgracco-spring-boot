package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Processo;
import br.com.finchsolucoes.xgracco.domain.entity.ProcessoParte;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositório da entidade Parte.
 *
 * @author Renan Gigliotti
 * @since 2.1
 */

public interface ProcessoParteRepository extends JpaRepository<ProcessoParte, Long>,ProcessoParteJpaRepository {


}
