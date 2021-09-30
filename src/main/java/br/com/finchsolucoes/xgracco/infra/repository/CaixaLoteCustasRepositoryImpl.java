package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.CaixaLoteCustas;
import br.com.finchsolucoes.xgracco.domain.entity.LoteCustas;
import br.com.finchsolucoes.xgracco.domain.entity.QCaixaLoteCustas;
import br.com.finchsolucoes.xgracco.domain.entity.QLoteCustas;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.domain.query.impl.CaixaLoteCustasFilter;
import br.com.finchsolucoes.xgracco.domain.repository.CaixaLoteCustasJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade LoteCustas.
 *
 * @author Raphael Moreira
 * @since 4.0.4
 */
@Repository
public class CaixaLoteCustasRepositoryImpl extends AbstractJpaRepository<CaixaLoteCustas, Long> implements CaixaLoteCustasJpaRepository {

    @Override
    public List<CaixaLoteCustas> find(Query<CaixaLoteCustas> query) {
        final PathBuilder<CaixaLoteCustas> path = new PathBuilder<>(CaixaLoteCustas.class, "loteCustas");
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
    public long count(Query<CaixaLoteCustas> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<CaixaLoteCustas> createQuery(Query<CaixaLoteCustas> query) {
        QCaixaLoteCustas qCaixaLoteCustas = QCaixaLoteCustas.caixaLoteCustas;

        final JPAQuery<CaixaLoteCustas> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qCaixaLoteCustas)
                .from(qCaixaLoteCustas);

        // filter
        if (query.getFilter() != null && query.getFilter() instanceof CaixaLoteCustasFilter) {
            final CaixaLoteCustasFilter caixaLoteCustasFilter = (CaixaLoteCustasFilter) query.getFilter();

            if (Objects.nonNull(caixaLoteCustasFilter.getLoteCustas())) {
                jpaQuery.where(qCaixaLoteCustas.loteCustas.eq(caixaLoteCustasFilter.getLoteCustas()));
            }
        }

        return jpaQuery;
    }


    @Override
    public List<CaixaLoteCustas> findByLote(LoteCustas loteCustas, String sortProperty, Sorter.Direction sortDirection) {
        final PathBuilder<CaixaLoteCustas> pathBuilder = new PathBuilder<>(CaixaLoteCustas.class, "caixaLoteCustas");
        QCaixaLoteCustas qCaixaLoteCustas = QCaixaLoteCustas.caixaLoteCustas;

        JPAQuery<CaixaLoteCustas> query = new JPAQueryFactory(entityManager)
                .select(
                        QCaixaLoteCustas.create(
                                qCaixaLoteCustas.id,
                                qCaixaLoteCustas.valor,
                                qCaixaLoteCustas.descricao,
                                qCaixaLoteCustas.dataLancamento
                        )
                )
                .from(qCaixaLoteCustas)
                .where(qCaixaLoteCustas.loteCustas.eq(loteCustas));

        if (Objects.nonNull(sortDirection) && StringUtils.isNotEmpty(sortProperty)) {
            if (sortDirection.equals(Sorter.Direction.ASC)) {
                query.orderBy(pathBuilder.getString(sortProperty).asc());
            } else {
                query.orderBy(pathBuilder.getString(sortProperty).desc());
            }
        } else {
            query.orderBy(qCaixaLoteCustas.id.desc());
        }

        return query.fetch();
    }

    @Override
    public Optional<CaixaLoteCustas> findByIdCaixa(Long idCaixa) {
        QCaixaLoteCustas qCaixaLoteCustas = QCaixaLoteCustas.caixaLoteCustas;
        QLoteCustas qLoteCustas = QLoteCustas.loteCustas;

        return Optional.ofNullable(
                new JPAQueryFactory(entityManager)
                    .select(
                            QCaixaLoteCustas.create(
                                    qCaixaLoteCustas.id,
                                    qCaixaLoteCustas.valor,
                                    qCaixaLoteCustas.descricao,
                                    qCaixaLoteCustas.dataLancamento,
                                    QLoteCustas.create(
                                        qLoteCustas.id,
                                        qLoteCustas.numeroLote,
                                        qLoteCustas.cliente
                                    )
                            )
                    )
                    .from(qCaixaLoteCustas)
                    .join(qCaixaLoteCustas.loteCustas, qLoteCustas)
                    .where(qCaixaLoteCustas.id.eq(idCaixa))
                    .fetchOne()
        );
    }
}
