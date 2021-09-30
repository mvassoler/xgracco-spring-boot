package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Pessoa;
import br.com.finchsolucoes.xgracco.domain.entity.PessoaEndereco;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;

public interface PessoaEnderecoJpaRepository {
    List<PessoaEndereco> find(Pessoa pessoa, Query<PessoaEndereco> query);

    Long count(Pessoa pessoa, Query<PessoaEndereco> query);
}
