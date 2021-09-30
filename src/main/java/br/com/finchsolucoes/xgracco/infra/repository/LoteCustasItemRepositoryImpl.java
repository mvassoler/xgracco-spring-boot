package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.domain.query.impl.LoteCustasItemFilter;
import br.com.finchsolucoes.xgracco.domain.repository.LoteCustasItemJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
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
public class LoteCustasItemRepositoryImpl extends AbstractJpaRepository<LoteCustasItem, Long> implements LoteCustasItemJpaRepository {
    @Override
    public List<LoteCustasItem> find(Query<LoteCustasItem> query) {
        final PathBuilder<LoteCustasItem> path = new PathBuilder<>(LoteCustasItem.class, "loteCustasItem");
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
    public long count(Query<LoteCustasItem> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<LoteCustasItem> createQuery(Query<LoteCustasItem> query) {
        QLoteCustasItem qLoteCustasItem = QLoteCustasItem.loteCustasItem;

        final JPAQuery<LoteCustasItem> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qLoteCustasItem)
                .from(qLoteCustasItem);

        // filter
        if (query.getFilter() != null && query.getFilter() instanceof LoteCustasItemFilter) {
            final LoteCustasItemFilter loteCustasFilter = (LoteCustasItemFilter) query.getFilter();

            if (Objects.nonNull(loteCustasFilter.getLoteCustas())) {
                jpaQuery.where(qLoteCustasItem.loteCustas.eq(loteCustasFilter.getLoteCustas()));
            }
        }

        return jpaQuery;
    }

    @Override
    public List<ProcessoDespesas> findByLote(LoteCustas loteCustas, Boolean semCaixa, String sortProperty, Sorter.Direction sortDirection, Long page) {
        final PathBuilder<ProcessoDespesas> pathBuilder = new PathBuilder<>(ProcessoDespesas.class, "processoDespesas");
        QProcessoDespesas qProcessoDespesas = QProcessoDespesas.processoDespesas;
        QProcesso qProcesso = QProcesso.processo1;
        QLoteCustasItem qLoteCustasItem = QLoteCustasItem.loteCustasItem;
        QCaixaLoteDespesas qCaixaLoteDespesas = QCaixaLoteDespesas.caixaLoteDespesas;

        JPAQuery<ProcessoDespesas> query = new JPAQueryFactory(entityManager)
                .select(
                        QProcessoDespesas.create(
                                qLoteCustasItem.id,
                            qProcessoDespesas.dataLancamento,
                            qProcessoDespesas.valor,
                            qProcessoDespesas.statusPagamento,
                            QProcesso.create(
                                    qProcesso.id,
                                    qProcesso.numero
                            )
                    )
                )
                .from(qProcessoDespesas)
                .join(qProcessoDespesas.processos, qProcesso)
                .innerJoin(qLoteCustasItem).on(qLoteCustasItem.processoDespesas.id.eq(qProcessoDespesas.id))
                .where(qLoteCustasItem.loteCustas.eq(loteCustas));

        if (semCaixa) {
            query.where(qProcessoDespesas.notIn(
                    JPAExpressions
                        .select(
                                qCaixaLoteDespesas.processoDespesas
                        )
                        .from(qCaixaLoteDespesas)
                        .fetchAll()
                    )
            );
        }

        if (sortDirection.equals(Sorter.Direction.ASC)) {
            query.orderBy(pathBuilder.getString(sortProperty).asc());
        }
        else {
            query.orderBy(pathBuilder.getString(sortProperty).desc());
        }

        return query.fetch();
    }

    @Override
    public List<ProcessoDespesas> findByIdLista(List<LoteCustasItem> loteCustasItemList) {
        QProcessoDespesas qProcessoDespesas = QProcessoDespesas.processoDespesas;
        QProcesso qProcesso = QProcesso.processo1;
        QLoteCustasItem qLoteCustasItem = QLoteCustasItem.loteCustasItem;

        return new JPAQueryFactory(entityManager)
                .select(
                        QProcessoDespesas.create(
                                qProcessoDespesas.id,
                                qProcessoDespesas.dataLancamento,
                                qProcessoDespesas.valor,
                                qProcessoDespesas.statusPagamento,
                                QProcesso.create(
                                        qProcesso.id,
                                        qProcesso.numero
                                )
                        )
                )
                .from(qProcessoDespesas)
                .join(qProcessoDespesas.processos, qProcesso)
                .innerJoin(qLoteCustasItem).on(qLoteCustasItem.processoDespesas.id.eq(qProcessoDespesas.id))
                .where(qLoteCustasItem.in(loteCustasItemList))
                .fetch();
    }

    @Override
    public List<ProcessoDespesas> findByCaixa(CaixaLoteCustas caixaLoteCustas, String sortProperty, Sorter.Direction sortDirection, Long page) {
        final PathBuilder<ProcessoDespesas> pathBuilder = new PathBuilder<>(ProcessoDespesas.class, "processoDespesas");
        QProcessoDespesas qProcessoDespesas = QProcessoDespesas.processoDespesas;
        QProcesso qProcesso = QProcesso.processo1;
        QLoteCustasItem qLoteCustasItem = QLoteCustasItem.loteCustasItem;
        QCaixaLoteDespesas qCaixaLoteDespesas = QCaixaLoteDespesas.caixaLoteDespesas;

        JPAQuery<ProcessoDespesas> query = new JPAQueryFactory(entityManager)
                .select(
                        QProcessoDespesas.create(
                                qCaixaLoteDespesas.id,
                                qProcessoDespesas.dataLancamento,
                                qProcessoDespesas.valor,
                                qProcessoDespesas.statusPagamento,
                                QProcesso.create(
                                        qProcesso.id,
                                        qProcesso.numero
                                )
                        )
                )
                .from(qProcessoDespesas)
                .join(qProcessoDespesas.processos, qProcesso)
                .innerJoin(qLoteCustasItem).on(qLoteCustasItem.processoDespesas.id.eq(qProcessoDespesas.id))
                .innerJoin(qCaixaLoteDespesas).on(qCaixaLoteDespesas.processoDespesas.id.eq(qProcessoDespesas.id))
                .where(qCaixaLoteDespesas.caixaLoteCustas.eq(caixaLoteCustas));
                /*.where(qProcessoDespesas.in(
                    JPAExpressions
                            .select(
                                    qCaixaLoteDespesas.processoDespesas
                            )
                            .from(qCaixaLoteDespesas)
                            .where(qCaixaLoteDespesas.caixaLoteCustas.eq(caixaLoteCustas))
                            .fetchAll()
                    )
                );*/

        if (sortDirection.equals(Sorter.Direction.ASC)) {
            query.orderBy(pathBuilder.getString(sortProperty).asc());
        }
        else {
            query.orderBy(pathBuilder.getString(sortProperty).desc());
        }

        return query.fetch();
    }

    @Override
    public LoteCustasItem findByDespesa(ProcessoDespesas processoDespesas) {
        QLoteCustasItem qLoteCustasItem = QLoteCustasItem.loteCustasItem;

        return new JPAQueryFactory(entityManager)
                .select(
                        QLoteCustasItem.create(
                                qLoteCustasItem.id
                        )
                )
                .from(qLoteCustasItem)
                .where(qLoteCustasItem.processoDespesas.in(processoDespesas))
                .fetchOne();
    }
}
