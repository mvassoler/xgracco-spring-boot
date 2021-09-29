package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.ReferenciaHonorarios;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;

public interface ReferenciaHonorarioJpaRepository {

    List<ReferenciaHonorarios> find(Query<ReferenciaHonorarios> query);

    long count(Query<ReferenciaHonorarios> query);
}
