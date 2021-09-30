package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.PossibilidadePerda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PossibilidadePerdaRepository extends JpaRepository<PossibilidadePerda, Long>, PossibilidadePerdaJpaRepository {

}
