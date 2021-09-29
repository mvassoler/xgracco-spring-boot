package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.UsuarioEscritorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioEscritorioRepository extends JpaRepository<UsuarioEscritorio,Long>, UsuarioEscritorioJpaRepository {
}
