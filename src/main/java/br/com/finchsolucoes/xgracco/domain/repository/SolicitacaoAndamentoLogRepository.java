package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.SolicitacaoAndamento;
import br.com.finchsolucoes.xgracco.domain.entity.SolicitacaoAndamentoLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Interface responsável por todos os casos de repositório relacionados à entidade {@link SolicitacaoAndamentoLog}.
 *
 * @author andre.baroni
 * @since 18/04/2019
 */
@Repository
public interface SolicitacaoAndamentoLogRepository extends JpaRepository<SolicitacaoAndamentoLog, Long>,SolicitacaoAndamentoLogJpaRepository {

}
