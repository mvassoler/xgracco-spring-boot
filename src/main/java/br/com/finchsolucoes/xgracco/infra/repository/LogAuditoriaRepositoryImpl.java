package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.LogAuditoria;
import br.com.finchsolucoes.xgracco.domain.entity.QLogAuditoria;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.LogAuditoriaFilter;
import br.com.finchsolucoes.xgracco.domain.repository.LogAuditoriaJpaRepository;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

/**
 * Created by Jordano on 08/12/2017.
 */
@Repository
public class LogAuditoriaRepositoryImpl extends AbstractJpaRepository<LogAuditoria, Long> implements LogAuditoriaJpaRepository {


    @Override
    public List<LogAuditoria> find(Query<LogAuditoria> query) {
        return null;
    }

    @Override
    public long count(Query<LogAuditoria> query) {
        return 0;
    }

    private JPAQuery<LogAuditoria> createQuery(Query<LogAuditoria> query) {
        final QLogAuditoria qLogAuditoria = QLogAuditoria.logAuditoria;

        final JPAQuery<LogAuditoria> jpaQuery = new JPAQueryFactory(this.entityManager)
                .select(qLogAuditoria)
                .from(qLogAuditoria);

        if (query.getFilter() != null && query.getFilter() instanceof LogAuditoriaFilter) {
            final LogAuditoriaFilter filter = (LogAuditoriaFilter) query.getFilter();

            if (Objects.nonNull(filter.getDataInicio()) && Objects.nonNull(filter.getDataFim())) {
                jpaQuery.where(qLogAuditoria.dataAlteracao.between(filter.getDataInicio(), filter.getDataFim()));
            }

        }

        return jpaQuery;
    }
}
