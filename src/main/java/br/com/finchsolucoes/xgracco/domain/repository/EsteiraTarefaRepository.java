package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.EsteiraTarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EsteiraTarefaRepository extends JpaRepository<EsteiraTarefa, Long>, EsteiraTarefaJpaRepository {

}