package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Processo;
import br.com.finchsolucoes.xgracco.domain.entity.ProcessoDependency;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @author Jordano
 * @since 2.2
 */
public interface ProcessoDependencyRepository<T extends ProcessoDependency, ID> extends Serializable {

    List<T> find(Processo processo, Query<T> query);

    Optional<T> findById(Processo processo, ID id);

    Optional<T> findByProcessoAndId(Processo processo, ID id);

    Long count(Processo processo, Query<T> query);

    void create(T entity);

    void update(T entity);

    void remove(T entity);

    void removeById(Processo processo, ID id);

}
