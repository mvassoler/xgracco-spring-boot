package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.SalarioMinimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório da entidade SalarioMinimo
 * <p>
 * Created by Jordano on 24/04/2018.
 */
@Repository
public interface SalarioMinimoRepository extends JpaRepository<SalarioMinimo, Long>,SalarioMinimoJpaRepository {

}
