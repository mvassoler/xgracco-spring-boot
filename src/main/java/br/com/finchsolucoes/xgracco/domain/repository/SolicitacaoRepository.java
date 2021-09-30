package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.SolicitacaoLog;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Thiago Fogar
 * @since
 */
@Repository
public interface SolicitacaoRepository extends JpaRepository<SolicitacaoLog, Long>, SolicitacaoJpaRepository {


}
