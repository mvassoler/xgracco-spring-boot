package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.TipoGarantia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoGarantiaRepository extends JpaRepository<TipoGarantia,Long>, TipoGarantiaJpaRepository {
}
