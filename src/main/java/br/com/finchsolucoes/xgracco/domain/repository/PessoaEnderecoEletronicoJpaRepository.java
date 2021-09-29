package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Pessoa;
import br.com.finchsolucoes.xgracco.domain.entity.PessoaEnderecoEletronico;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;

public interface PessoaEnderecoEletronicoJpaRepository {
    List<PessoaEnderecoEletronico> find(Pessoa pessoa, Query<PessoaEnderecoEletronico> query);

    Long count(Pessoa pessoa, Query<PessoaEnderecoEletronico> query);
}
