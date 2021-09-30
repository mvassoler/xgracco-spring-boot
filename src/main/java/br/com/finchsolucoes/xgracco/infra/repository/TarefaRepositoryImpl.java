package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.TarefaFilter;
import br.com.finchsolucoes.xgracco.domain.repository.TarefaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade Tarefa.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
@Repository
public class TarefaRepositoryImpl extends AbstractJpaRepository<Tarefa,Long> implements TarefaRepository {


    @Override
    public List<FluxoTrabalhoTarefa> findByFluxoTrabalho(FluxoTrabalho fluxoTrabalho, Query<Tarefa> query) {
        return createQuery(fluxoTrabalho, query, true).fetch();
    }

    @Override
    public List<FluxoTrabalhoTarefa> findByFluxoTrabalhoNoLimit(FluxoTrabalho fluxoTrabalho, Query<Tarefa> query) {
        return createQuery(fluxoTrabalho, query, false).fetch();
    }

    @Override
    public long countByFluxoTrabalho(FluxoTrabalho fluxoTrabalho, Query<Tarefa> query) {
        return createQuery(fluxoTrabalho, query, false).fetchCount();
    }

    @Override
    public void create(Tarefa tarefa) {
        entityManager.persist(tarefa);
        entityManager.flush();
    }

    @Override
    public void create(Collection<Tarefa> tarefas) {
        int i = 1;
        for (Tarefa tarefa : tarefas) {
            entityManager.persist(tarefa);

            if (i % 50 == 0) {
                entityManager.flush();
                entityManager.clear();
            }
            i++;
        }

        entityManager.flush();
        entityManager.clear();
    }

    @Override
    public void update(Tarefa tarefa) {
        entityManager.merge(tarefa);
        entityManager.flush();
    }

    @Override
    public void remove(Tarefa tarefa) {
        final QTarefa qTarefa = QTarefa.tarefa;

        new JPAUpdateClause(entityManager, qTarefa)
                .set(qTarefa.ativo, false)
                .where(qTarefa.eq(tarefa))
                .execute();

        entityManager.flush();
    }


    private JPAQuery<FluxoTrabalhoTarefa> createQuery(FluxoTrabalho fluxoTrabalho, Query<Tarefa> query, boolean sortAndPaging) {
        final QTarefa qTarefa = QTarefa.tarefa;
        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;

        final PathBuilder<Tarefa> path = new PathBuilder<>(Tarefa.class, "tarefa");

        final JPAQuery<FluxoTrabalhoTarefa> jpaQuery = new JPAQueryFactory(entityManager)
                .select(QFluxoTrabalhoTarefa.create(
                        qFluxoTrabalhoTarefa.id,
                        QTarefa.create(
                                qTarefa.id,
                                qTarefa.nome
                        )
                ))
                .from(qFluxoTrabalhoTarefa)
                .join(qFluxoTrabalhoTarefa.tarefa, qTarefa)
                .where(qFluxoTrabalhoTarefa.fluxoTrabalho.eq(fluxoTrabalho));

        // filter
        if (query.getFilter() != null && query.getFilter() instanceof TarefaFilter) {
            final TarefaFilter tarefaFilter = (TarefaFilter) query.getFilter();

            // ativo
            if (tarefaFilter.getAtivo() != null) {
                jpaQuery.where(qTarefa.ativo.eq(tarefaFilter.getAtivo()));
            }

            // nome
            if (StringUtils.isNotEmpty(tarefaFilter.getNome())) {
                jpaQuery.where(qTarefa.nome.likeIgnoreCase("%" + tarefaFilter.getNome() + "%"));
            }
        }

        if (sortAndPaging) {
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
            if (query.getLimit() != Query.NO_LIMIT) {
                jpaQuery.limit(query.getLimit());
            }
        }

        return jpaQuery;
    }

    private JPAQuery<Tarefa> createQuery(Query<Tarefa> query) {
        final QTarefa qTarefa = QTarefa.tarefa;
        final PathBuilder<Tarefa> path = new PathBuilder<>(Tarefa.class, "tarefa");

        final JPAQuery<Tarefa> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qTarefa)
                .from(qTarefa)
                .where(qTarefa.id.goe(5000));

        // filter
        if (query.getFilter() != null && query.getFilter() instanceof TarefaFilter) {
            final TarefaFilter tarefaFilter = (TarefaFilter) query.getFilter();

            // ativo
            if (tarefaFilter.getAtivo() != null) {
                jpaQuery.where(qTarefa.ativo.eq(tarefaFilter.getAtivo()));
            }

            // nome
            if (StringUtils.isNotEmpty(tarefaFilter.getNome())) {
                jpaQuery.where(qTarefa.nome.likeIgnoreCase("%" + tarefaFilter.getNome() + "%"));
            }

        }

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
        if (query.getLimit() != Query.NO_LIMIT) {
            jpaQuery.limit(query.getLimit());
        }

        return jpaQuery;
    }

    @Override
    public List<Tarefa> find(Query<Tarefa> query) {
        return createQuery(query).fetch();
    }

    @Override
    public long count(Query<Tarefa> query) {
        return createQuery(query).fetchCount();
    }

    @Override
    public Optional<Tarefa> findById(Long idTarefa) {
        QTarefa qTarefa = QTarefa.tarefa;
        return Optional.of(new JPAQueryFactory(entityManager)
                .select(qTarefa)
                .from(qTarefa)
                .where(qTarefa.id.eq(idTarefa))
                .fetchOne());
    }

    @Override
    public Long validarInativacaoTarefa(Tarefa tarefa) {
        QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;
        QTarefa qTarefa = QTarefa.tarefa;

        return new JPAQueryFactory(entityManager)
                .select(QFluxoTrabalhoTarefa.create(
                        qFluxoTrabalhoTarefa.id))
                .from(qFluxoTrabalhoTarefa)
                .join(qFluxoTrabalhoTarefa.tarefa, qTarefa)
                .where(qFluxoTrabalhoTarefa.ativo.eq(Boolean.TRUE))
                .where(qTarefa.eq(tarefa))
                .fetchCount();
    }

    @Override
    public Optional<Tarefa> findByFluxoTrabalhoTarefa(FluxoTrabalhoTarefa fluxoTrabalhoTarefa) {
        QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;
        return Optional.of(new JPAQueryFactory(entityManager)
                .select(qFluxoTrabalhoTarefa.tarefa)
                .from(qFluxoTrabalhoTarefa)
                .where(qFluxoTrabalhoTarefa.id.eq(fluxoTrabalhoTarefa.getId()))
                .fetchOne());
    }

    @Override
    public Optional<Tarefa> findByNome(String nome) {
        QTarefa qTarefa = QTarefa.tarefa;
        return Optional.of(new JPAQueryFactory(entityManager)
                .select(qTarefa)
                .from(qTarefa)
                .where(qTarefa.nome.eq(nome))
                .fetchOne());
    }
}
