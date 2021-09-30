package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.QSalarioMinimo;
import br.com.finchsolucoes.xgracco.domain.entity.SalarioMinimo;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.SalarioMinimoFilter;
import br.com.finchsolucoes.xgracco.domain.repository.SalarioMinimoJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by felipiberdun on 29/12/2016.
 */
@Repository
public class SalarioMinimoRepositoryImpl extends AbstractJpaRepository<SalarioMinimo, Long> implements SalarioMinimoJpaRepository {


    public List<SalarioMinimo> find(Query<SalarioMinimo> query) {
        final JPAQuery<SalarioMinimo> jpaQuery = createQuery(query);
        final PathBuilder<SalarioMinimo> entityPath = new PathBuilder<>(SalarioMinimo.class, "salarioMinimo");

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


    public long count(Query<SalarioMinimo> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<SalarioMinimo> createQuery(Query<SalarioMinimo> query) {
        final QSalarioMinimo qSalarioMinimo = QSalarioMinimo.salarioMinimo;

        final JPAQuery<SalarioMinimo> jpaQuery = new JPAQueryFactory(this.entityManager)
                .select(qSalarioMinimo)
                .from(qSalarioMinimo);

        if (query.getFilter() != null && query.getFilter() instanceof SalarioMinimoFilter) {
            final SalarioMinimoFilter filter = (SalarioMinimoFilter) query.getFilter();

            if (filter.getAno() != null) {
                jpaQuery.where(qSalarioMinimo.ano.eq(filter.getAno()));
            }

            if (filter.getValor() != null) {
                jpaQuery.where(qSalarioMinimo.valor.eq(filter.getValor()));
            }
        }

        return jpaQuery;
    }

}
