package br.com.finchsolucoes.xgracco.infra.repository;


import br.com.finchsolucoes.xgracco.domain.entity.QSistemaVirtual;
import br.com.finchsolucoes.xgracco.domain.entity.SistemaVirtual;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.SistemaVirtualFilter;
import br.com.finchsolucoes.xgracco.domain.repository.SistemaVirtualJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SistemaVirtualRepositoryImpl extends AbstractJpaRepository<SistemaVirtual, Long> implements SistemaVirtualJpaRepository {


    public List<SistemaVirtual> find(Query<SistemaVirtual> query) {
        final PathBuilder<SistemaVirtual> path = new PathBuilder<>(SistemaVirtual.class, "sistemaVirtual");
        final JPAQuery jpaQuery = createQuery(query);

        // order
        if (query.getSorter() != null && StringUtils.isNotBlank(query.getSorter().getProperty())) {
            if (query.getSorter().isDesc()) {
                jpaQuery.orderBy(path.getString(query.getSorter().getProperty()).desc());
            } else {
                jpaQuery.orderBy(path.getString(query.getSorter().getProperty()).asc());
            }
        } else {
            jpaQuery.orderBy(path.getString("id").asc());
        }

        // page
        if (Optional.ofNullable(query.getPage()).orElse(0L) > 0L) {
            jpaQuery.offset(((query.getPage() - 1L) * query.getLimit()));
        }

        // limit
        jpaQuery.limit(query.getLimit());

        return jpaQuery.fetch();
    }


    public List<SistemaVirtual> findAllCache() {

        final QSistemaVirtual qSistemaVirtual = QSistemaVirtual.sistemaVirtual;

        final JPAQuery<SistemaVirtual> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qSistemaVirtual)
                .from(qSistemaVirtual);

        return jpaQuery.fetch();
    }


    public long count(Query<SistemaVirtual> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<SistemaVirtual> createQuery(Query<SistemaVirtual> query) {
        QSistemaVirtual qSistemaVirtual = QSistemaVirtual.sistemaVirtual;


        final JPAQuery<SistemaVirtual> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qSistemaVirtual)
                .from(qSistemaVirtual);

        // filter
        if (query.getFilter() != null && query.getFilter() instanceof SistemaVirtualFilter) {
            final SistemaVirtualFilter sistemaVirtualFilter = (SistemaVirtualFilter) query.getFilter();

            // descricao
            if (StringUtils.isNotEmpty(sistemaVirtualFilter.getDescricao())) {
                jpaQuery.where(qSistemaVirtual.descricao.likeIgnoreCase("%" + sistemaVirtualFilter.getDescricao() + "%"));
            }
        }

        return jpaQuery;
    }


    public List<SistemaVirtual> findByDescricao(String sistemaDescricao) {
        QSistemaVirtual qSistemaVirtual = QSistemaVirtual.sistemaVirtual;

        return new JPAQueryFactory(entityManager)
                .select(qSistemaVirtual)
                .from(qSistemaVirtual)
                .where(qSistemaVirtual.descricao.equalsIgnoreCase(sistemaDescricao))
                .fetch();
    }
}
