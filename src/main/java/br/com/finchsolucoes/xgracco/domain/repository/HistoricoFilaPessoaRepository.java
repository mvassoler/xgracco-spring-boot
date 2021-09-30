package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.HistoricoFilaPessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricoFilaPessoaRepository extends JpaRepository<HistoricoFilaPessoa, Long>, HistoricoFilaPessoaJpaRepository {

}
