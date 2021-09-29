package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.PreCadastroInfoAdicional;
import br.com.finchsolucoes.xgracco.domain.entity.PreCadastroProcesso;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;

public interface PreCadastroInfoAdicionalJpaRepository {
    List<PreCadastroInfoAdicional> find(Query<PreCadastroInfoAdicional> query);

    long count(Query<PreCadastroInfoAdicional> query);

    void removeByPreCadastroProcesso(PreCadastroProcesso preCadastroProcesso);
}
