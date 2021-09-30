package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.PreCadastroProcesso;
import br.com.finchsolucoes.xgracco.domain.entity.Processo;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;
import java.util.Optional;

public interface PreCadastroProcessoJpaRepository {
    List<PreCadastroProcesso> find(Query<PreCadastroProcesso> query);

    long count(Query<PreCadastroProcesso> query);

    List<PreCadastroProcesso> findAllPreCadastroPartes(Long idParte);

    Optional<PreCadastroProcesso> findByProcesso(Processo processo);
}
