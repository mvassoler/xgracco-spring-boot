package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.PreCadastroParte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreCadastroParteRepository extends JpaRepository<PreCadastroParte, Long>, PreCadastroParteJpaRepository{

}
