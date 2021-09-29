package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Pessoa;
import br.com.finchsolucoes.xgracco.domain.entity.PessoaDivisao;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;

public interface PessoaDivisaoJpaRepository {
    List<PessoaDivisao> find(Pessoa pessoa, Query<PessoaDivisao> query);

    List<PessoaDivisao> findAllCache();

    Long count(Pessoa pessoa, Query<PessoaDivisao> query);
}
