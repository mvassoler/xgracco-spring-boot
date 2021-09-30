package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.UsuarioDashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioDashboardRepository extends JpaRepository<UsuarioDashboard,Long>, UsuarioDashboardJpaRepository {
}
