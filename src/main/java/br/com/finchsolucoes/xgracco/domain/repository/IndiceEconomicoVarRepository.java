package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.IndiceEconomicoVar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndiceEconomicoVarRepository extends JpaRepository<IndiceEconomicoVar, Long>, IndiceEconomicoVarJpaRepository {

}
