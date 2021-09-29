package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.FilaPessoaEspera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilaPessoaEsperaRepository extends JpaRepository<FilaPessoaEspera, Long>, FilaPessoaEsperaJpaRepository {

}
