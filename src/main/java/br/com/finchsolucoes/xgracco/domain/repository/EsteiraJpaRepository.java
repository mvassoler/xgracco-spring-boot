package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Esteira;
import br.com.finchsolucoes.xgracco.domain.entity.FluxoTrabalhoTarefa;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EsteiraJpaRepository {
    List<Esteira> find(Query<Esteira> query);

    long count(Query<Esteira> query);

    List<Esteira> findByFluxoTrabalhoTarefa(FluxoTrabalhoTarefa ftt);
}
