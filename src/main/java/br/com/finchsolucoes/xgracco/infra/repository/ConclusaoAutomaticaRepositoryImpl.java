package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.ConclusaoAutomaticaFilter;
import br.com.finchsolucoes.xgracco.domain.repository.ConclusaoAutomaticaJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author thiago.fogar
 */
@Repository
public class ConclusaoAutomaticaRepositoryImpl extends AbstractJpaRepository<ConclusaoAutomatica, Long> implements ConclusaoAutomaticaJpaRepository {


    @Override
    public List<ConclusaoAutomatica> find(Query<ConclusaoAutomatica> query) {
        final PathBuilder<ConclusaoAutomatica> path = new PathBuilder<>(ConclusaoAutomatica.class, "conclusaoAutomatica");
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

    @Override
    public long count(Query<ConclusaoAutomatica> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<ConclusaoAutomatica> createQuery(Query<ConclusaoAutomatica> query) {

        final QConclusaoAutomatica qConclusaoAutomatica = QConclusaoAutomatica.conclusaoAutomatica;
        final QFluxoTrabalho qFluxoTrabalho = QFluxoTrabalho.fluxoTrabalho;
        final QTarefa qTarefa = QTarefa.tarefa;
        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;
        final QTarefaStatusFinal qTarefaStatusFinal = QTarefaStatusFinal.tarefaStatusFinal;
        final QStatusFinal qStatusFinal = QStatusFinal.statusFinal;

        final JPAQuery<ConclusaoAutomatica> jpaQuery = new JPAQueryFactory(entityManager)
                .select(QConclusaoAutomatica.create(
                        qConclusaoAutomatica.id,
                        QFluxoTrabalhoTarefa.create(
                                qFluxoTrabalho.id,
                                QTarefa.create(
                                        qTarefa.id,
                                        qTarefa.nome
                                )
                        ),
                        QStatusFinal.create(
                                qStatusFinal.id,
                                qStatusFinal.descricao
                        ),
                        QFluxoTrabalho.create(
                                qFluxoTrabalho.id,
                                qFluxoTrabalho.descricao
                        )
                ))
                .from(qConclusaoAutomatica)
                .join(qConclusaoAutomatica.fluxoTrabalho, qFluxoTrabalho)
                .join(qConclusaoAutomatica.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefa)
                .join(qFluxoTrabalhoTarefa.tarefa, qTarefa)
                .join(qConclusaoAutomatica.statusFinal, qStatusFinal);

        // filter
        if (query.getFilter() != null && query.getFilter() instanceof ConclusaoAutomaticaFilter) {
            final ConclusaoAutomaticaFilter conclusaoAutomaticaFilter = (ConclusaoAutomaticaFilter) query.getFilter();

            if (conclusaoAutomaticaFilter.getFluxoTrabalho() != null) {
                jpaQuery.where(qConclusaoAutomatica.fluxoTrabalho.eq(conclusaoAutomaticaFilter.getFluxoTrabalho()));
            }

            if (conclusaoAutomaticaFilter.getFluxoTrabalhoTarefa() != null) {
                jpaQuery.where(qConclusaoAutomatica.fluxoTrabalhoTarefa.eq(conclusaoAutomaticaFilter.getFluxoTrabalhoTarefa()));
            }
        }
        return jpaQuery;
    }

    @Override
    public Optional<ConclusaoAutomatica> findByFluxoTrabalhoAndTarefa(FluxoTrabalho fluxoTrabalho, FluxoTrabalhoTarefa fluxoTrabalhoTarefa, StatusFinal statusFinal) {
        final QConclusaoAutomatica qConclusaoAutomatica = QConclusaoAutomatica.conclusaoAutomatica;
        final QConclusaoAutomaticaTarefa qConclusaoAutomaticaTarefa = QConclusaoAutomaticaTarefa.conclusaoAutomaticaTarefa;

        return Optional.ofNullable(
                new JPAQueryFactory(entityManager)
                        .select(qConclusaoAutomatica)
                        .from(qConclusaoAutomatica)
                        .join(qConclusaoAutomatica.conclusaoAutomaticaTarefas, qConclusaoAutomaticaTarefa).fetchJoin()
                        .where(qConclusaoAutomatica.fluxoTrabalho.eq(fluxoTrabalho))
                        .where(qConclusaoAutomatica.fluxoTrabalhoTarefa.eq(fluxoTrabalhoTarefa))
                        .where(qConclusaoAutomatica.statusFinal.eq(statusFinal))
                        .fetchOne()
        );
    }
}
