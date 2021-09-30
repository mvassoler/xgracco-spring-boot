package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Carteira;
import br.com.finchsolucoes.xgracco.domain.entity.Honorario;
import br.com.finchsolucoes.xgracco.domain.entity.Pessoa;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;

public interface HonorarioJpaRepository {
    List<Honorario> find(Query<Honorario> query);

    long count(Query<Honorario> query);

    List<Honorario> findHonorarios(Pessoa cliente, Carteira carteira);
}
