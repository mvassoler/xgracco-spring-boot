package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.LoteCustas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoteCustasRepository extends JpaRepository<LoteCustas, Long>, LoteCustasJpaRepository {
}
