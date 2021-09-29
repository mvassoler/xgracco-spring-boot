package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.PreCadastroUsuarioResponsavel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreCadastroUsuarioResponsavelRepository extends JpaRepository<PreCadastroUsuarioResponsavel, Long>, PreCadastroUsuarioResponsavelJpaRepository {

}
