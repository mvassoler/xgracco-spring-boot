package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long>, PerfilJpaRepository {

}