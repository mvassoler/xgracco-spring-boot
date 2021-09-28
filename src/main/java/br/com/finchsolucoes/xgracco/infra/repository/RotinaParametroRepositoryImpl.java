package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.QRotinaParametro;
import br.com.finchsolucoes.xgracco.domain.entity.Rotina;
import br.com.finchsolucoes.xgracco.domain.entity.RotinaParametro;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.RotinaParametroFilter;
import br.com.finchsolucoes.xgracco.domain.repository.RotinaParametroJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade RotinaParametro.
 *
 * @author Renan Gigliotti
 * @since 2.1
 */
@Repository
public class RotinaParametroRepositoryImpl extends AbstractJpaRepository<RotinaParametro, Long> implements RotinaParametroJpaRepository {


    public List<RotinaParametro> find(Query<RotinaParametro> query) {
        final PathBuilder<Rotina> path = new PathBuilder<>(Rotina.class, "rotinaParametro");
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


    public long count(Query<RotinaParametro> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<RotinaParametro> createQuery(Query<RotinaParametro> query) {
        QRotinaParametro qRotinaParametro = QRotinaParametro.rotinaParametro;

        final JPAQuery<RotinaParametro> jpaQuery = new JPAQueryFactory(entityManager)
                .selectDistinct(qRotinaParametro)
                .from(qRotinaParametro)
                .leftJoin(qRotinaParametro.rotina).fetchJoin();

        // filter
        if (query.getFilter() != null && query.getFilter() instanceof RotinaParametroFilter) {
            final RotinaParametroFilter rotinaParametroFilter = (RotinaParametroFilter) query.getFilter();

            // parametro
            if (StringUtils.isNotEmpty(rotinaParametroFilter.getParametro())) {
                jpaQuery.where(qRotinaParametro.parametro.likeIgnoreCase("%" + rotinaParametroFilter.getParametro() + "%"));
            }
        }

        return jpaQuery;
    }
}
