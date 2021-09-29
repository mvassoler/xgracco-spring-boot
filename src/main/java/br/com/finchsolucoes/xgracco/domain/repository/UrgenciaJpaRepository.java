package br.com.finchsolucoes.xgracco.domain.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Urgencia;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import com.querydsl.jpa.impl.JPAQuery;

import java.util.List;
import java.util.Optional;

public interface UrgenciaJpaRepository {

    List<Urgencia> find(Query<Urgencia> query);

    List<Urgencia> findAllCache();

    Optional<Urgencia> findByDescricao(String descricao);

    long count(Query<Urgencia> query);

}
