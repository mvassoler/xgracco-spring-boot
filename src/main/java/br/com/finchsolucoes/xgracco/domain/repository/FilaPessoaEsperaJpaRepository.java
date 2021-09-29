package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.FilaPessoaEspera;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;

public interface FilaPessoaEsperaJpaRepository {
    List<FilaPessoaEspera> find(Query<FilaPessoaEspera> query);

    long count(Query<FilaPessoaEspera> query);
}
