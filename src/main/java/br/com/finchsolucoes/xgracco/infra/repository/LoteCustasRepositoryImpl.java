package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.enums.EnumStatusLoteCustas;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.domain.query.impl.LoteCustasFilter;
import br.com.finchsolucoes.xgracco.domain.repository.LoteCustasJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
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
public class LoteCustasRepositoryImpl extends AbstractJpaRepository<LoteCustas, Long> implements LoteCustasJpaRepository {
    @Override
    public List<LoteCustas> find(Query<LoteCustas> query) {
        final PathBuilder<LoteCustas> path = new PathBuilder<>(LoteCustas.class, "loteCustas");
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
    public long count(Query<LoteCustas> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<LoteCustas> createQuery(Query<LoteCustas> query) {
        QLoteCustas qLoteCustas = QLoteCustas.loteCustas;

        final JPAQuery<LoteCustas> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qLoteCustas)
                .from(qLoteCustas);

        // filter
        if (query.getFilter() != null && query.getFilter() instanceof LoteCustasFilter) {
            final LoteCustasFilter loteCustasFilter = (LoteCustasFilter) query.getFilter();

            if (Objects.nonNull(loteCustasFilter.getCliente())) {
                jpaQuery.where(qLoteCustas.cliente.eq(loteCustasFilter.getCliente()));
            }

            if (Objects.nonNull(loteCustasFilter.getDataLoteInicio())) {
                jpaQuery.where(qLoteCustas.dataLote.goe(loteCustasFilter.getDataLoteInicio()));
            }

            if (Objects.nonNull(loteCustasFilter.getDataLoteFim())) {
                jpaQuery.where(qLoteCustas.dataLote.loe(loteCustasFilter.getDataLoteFim()));
            }

            if (Objects.nonNull(loteCustasFilter.getStatusLoteCustas())) {
                jpaQuery.where(qLoteCustas.statusLote.eq(loteCustasFilter.getStatusLoteCustas()));
            }

            if (Objects.nonNull(loteCustasFilter.getNumeroLote())) {
                if (StringUtils.isNotEmpty(loteCustasFilter.getNumeroLote())) {
                    jpaQuery.where(qLoteCustas.numeroLote.like("%" + loteCustasFilter.getNumeroLote() + "%"));
                }
            }
        }

        return jpaQuery;
    }

    @Override
    public List<LoteCustas> findByPeriodoCliente(String numeroLote, Integer statusLote, Calendar dataInicio, Calendar dataFim, Pessoa cliente, String sortProperty, Sorter.Direction sortDirection) {
        final PathBuilder<LoteCustas> pathBuilder = new PathBuilder<>(LoteCustas.class, "loteCustas");
        QLoteCustas qLoteCustas = QLoteCustas.loteCustas;
        QLoteCustasItem qLoteCustasItem = QLoteCustasItem.loteCustasItem;
        QCaixaLoteCustas qCaixaLoteCustas = QCaixaLoteCustas.caixaLoteCustas;

        JPAQuery<LoteCustas> query = new JPAQueryFactory(entityManager)
                .select(
                        QLoteCustas.create(
                                qLoteCustas.id,
                                qLoteCustas.numeroLote,
                                qLoteCustas.dataLote,
                                qLoteCustas.statusLote,
                                qLoteCustas.cliente,
                                JPAExpressions
                                        .select(
                                                qLoteCustasItem.processoDespesas.valor.sum().doubleValue().coalesce(new Double(0))
                                        )
                                        .from(qLoteCustasItem)
                                        .where(qLoteCustasItem.loteCustas.eq(qLoteCustas)),
                                JPAExpressions
                                        .select(
                                                qCaixaLoteCustas.valor.sum().doubleValue().coalesce(new Double(0))
                                        )
                                        .from(qCaixaLoteCustas)
                                        .where(qCaixaLoteCustas.loteCustas.eq(qLoteCustas))
                        )
                )
                .from(qLoteCustas);

        if (Objects.nonNull(numeroLote)) {
            if (StringUtils.isNotEmpty(numeroLote)) {
                query.where(qLoteCustas.numeroLote.like("%" + numeroLote + "%"));
            }
        }

        if (Objects.nonNull(dataInicio)) {
            query.where(qLoteCustas.dataLote.goe(dataInicio));
        }

        if (Objects.nonNull(dataFim)) {
            query.where(qLoteCustas.dataLote.loe(dataFim));
        }

        if (Objects.nonNull(cliente)) {
            query.where(qLoteCustas.cliente.eq(cliente));
        }
        else {
            query.where(qLoteCustas.cliente.eq(new Pessoa(0L)));
        }

        if (Objects.nonNull(statusLote)) {
            if (statusLote > 0) {
                query.where(qLoteCustas.statusLote.eq(EnumStatusLoteCustas.getById(statusLote)));
            }
        }

        if (Objects.nonNull(sortDirection)) {
            if (sortDirection.equals(Sorter.Direction.ASC)) {
                query.orderBy(pathBuilder.getString(sortProperty).asc());
            } else {
                query.orderBy(pathBuilder.getString(sortProperty).desc());
            }
        } else {
            if (Objects.nonNull(sortProperty)) {
                query.orderBy(pathBuilder.getString(sortProperty).desc());
            }
        }

        return query.fetch();
    }
}
