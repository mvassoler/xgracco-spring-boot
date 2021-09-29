package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.HonorarioFilter;
import br.com.finchsolucoes.xgracco.domain.repository.HonorarioJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementação JPA do repositório Honorario.
 *
 * @author Jordano
 * @since 2.1
 */
@Repository
public class HonorarioRepositoryImpl extends AbstractJpaRepository<Honorario, Long> implements HonorarioJpaRepository {
    @Override
    public List<Honorario> find(Query<Honorario> query) {
        final PathBuilder<Honorario> path = new PathBuilder<>(Honorario.class, "honorario");
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
    public long count(Query<Honorario> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<Honorario> createQuery(Query<Honorario> query) {
        QHonorario qHonorario = QHonorario.honorario;

        final JPAQuery<Honorario> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qHonorario)
                .from(qHonorario);

        // filter
        if (query.getFilter() != null && query.getFilter() instanceof HonorarioFilter) {
            final HonorarioFilter honorarioFilter = (HonorarioFilter) query.getFilter();

            // descricao
            if (StringUtils.isNotEmpty(honorarioFilter.getDescricao())) {
                jpaQuery.where(qHonorario.descricao.likeIgnoreCase("%" + honorarioFilter.getDescricao() + "%"));
            }

            // processo
            if (Objects.nonNull(honorarioFilter.getProcesso() )) {
                jpaQuery.where(qHonorario.processo.eq(honorarioFilter.getProcesso()));
            }

            // responsável
            if (honorarioFilter.getResponsavel() != null) {
                jpaQuery.where(qHonorario.responsavel.eq(honorarioFilter.getResponsavel()));
            }

        }

        return jpaQuery;
    }

    @Override
    public List<Honorario> findHonorarios(Pessoa cliente, Carteira carteira) {
        QHonorario qHonorario = QHonorario.honorario;
        QProcesso qProcesso = QProcesso.processo1;

        return new JPAQueryFactory(entityManager)
                .select(
                        QHonorario.create(
                                qHonorario.id,
                                qHonorario.descricao,
                                qHonorario.valor,
                                QProcesso.create(
                                        qProcesso.id
                                ),
                                qHonorario.dataLancamento
                        )
                )
                .from(qHonorario)
                .join(qHonorario.processo, qProcesso)
                .where(qProcesso.cliente.eq(cliente))
                .where(qProcesso.carteira.eq(carteira))
                .fetch();
    }
}
