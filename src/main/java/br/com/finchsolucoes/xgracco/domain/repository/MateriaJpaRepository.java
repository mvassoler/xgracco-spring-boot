package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Materia;
import br.com.finchsolucoes.xgracco.domain.entity.Processo;
import br.com.finchsolucoes.xgracco.domain.query.Query;

import java.util.List;

public interface MateriaJpaRepository {
    List<Materia> find(Query<Materia> query);

    List<Materia> findAllCache();

    long count(Query<Materia> query);

    List<Processo> findByProcesso(Long id);
}
