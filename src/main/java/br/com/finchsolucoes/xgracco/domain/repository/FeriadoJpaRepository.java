package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Feriado;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeriadoJpaRepository {
    List<Feriado> find(Query<Feriado> query);

    long count(Query<Feriado> query);
}
