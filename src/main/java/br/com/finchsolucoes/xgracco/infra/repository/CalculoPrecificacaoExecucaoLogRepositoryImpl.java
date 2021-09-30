package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.CalculoPrecificacaoExecucaoLog;
import br.com.finchsolucoes.xgracco.domain.entity.QCalculoPrecificacao;
import br.com.finchsolucoes.xgracco.domain.entity.QCalculoPrecificacaoExecucaoLog;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.CalculoPrecificacaoLogsFilter;
import br.com.finchsolucoes.xgracco.domain.repository.CalculoPrecificacaoExecucaoLogJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class CalculoPrecificacaoExecucaoLogRepositoryImpl extends AbstractJpaRepository<CalculoPrecificacaoExecucaoLog, Long> implements CalculoPrecificacaoExecucaoLogJpaRepository {

    @Override
    public List<CalculoPrecificacaoExecucaoLog>  find(Query<CalculoPrecificacaoExecucaoLog> query) {
        final PathBuilder<CalculoPrecificacaoExecucaoLog>  path = new PathBuilder<>(CalculoPrecificacaoExecucaoLog.class, "calculoPrecificacaoExecucaoLog");
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
        List<CalculoPrecificacaoExecucaoLog>  list = jpaQuery.fetch();
        return list;
    }

    @Override
    public long count(Query<CalculoPrecificacaoExecucaoLog> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<CalculoPrecificacaoExecucaoLog>  createQuery(Query<CalculoPrecificacaoExecucaoLog>  query) {
        final QCalculoPrecificacaoExecucaoLog qCalculoPrecificacaoExecucaoLog = QCalculoPrecificacaoExecucaoLog.calculoPrecificacaoExecucaoLog;

        final JPAQuery<CalculoPrecificacaoExecucaoLog>  jpaQuery = new JPAQueryFactory(entityManager)
                .select(qCalculoPrecificacaoExecucaoLog)
                .from(qCalculoPrecificacaoExecucaoLog);

        // filter
        if (query.getFilter() != null && query.getFilter() instanceof CalculoPrecificacaoLogsFilter) {
            final CalculoPrecificacaoLogsFilter calculoPrecificacaoLogFilter = (CalculoPrecificacaoLogsFilter) query.getFilter();

            //calculo
            if(Objects.nonNull(calculoPrecificacaoLogFilter.getCalculoPrecificacao())){
                QCalculoPrecificacao qCalculoPrecificacao = QCalculoPrecificacao.calculoPrecificacao;
                jpaQuery.join(qCalculoPrecificacaoExecucaoLog.calculoPrecificacao, qCalculoPrecificacao).fetchJoin()
                        .where(qCalculoPrecificacao.id.eq(calculoPrecificacaoLogFilter.getCalculoPrecificacao().getId()));
            }

            //processoatualizado
            if(Objects.nonNull(calculoPrecificacaoLogFilter.getProcessoAtualizado())){
                jpaQuery.where(qCalculoPrecificacaoExecucaoLog.processoAtualizado.eq(calculoPrecificacaoLogFilter.getProcessoAtualizado()));
            }

            //processounico
            if(StringUtils.isNotBlank(calculoPrecificacaoLogFilter.getProcessoUnico())) {
                jpaQuery.where(qCalculoPrecificacaoExecucaoLog.processoUnico.eq(calculoPrecificacaoLogFilter.getProcessoUnico()));
            }

        }

        return jpaQuery;
    }

    @Override
    public Optional<CalculoPrecificacaoExecucaoLog> findByIdAndIdCalculo(Long idLog, Long idCalculo) {
        final QCalculoPrecificacaoExecucaoLog qCalculoPrecificacaoExecucaoLog = QCalculoPrecificacaoExecucaoLog.calculoPrecificacaoExecucaoLog;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                            .select(qCalculoPrecificacaoExecucaoLog)
                            .from(qCalculoPrecificacaoExecucaoLog)
                            .where(qCalculoPrecificacaoExecucaoLog.calculoPrecificacao.id.eq(idCalculo))
                            .where(qCalculoPrecificacaoExecucaoLog.id.eq(idLog)).fetchOne());
    }
}
