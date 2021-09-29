package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Pessoa;
import br.com.finchsolucoes.xgracco.domain.entity.PessoaEmpresaColaborador;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;

public interface PessoaEmpresaColaboradorJpaRepository {
    List<PessoaEmpresaColaborador> find(Pessoa pessoa, Query<PessoaEmpresaColaborador> query);

    Long count(Pessoa pessoa, Query<PessoaEmpresaColaborador> query);
}
