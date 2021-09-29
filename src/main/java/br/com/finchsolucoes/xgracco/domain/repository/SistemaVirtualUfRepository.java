package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.SistemaVirtualUf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SistemaVirtualUfRepository extends JpaRepository<SistemaVirtualUf, Long>,SistemaVirtualUfJpaRepository {

}
