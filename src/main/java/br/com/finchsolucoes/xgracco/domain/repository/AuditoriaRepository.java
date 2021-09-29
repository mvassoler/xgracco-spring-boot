package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.legacy.beans.dto.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditoriaRepository extends JpaRepository<Auditoria, Long>, AuditoriaJpaRepository {

}
