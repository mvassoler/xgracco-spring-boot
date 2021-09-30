package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.ProcessoDespesaTipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório da Processo Despesa Tipo
 *
 * @author Jordano
 * @since 2.1
 */
@Repository
public interface ProcessoDespesaTipoRepository extends JpaRepository<ProcessoDespesaTipo, Long>,ProcessoDespesaTipoJpaRepository {



}
