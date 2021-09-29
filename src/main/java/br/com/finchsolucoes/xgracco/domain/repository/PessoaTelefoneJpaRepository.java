package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Pessoa;
import br.com.finchsolucoes.xgracco.domain.entity.PessoaTelefone;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;

public interface PessoaTelefoneJpaRepository {
    List<PessoaTelefone> find(Pessoa pessoa, Query<PessoaTelefone> query);

    Long count(Pessoa pessoa, Query<PessoaTelefone> query);
}
