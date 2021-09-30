package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.ImportacaoPlanilha;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;

public interface ImportacaoPlanilhaJpaRepository {
    List<ImportacaoPlanilha> find(Query<ImportacaoPlanilha> query);

    long count(Query<ImportacaoPlanilha> query);
}
