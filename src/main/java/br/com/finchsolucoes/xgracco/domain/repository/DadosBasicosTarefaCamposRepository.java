package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.DadosBasicosTarefaCampos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DadosBasicosTarefaCamposRepository extends JpaRepository<DadosBasicosTarefaCampos, Long>, DadosBasicosTarefaCamposJpaRepository {

}
