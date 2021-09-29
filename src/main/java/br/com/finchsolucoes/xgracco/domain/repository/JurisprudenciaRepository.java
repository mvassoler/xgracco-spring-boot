package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Jurisprudencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JurisprudenciaRepository extends JpaRepository<Jurisprudencia, Long>, JurisprudenciaJpaRepository {

}
