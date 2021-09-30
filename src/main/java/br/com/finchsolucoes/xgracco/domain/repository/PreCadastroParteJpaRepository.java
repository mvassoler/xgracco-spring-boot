package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.PreCadastroParte;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;

public interface PreCadastroParteJpaRepository {
    List<PreCadastroParte> find(Query<PreCadastroParte> query);

    long count(Query<PreCadastroParte> query);
}
