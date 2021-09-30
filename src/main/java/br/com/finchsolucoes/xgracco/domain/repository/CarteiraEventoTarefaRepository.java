package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.CarteiraEventoTarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarteiraEventoTarefaRepository extends JpaRepository<CarteiraEventoTarefa, Long>, CarteiraEventoTarefaJpaRepository {

}
