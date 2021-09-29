package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.ContratoClausula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContratoClausulaRepository extends JpaRepository<ContratoClausula, Long>, ContratoClausulaJpaRepository {

}
