package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.TipoGarantia;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;

public interface TipoGarantiaJpaRepository {

    List<TipoGarantia> find(Query<TipoGarantia> query);

    long count(Query<TipoGarantia> query);
}
