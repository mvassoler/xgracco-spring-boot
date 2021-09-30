package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.DadosBasicosTarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DadosBasicosTarefaRepository extends JpaRepository<DadosBasicosTarefa, Long>, DadosBasicosTarefaJpaRepository{
}
