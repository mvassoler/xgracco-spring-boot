package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.PessoaEmpresaColigada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaEmpresaColigadaRepository extends JpaRepository<PessoaEmpresaColigada, Long>, PessoaEmpresaColigadaJpaRepository {

}
