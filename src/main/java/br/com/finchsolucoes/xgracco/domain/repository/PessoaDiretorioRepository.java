package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.PessoaDiretorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaDiretorioRepository extends JpaRepository<PessoaDiretorio, Long>, PessoaDiretorioJpaRepository {
}
