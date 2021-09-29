package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.CaixaLoteDespesasFilter;
import br.com.finchsolucoes.xgracco.domain.repository.CaixaLoteDespesasJpaRepository;
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
public class CaixaLoteDespesasRepositoryImpl extends AbstractJpaRepository<CaixaLoteDespesas, Long> implements CaixaLoteDespesasJpaRepository {

    @Override
    public List<CaixaLoteDespesas> find(Query<CaixaLoteDespesas> query) {
        final PathBuilder<CaixaLoteDespesas> path = new PathBuilder<>(CaixaLoteDespesas.class, "loteCustas");
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
    public long count(Query<CaixaLoteDespesas> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<CaixaLoteDespesas> createQuery(Query<CaixaLoteDespesas> query) {
        QCaixaLoteDespesas qCaixaLoteDespesas = QCaixaLoteDespesas.caixaLoteDespesas;

        final JPAQuery<CaixaLoteDespesas> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qCaixaLoteDespesas)
                .from(qCaixaLoteDespesas);

        // filter
        if (query.getFilter() != null && query.getFilter() instanceof CaixaLoteDespesasFilter) {
            final CaixaLoteDespesasFilter caixaLoteDespesasFilter = (CaixaLoteDespesasFilter) query.getFilter();

            if (Objects.nonNull(caixaLoteDespesasFilter.getCaixaLoteCustas())) {
                jpaQuery.where(qCaixaLoteDespesas.caixaLoteCustas.eq(caixaLoteDespesasFilter.getCaixaLoteCustas()));
            }
        }

        return null;
    }

    @Override
    public List<CaixaLoteDespesas> findByCaixa(CaixaLoteCustas caixaLoteCustas) {
        QCaixaLoteDespesas qCaixaLoteDespesas = QCaixaLoteDespesas.caixaLoteDespesas;
        QProcessoDespesas qProcessoDespesas = QProcessoDespesas.processoDespesas;
        QProcessoDespesaTipo qProcessoDespesaTipo = QProcessoDespesaTipo.processoDespesaTipo;
        QProcesso qProcesso = QProcesso.processo1;

        return new JPAQueryFactory(entityManager)
                .select(
                        QCaixaLoteDespesas.create(
                                qCaixaLoteDespesas.id,
                                QProcessoDespesas.create(
                                        qProcessoDespesas.id,
                                        qProcessoDespesas.dataLancamento,
                                        qProcessoDespesas.valor,
                                        qProcessoDespesas.statusPagamento,
                                        QProcesso.create(
                                                qProcesso.id,
                                                qProcesso.numero,
                                                qProcesso.controleCliente
                                        ),
                                        QProcessoDespesaTipo.create(
                                                qProcessoDespesaTipo.id,
                                                qProcessoDespesaTipo.descricao,
                                                qProcessoDespesaTipo.reembolsavel
                                        )
                                )
                        )
                )
                .from(qCaixaLoteDespesas)
                .join(qCaixaLoteDespesas.processoDespesas, qProcessoDespesas)
                .join(qProcessoDespesas.processoDispesaTipo, qProcessoDespesaTipo)
                .join(qProcessoDespesas.processos, qProcesso)
                .where(qCaixaLoteDespesas.caixaLoteCustas.eq(caixaLoteCustas))
                .fetch();
    }
}
