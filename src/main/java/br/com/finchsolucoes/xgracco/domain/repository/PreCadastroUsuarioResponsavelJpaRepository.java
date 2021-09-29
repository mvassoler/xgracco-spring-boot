package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.PreCadastroUsuarioResponsavel;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.PreCadastroUsuarioResponsavelFilter;

import java.util.List;
import java.util.Optional;

public interface PreCadastroUsuarioResponsavelJpaRepository {
    List<PreCadastroUsuarioResponsavel> find(Query<PreCadastroUsuarioResponsavel> query);

    long count(Query<PreCadastroUsuarioResponsavel> query);

    Optional<PreCadastroUsuarioResponsavel> findPreCadastroProcesso(PreCadastroUsuarioResponsavelFilter preCadastroUsuarioResponsavelFilter);
}
