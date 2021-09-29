package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.TarefaStatusFinal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarefaStatusFinalRepository extends JpaRepository<TarefaStatusFinal,Long>, TarefaStatusFinalJpaRepository {
}
