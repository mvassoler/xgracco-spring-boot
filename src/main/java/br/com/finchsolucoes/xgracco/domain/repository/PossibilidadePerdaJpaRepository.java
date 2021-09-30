package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.PossibilidadePerda;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;

public interface PossibilidadePerdaJpaRepository {
    List<PossibilidadePerda> find(Query<PossibilidadePerda> query);

    long count(Query<PossibilidadePerda> query);
}
