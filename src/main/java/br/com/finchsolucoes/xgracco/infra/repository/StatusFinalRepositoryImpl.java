package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.enums.EnumStatusTarefa;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.StatusFinalFilter;
import br.com.finchsolucoes.xgracco.domain.repository.StatusFinalJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade StatusFinal.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
@Repository
public class StatusFinalRepositoryImpl extends AbstractJpaRepository<StatusFinal, Long> implements StatusFinalJpaRepository {


    public List<StatusFinal> find(Query<StatusFinal> query) {
        final PathBuilder<StatusFinal> path = new PathBuilder<>(StatusFinal.class, "statusFinal");
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


    public long count(Query<StatusFinal> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<StatusFinal> createQuery(Query<StatusFinal> query) {
        QStatusFinal qStatusFinal = QStatusFinal.statusFinal;
        QFormulario qFormulario = QFormulario.formulario;

        final JPAQuery<StatusFinal> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qStatusFinal)
                .from(qStatusFinal)
                .leftJoin(qStatusFinal.formulario, qFormulario).fetchJoin();

        // filter
        if (query.getFilter() != null && query.getFilter() instanceof StatusFinalFilter) {
            final StatusFinalFilter statusFinalFilter = (StatusFinalFilter) query.getFilter();

            // descricao
            if (StringUtils.isNotEmpty(statusFinalFilter.getDescricao())) {
                jpaQuery.where(qStatusFinal.descricao.likeIgnoreCase("%" + statusFinalFilter.getDescricao() + "%"));
            }
        }

        return jpaQuery;
    }


    public Optional<StatusFinal> findByTarefaStatusFinal(TarefaStatusFinal tarefaStatusFinal) {
        QTarefaStatusFinal qTarefaStatusFinal = QTarefaStatusFinal.tarefaStatusFinal;
        QStatusFinal qStatusFinal = QStatusFinal.statusFinal;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(qStatusFinal)
                .from(qTarefaStatusFinal)
                .join(qTarefaStatusFinal.statusFinal, qStatusFinal)
                .where(qTarefaStatusFinal.eq(tarefaStatusFinal))
                .fetchOne());
    }


    public Optional<StatusFinal> findByDescricao(String descricao) {
        QStatusFinal qStatusFinal = QStatusFinal.statusFinal;

        return Optional.ofNullable(
                new JPAQueryFactory(entityManager)
                        .select(qStatusFinal)
                        .from(qStatusFinal)
                        .where(qStatusFinal.descricao.eq(descricao))
                        .fetchOne()
        );
    }


    public List<TarefaStatusFinal> findByDadosBasicosTarefa(DadosBasicosTarefa dadosBasicosTarefa, EnumStatusTarefa statusTarefa) {
        final QTarefaStatusFinal qTarefaStatusFinal = QTarefaStatusFinal.tarefaStatusFinal;
        final QTarefa qTarefa = QTarefa.tarefa;
        final QDadosBasicosTarefa qDadosBasicosTarefa = QDadosBasicosTarefa.dadosBasicosTarefa;
        final QStatusFinal qStatusFinal = QStatusFinal.statusFinal;
        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;

        JPAQuery<TarefaStatusFinal> query = new JPAQueryFactory(entityManager)
                .select(QTarefaStatusFinal.create(
                        qTarefaStatusFinal.id,
                        QFluxoTrabalhoTarefa.create(
                                qFluxoTrabalhoTarefa.id,

                                QTarefa.create(
                                        qTarefa.id,
                                        qTarefa.nome
                                )
                        ),
                        QStatusFinal.create(
                                qStatusFinal.id,
                                qStatusFinal.descricao
                        ),
                        qTarefaStatusFinal.status,
                        QFormulario.create(
                                qTarefaStatusFinal.formulario.id
                        )
                ))
                .from(qDadosBasicosTarefa)
                .join(qDadosBasicosTarefa.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefa)
                .join(qFluxoTrabalhoTarefa.tarefa, qTarefa)
                .join(qFluxoTrabalhoTarefa.statusFinais, qTarefaStatusFinal)
                .join(qTarefaStatusFinal.statusFinal, qStatusFinal)
                .where(qDadosBasicosTarefa.eq(dadosBasicosTarefa));

        if (statusTarefa != null) {
            query.where(qTarefaStatusFinal.status.eq(statusTarefa));
        }

        return query.fetch();
    }


    public List<StatusFinal> findByFluxoTrabalhoTarefa(FluxoTrabalhoTarefa fluxoTrabalhoTarefa, Integer statusTarefa) {
        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;
        final QTarefaStatusFinal qTarefaStatusFinal = QTarefaStatusFinal.tarefaStatusFinal;
        final QStatusFinal qStatusFinal = QStatusFinal.statusFinal;

        JPAQuery<StatusFinal> query = new JPAQueryFactory(entityManager)
                .select(qStatusFinal)
                .from(qTarefaStatusFinal)
                .join(qTarefaStatusFinal.statusFinal, qStatusFinal)
                .join(qTarefaStatusFinal.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefa)
                .where(qFluxoTrabalhoTarefa.eq(fluxoTrabalhoTarefa));

        if (statusTarefa != null) {
            query.where(qTarefaStatusFinal.status.eq(EnumStatusTarefa.getById(statusTarefa)));
        }

        return query.fetch();
    }
}
