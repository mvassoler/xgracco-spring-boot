package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.PreCadastroProcesso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreCadastroProcessoRepository extends JpaRepository<PreCadastroProcesso, Long>, PreCadastroProcessoJpaRepository {

}
