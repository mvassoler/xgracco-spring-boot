package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.QRotinaExecucaoLog;
import br.com.finchsolucoes.xgracco.domain.entity.RotinaExecucaoLog;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.RotinaExecucaoLogFilter;
import br.com.finchsolucoes.xgracco.domain.repository.RotinaExecucaoLogJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade RotinaExecucao.
 *
 * @author Renan Gigliotti
 * @since 2.0
 */
@Repository
public class RotinaExecucaoLogRepositoryImpl extends AbstractJpaRepository<RotinaExecucaoLog, Long> implements RotinaExecucaoLogJpaRepository {


    public List<RotinaExecucaoLog> find(Query<RotinaExecucaoLog> query) {
        final PathBuilder<RotinaExecucaoLog> path = new PathBuilder<>(RotinaExecucaoLog.class, "rotinaExecucaoLog");
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


    public long count(Query<RotinaExecucaoLog> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<RotinaExecucaoLog> createQuery(Query<RotinaExecucaoLog> query) {
        QRotinaExecucaoLog qRotinaExecucaoLog = QRotinaExecucaoLog.rotinaExecucaoLog;

        final JPAQuery<RotinaExecucaoLog> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qRotinaExecucaoLog)
                .from(qRotinaExecucaoLog);

        // filter
        if (query.getFilter() != null && query.getFilter() instanceof RotinaExecucaoLogFilter) {
            final RotinaExecucaoLogFilter rotinaLogFilter = (RotinaExecucaoLogFilter) query.getFilter();

            // id
            if (rotinaLogFilter.getId() != null) {
                jpaQuery.where(qRotinaExecucaoLog.id.eq(rotinaLogFilter.getId()));
            }

            // rotina
            if (rotinaLogFilter.getExecucao() != null) {
                jpaQuery.where(qRotinaExecucaoLog.execucao.eq(rotinaLogFilter.getExecucao()));
            }

            // descricao
            if (StringUtils.isNotEmpty(rotinaLogFilter.getDescricao())) {
                jpaQuery.where(qRotinaExecucaoLog.descricao.likeIgnoreCase("%" + rotinaLogFilter.getDescricao() + "%"));
            }

            // status
            if (CollectionUtils.isNotEmpty(rotinaLogFilter.getStatus())) {
                jpaQuery.where(qRotinaExecucaoLog.status.in(rotinaLogFilter.getStatus()));
            }

            // status
            if (rotinaLogFilter.getDataExecucao() != null) {
                jpaQuery.where(qRotinaExecucaoLog.dataExecucao.eq(rotinaLogFilter.getDataExecucao()));
            }
        }

        return jpaQuery;
    }
}
