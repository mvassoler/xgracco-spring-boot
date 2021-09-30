package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.PessoaEnderecoEletronico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaEnderecoEletronicoRepository extends JpaRepository<PessoaEnderecoEletronico, Long>, PessoaEnderecoEletronicoJpaRepository {

}
