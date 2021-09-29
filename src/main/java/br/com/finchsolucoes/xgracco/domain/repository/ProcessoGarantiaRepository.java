
package br.com.finchsolucoes.xgracco.domain.repository;
import br.com.finchsolucoes.xgracco.domain.entity.ProcessoGarantia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ProcessoGarantiaRepository extends JpaRepository<ProcessoGarantia, Long>, ProcessoGarantiaJpaRepository {
}