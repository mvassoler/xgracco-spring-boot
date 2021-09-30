package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface EsteiraTarefaJpaRepository {
    List<EsteiraTarefa> findByEsteira(Esteira esteira);

    List<EsteiraTarefa> findByTarefas(Collection<Tarefa> tarefas);

    void deleteEsteiraTarefa(Long id);

    Optional<EsteiraTarefa> findByEsteiraAndCarteiraAndTarefa(Esteira esteira, Carteira carteira, FluxoTrabalhoTarefa fluxoTrabalhoTarefa);

    List<EsteiraTarefa> find(Query<EsteiraTarefa> query);

    long count(Query<EsteiraTarefa> query);
}
