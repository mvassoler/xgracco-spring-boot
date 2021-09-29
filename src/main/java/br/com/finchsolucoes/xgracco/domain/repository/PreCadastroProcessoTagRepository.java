package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.PreCadastroProcessoTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreCadastroProcessoTagRepository extends JpaRepository<PreCadastroProcessoTag, Long>, PreCadastroProcessoTagJpaRepository {
}
