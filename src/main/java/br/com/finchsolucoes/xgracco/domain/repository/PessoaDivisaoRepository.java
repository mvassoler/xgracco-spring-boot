package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.PessoaDivisao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaDivisaoRepository extends JpaRepository<PessoaDivisao, Long>, PessoaDivisaoJpaRepository {
}
