package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Jurisprudencia;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;

public interface JurisprudenciaJpaRepository {
    List<Jurisprudencia> find(Query<Jurisprudencia> query);

    long count(Query<Jurisprudencia> query);
}
