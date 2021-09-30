package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.IndiceEconomico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndiceEconomicoRepository extends JpaRepository<IndiceEconomico, Long>, IndiceEconomicoJpaRepository {

}
