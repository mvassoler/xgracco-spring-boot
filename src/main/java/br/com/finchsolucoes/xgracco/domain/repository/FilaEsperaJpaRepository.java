package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Fila;
import br.com.finchsolucoes.xgracco.domain.entity.FilaEspera;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;

public interface FilaEsperaJpaRepository {
    List<FilaEspera> find(Query<FilaEspera> query);

    List<FilaEspera> findFila(Fila fila);

    long count(Query<FilaEspera> query);
}
