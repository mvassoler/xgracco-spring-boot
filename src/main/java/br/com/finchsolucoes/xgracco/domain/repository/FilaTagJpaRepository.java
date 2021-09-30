package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.FilaTag;
import br.com.finchsolucoes.xgracco.domain.entity.Tag;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;

public interface FilaTagJpaRepository {
    List<FilaTag> find(Query<FilaTag> query);

    long count(Query<FilaTag> query);

    Long countFilaByTag(Tag tag);
}
