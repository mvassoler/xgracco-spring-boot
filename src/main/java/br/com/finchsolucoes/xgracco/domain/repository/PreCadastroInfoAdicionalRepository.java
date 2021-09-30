package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.PreCadastroInfoAdicional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreCadastroInfoAdicionalRepository extends JpaRepository<PreCadastroInfoAdicional, Long>, PreCadastroInfoAdicionalJpaRepository {

}
