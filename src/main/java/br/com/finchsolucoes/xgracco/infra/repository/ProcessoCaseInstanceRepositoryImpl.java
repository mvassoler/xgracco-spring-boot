package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.FluxoTrabalho;
import br.com.finchsolucoes.xgracco.domain.entity.Processo;
import br.com.finchsolucoes.xgracco.domain.entity.ProcessoCaseInstance;
import br.com.finchsolucoes.xgracco.domain.entity.QProcessoCaseInstance;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.ProcessoCaseInstanceFilter;
import br.com.finchsolucoes.xgracco.domain.repository.ProcessoCaseInstanceJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

/**
 * Repositório da entidade ProcessoCaseInstance.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
@Repository
public class ProcessoCaseInstanceRepositoryImpl implements ProcessoCaseInstanceJpaRepository {

    @PersistenceContext
    private EntityManager entityManager;


    public void create(Collection<ProcessoCaseInstance> processosCaseInstances) {
        int i = 1;
        for (ProcessoCaseInstance processoCaseInstance : processosCaseInstances) {
            entityManager.persist(processoCaseInstance);

            if (i % 50 == 0) {
                entityManager.flush();
                entityManager.clear();
            }
            i++;
        }

        entityManager.flush();
        entityManager.clear();
    }

    public List<ProcessoCaseInstance> find(Query<ProcessoCaseInstance> query) {
        final JPAQuery<ProcessoCaseInstance> jpaQuery = createQuery(query);
        final PathBuilder<ProcessoCaseInstance> builder = new PathBuilder<>(ProcessoCaseInstance.class, "processoCaseInstance");

        jpaQuery.orderBy(builder.getString("caseInstanceId").desc());

        if (Optional.ofNullable(query.getPage()).orElse(0L) > 0L) {
            jpaQuery.offset((query.getPage() - 1L) * query.getLimit());
        }

        jpaQuery.limit(query.getLimit());

        return jpaQuery.fetch();
    }

    private JPAQuery<ProcessoCaseInstance> createQuery(Query<ProcessoCaseInstance> query) {
        final QProcessoCaseInstance qProcessoCaseInstance = QProcessoCaseInstance.processoCaseInstance;

        final JPAQuery<ProcessoCaseInstance> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qProcessoCaseInstance)
                .from(qProcessoCaseInstance);

        if (query.getFilter() != null && query.getFilter() instanceof ProcessoCaseInstanceFilter) {
            ProcessoCaseInstanceFilter filter = (ProcessoCaseInstanceFilter) query.getFilter();

            if (StringUtils.isNotBlank(filter.getCaseInstanceId())) {
                jpaQuery.where(qProcessoCaseInstance.caseInstanceId.eq(filter.getCaseInstanceId()));
            }

            if (Objects.nonNull(filter.getProcesso())) {
                jpaQuery.where(qProcessoCaseInstance.processo.eq(filter.getProcesso()));
            }

            if (StringUtils.isNotBlank(filter.getDeployId())) {
                jpaQuery.where(qProcessoCaseInstance.deployId.eq(filter.getDeployId()));
            }
        }

        return jpaQuery;
    }

   public Optional<ProcessoCaseInstance> findProcessoCaseInstanceAtivo(Processo processo, FluxoTrabalho fluxoTrabalho) {
        final Query<ProcessoCaseInstance> query = Query.<ProcessoCaseInstance>builder().filter(new ProcessoCaseInstanceFilter(processo, null, fluxoTrabalho.getDeployId())).build();
        final JPAQuery<ProcessoCaseInstance> jpaQuery = createQuery(query);
        List<ProcessoCaseInstance> processoCaseInstances = jpaQuery.fetch();
        if (CollectionUtils.isNotEmpty(processoCaseInstances)) {
            Collections.sort(processoCaseInstances, new Comparator<ProcessoCaseInstance>() {
                @Override
                public int compare(ProcessoCaseInstance o1, ProcessoCaseInstance o2) {
                    return Integer.valueOf(o2.getCaseInstanceId()) - Integer.valueOf(o1.getCaseInstanceId());
                }
            });
        }
        return Optional.ofNullable(CollectionUtils.isNotEmpty(processoCaseInstances) ? processoCaseInstances.get(0) : null);
    }
}
