package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.GrupoCampoCarteira;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoCampoCarteiraRepository extends JpaRepository<GrupoCampoCarteira, Long>, GrupoCampoCarteiraJpaRepository {

}
