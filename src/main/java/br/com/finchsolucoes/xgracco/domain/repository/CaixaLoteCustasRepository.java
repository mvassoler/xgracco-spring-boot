package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.CaixaLoteCustas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaixaLoteCustasRepository extends JpaRepository<CaixaLoteCustas, Long>, CaixaLoteCustasJpaRepository {
}
