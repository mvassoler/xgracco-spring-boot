package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.PreCadastroProcessoTag;
import br.com.finchsolucoes.xgracco.domain.entity.Tag;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;

public interface PreCadastroProcessoTagJpaRepository {
    List<PreCadastroProcessoTag> find(Query<PreCadastroProcessoTag> query);

    long count(Query<PreCadastroProcessoTag> query);

    Long countPreCadastroAtivosByTag(Tag tag, boolean flagAvaliaStatusAtivo);
}
