package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Pessoa;
import br.com.finchsolucoes.xgracco.domain.entity.PessoaOab;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;

public interface PessoaOabJpaRepository {
    List<PessoaOab> find(Pessoa pessoa, Query<PessoaOab> query);

    Long count(Pessoa pessoa, Query<PessoaOab> query);
}
