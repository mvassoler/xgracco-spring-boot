package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.TipoLogradouro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoLogradouroRepository extends JpaRepository<TipoLogradouro,Long>,TipoLogradouroJpaRepository {
}
