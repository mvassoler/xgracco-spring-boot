package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.PessoaEmpresaColaborador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaEmpresaColaboradorRepository extends JpaRepository<PessoaEmpresaColaborador, Long>, PessoaEmpresaColaboradorJpaRepository {

}
