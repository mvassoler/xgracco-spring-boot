package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.ModeloAgendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeloAgendamentoRepository extends JpaRepository<ModeloAgendamento, Long>, ModeloAgendamentoJpaRepository {

}
