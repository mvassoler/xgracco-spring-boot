package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.enums.EnumStatusPagamentoDespesas;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.domain.query.impl.ProcessoDespesasFilter;
import br.com.finchsolucoes.xgracco.domain.repository.ProcessoDespesasJpaRepository;
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
 * Implementação JPA do repositório da entidade ProcessoDespesas.
 *
 * @author Raphael Moreira
 * @since 4.0.4
 */
@Repository
public class ProcessoDespesasRepositoryImpl extends AbstractJpaRepository<ProcessoDespesas, Long> implements ProcessoDespesasJpaRepository {


    public List<ProcessoDespesas> find(Query<ProcessoDespesas> query) {
        final PathBuilder<ProcessoDespesas> path = new PathBuilder<>(ProcessoDespesas.class, "processoDespesas");
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


    public long count(Query<ProcessoDespesas> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<ProcessoDespesas> createQuery(Query<ProcessoDespesas> query) {
        QProcessoDespesas qProcessoDespesas = QProcessoDespesas.processoDespesas;

        final JPAQuery<ProcessoDespesas> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qProcessoDespesas)
                .from(qProcessoDespesas);

        // filter
        if (query.getFilter() != null && query.getFilter() instanceof ProcessoDespesasFilter) {
            final ProcessoDespesasFilter despesasFilter = (ProcessoDespesasFilter) query.getFilter();

            // dataInicio
            if (Objects.nonNull(despesasFilter.getDataInicio())) {
                jpaQuery.where(qProcessoDespesas.dataLancamento.goe(despesasFilter.getDataInicio()));
            }

            // dataFim
            if (Objects.nonNull(despesasFilter.getDataFim())) {
                jpaQuery.where(qProcessoDespesas.dataLancamento.loe(despesasFilter.getDataFim()));
            }

            // clienteProcesso
            if (Objects.nonNull(despesasFilter.getClienteProcesso())) {
                jpaQuery.where(qProcessoDespesas.processos.cliente.eq(despesasFilter.getClienteProcesso()));
            }

            //Lista de despesas
            if (Objects.nonNull(despesasFilter.getProcessoDespesasList())) {
                if (despesasFilter.getProcessoDespesasList().size() > 0) {
                    jpaQuery.where(qProcessoDespesas.in(despesasFilter.getProcessoDespesasList()));
                }
            }

            // responsavel
            if (Objects.nonNull(despesasFilter.getResponsavel())) {
                jpaQuery.where(qProcessoDespesas.pessoa.eq(despesasFilter.getResponsavel()));
            }
        }

        return jpaQuery;
    }


    public List<ProcessoDespesas> findByPeriodoCliente(Calendar dataInicio, Calendar dataFim, Pessoa cliente, String sortProperty, Sorter.Direction sortDirection, Long page, Long limit) {
        final PathBuilder<ProcessoDespesas> pathBuilder = new PathBuilder<>(ProcessoDespesas.class, "processoDespesas");
        QProcessoDespesas qProcessoDespesas = QProcessoDespesas.processoDespesas;
        QProcesso qProcesso = QProcesso.processo1;
        QLoteCustasItem qLoteCustasItem = QLoteCustasItem.loteCustasItem;

        JPAQuery<ProcessoDespesas> query = new JPAQueryFactory(entityManager)
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
                .where(qProcessoDespesas.notIn(JPAExpressions
                        .select(qLoteCustasItem.processoDespesas)
                        .from(qLoteCustasItem)
                        .fetchAll()));

        if (Objects.nonNull(dataInicio)) {
            query.where(qProcessoDespesas.dataLancamento.goe(dataInicio));
        }

        if (Objects.nonNull(dataFim)) {
            query.where(qProcessoDespesas.dataLancamento.loe(dataFim));
        }

        if (Objects.nonNull(cliente)) {
            query.where(qProcesso.cliente.eq(cliente));
        }

        if (sortDirection.equals(Sorter.Direction.ASC)) {
            query.orderBy(pathBuilder.getString(sortProperty).asc());
        } else {
            query.orderBy(pathBuilder.getString(sortProperty).desc());
        }

        // page
        /*if (Optional.ofNullable(page).orElse(0L) > 0L) {
            query.offset(((page - 1L) * limit));
        }

        // limit
        query.limit(limit);*/

        return query.fetch();
    }


    public List<ProcessoDespesas> findByProcessoUnicoNumeroDocumento(String idProcessoUnico, String numeroDocumento) {
        final QProcessoDespesas qProcessoDespesas = QProcessoDespesas.processoDespesas;
        final QProcesso qProcesso = QProcesso.processo1;

        final JPAQueryFactory factory = new JPAQueryFactory(entityManager);
        final JPAQuery query = factory.query();

        query.select(qProcessoDespesas)
                .from(qProcessoDespesas)
                .join(qProcessoDespesas.processos, qProcesso)
                .where(qProcesso.processoUnico.eq(idProcessoUnico)
                        .and(qProcessoDespesas.numeroDocumento.eq(numeroDocumento)));

        return query.fetch();
    }


    public void updateStatusPagamentoImportacao(Processo processo, String numeroDocumento, EnumStatusPagamentoDespesas statusPagamentoDespesas) {
        final QProcessoDespesas qProcessoDespesas = QProcessoDespesas.processoDespesas;

        final JPAQueryFactory factory = new JPAQueryFactory(entityManager);

        factory.update(qProcessoDespesas)
                .set(qProcessoDespesas.statusPagamento, statusPagamentoDespesas)
                .where(qProcessoDespesas.processos.eq(processo)
                        .and(qProcessoDespesas.numeroDocumento.eq(numeroDocumento))
                        .and(qProcessoDespesas.statusPagamento.ne(statusPagamentoDespesas)))
                .execute();
    }


    public List<ProcessoDespesas> findAllCache() {
        final QProcessoDespesas qProcessoDespesas = QProcessoDespesas.processoDespesas;
        return new JPAQueryFactory(entityManager)
                .select(qProcessoDespesas)
                .from(qProcessoDespesas)
                .fetch();
    }
}
