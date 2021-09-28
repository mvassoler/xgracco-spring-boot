package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.TipoLogradouro;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;

public interface TipoLogradouroJpaRepository {

    List<TipoLogradouro> find(Query<TipoLogradouro> query);

    long count(Query<TipoLogradouro> query);


}
