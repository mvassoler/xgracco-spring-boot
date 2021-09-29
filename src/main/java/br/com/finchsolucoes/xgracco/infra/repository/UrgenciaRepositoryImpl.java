package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.QUrgencia;
import br.com.finchsolucoes.xgracco.domain.entity.Urgencia;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.UrgenciaFilter;
import br.com.finchsolucoes.xgracco.domain.repository.UrgenciaJpaRepository;
import br.com.finchsolucoes.xgracco.domain.repository.UrgenciaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by deivy.spaulonci
 */
@Repository
public class UrgenciaRepositoryImpl extends AbstractJpaRepository<Urgencia, Long> implements UrgenciaJpaRepository {


    public List<Urgencia> find(Query<Urgencia> query) {
        final JPAQuery<Urgencia> jpaQuery = createQuery(query);
        final PathBuilder<Urgencia> entityPath = new PathBuilder<>(Urgencia.class, "urgencia");

        if (query.getSorter() != null && StringUtils.isNotBlank(query.getSorter().getProperty())) {
            if (query.getSorter().isDesc()) {
                jpaQuery.orderBy(entityPath.getString(query.getSorter().getProperty()).desc());
            } else {
                jpaQuery.orderBy(entityPath.getString(query.getSorter().getProperty()).asc());
            }
        } else {
            jpaQuery.orderBy(entityPath.getString("id").asc());
        }

        if (Optional.ofNullable(query.getPage()).orElse(0L) > 0L) {
            jpaQuery.offset((query.getPage() - 1L) * query.getLimit());
        }

        jpaQuery.limit(query.getLimit());

        return jpaQuery.fetch();
    }


    public List<Urgencia> findAllCache() {

        final QUrgencia qUrgencia = QUrgencia.urgencia;

        final JPAQuery<Urgencia> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qUrgencia)
                .from(qUrgencia);

        return jpaQuery.fetch();
    }


    public Optional<Urgencia> findByDescricao(String descricao) {
        final QUrgencia qUrgencia = QUrgencia.urgencia;

        return Optional.ofNullable(
                new JPAQueryFactory(entityManager)
                .select(qUrgencia)
                .from(qUrgencia)
                .where(qUrgencia.descricao.likeIgnoreCase("%"+descricao+"%")).fetchFirst());
    }



    public long count(Query<Urgencia> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<Urgencia> createQuery(Query<Urgencia> query) {
        final QUrgencia qUrgencia = QUrgencia.urgencia;

        final JPAQuery<Urgencia> jpaQuery = new JPAQueryFactory(this.entityManager)
                .select(qUrgencia)
                .from(qUrgencia);

        if (query.getFilter() != null && query.getFilter() instanceof UrgenciaFilter) {
            final UrgenciaFilter urgenciaFilter = (UrgenciaFilter) query.getFilter();

            if (StringUtils.isNotBlank(urgenciaFilter.getDescricao())) {
                jpaQuery.where(qUrgencia.descricao.likeIgnoreCase("%" + urgenciaFilter.getDescricao() + "%"));
            }
        }

        return jpaQuery;
    }
}
