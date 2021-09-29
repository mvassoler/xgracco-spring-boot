package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.TarefaStatusFinalAgendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarefaStatusFinalAgendamentoRepository extends JpaRepository<TarefaStatusFinalAgendamento,Long>, TarefaStatusFinalJpaRepository {
}
