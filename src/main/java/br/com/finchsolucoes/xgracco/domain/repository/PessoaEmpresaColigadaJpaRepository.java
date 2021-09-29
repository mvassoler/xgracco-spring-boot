package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Pessoa;
import br.com.finchsolucoes.xgracco.domain.entity.PessoaEmpresaColigada;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;

public interface PessoaEmpresaColigadaJpaRepository {
    List<PessoaEmpresaColigada> find(Pessoa pessoa, Query<PessoaEmpresaColigada> query);

    Long count(Pessoa pessoa, Query<PessoaEmpresaColigada> query);
}
