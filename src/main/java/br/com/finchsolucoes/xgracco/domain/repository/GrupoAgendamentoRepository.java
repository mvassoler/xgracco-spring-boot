package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.GrupoAgendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoAgendamentoRepository extends JpaRepository<GrupoAgendamento, Long>, GrupoAgendamentoJpaRepository {

}
