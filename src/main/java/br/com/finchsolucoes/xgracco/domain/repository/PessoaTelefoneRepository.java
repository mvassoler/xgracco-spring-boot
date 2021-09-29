package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.PessoaTelefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaTelefoneRepository extends JpaRepository<PessoaTelefone, Long>, PessoaTelefoneJpaRepository {
}
