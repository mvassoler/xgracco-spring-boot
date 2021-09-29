package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.ConclusaoAutomatica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConclusaoAutomaticaRepository extends JpaRepository<ConclusaoAutomatica, Long>, ConclusaoAutomaticaJpaRepository {

}
