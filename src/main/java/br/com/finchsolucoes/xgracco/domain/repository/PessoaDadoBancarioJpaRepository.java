package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Pessoa;
import br.com.finchsolucoes.xgracco.domain.entity.PessoaDadoBancario;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;

public interface PessoaDadoBancarioJpaRepository {
    List<PessoaDadoBancario> find(Pessoa pessoa, Query<PessoaDadoBancario> query);

    Long count(Pessoa pessoa, Query<PessoaDadoBancario> query);
}
