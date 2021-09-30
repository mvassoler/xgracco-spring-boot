package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.FluxoTrabalhoTarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FluxoTrabalhoTarefaRepository extends JpaRepository<FluxoTrabalhoTarefa, Long>, FluxoTrabalhoTarefaJpaRepository {

}
