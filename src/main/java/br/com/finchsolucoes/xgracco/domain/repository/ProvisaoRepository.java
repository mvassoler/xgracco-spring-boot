package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Provisao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;

/**
 * Repositório da entidade Provisão
 *
 * @author Paulo Marçon
 * @since 2.1.04
 */
@Repository
public interface ProvisaoRepository extends JpaRepository<Provisao, Long>,ProvisaoJpaRepository {


}
