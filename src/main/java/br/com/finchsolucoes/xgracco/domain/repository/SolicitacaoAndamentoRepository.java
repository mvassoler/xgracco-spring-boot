package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.SolicitacaoAndamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interface responsável por todos os casos de repositório relacionados à entidade {@link SolicitacaoAndamento}.
 *
 * @author andre.baroni
 * @since 18/04/2019
 */
@Repository
public interface SolicitacaoAndamentoRepository extends JpaRepository<SolicitacaoAndamento, Long>,SolicitacaoAndamentoJpaRepository {


}
