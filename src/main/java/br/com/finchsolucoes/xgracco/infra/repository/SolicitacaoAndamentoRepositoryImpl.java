package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.enums.SolicitacaoAndamentoLogStatus;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.SolicitacaoAndamentoFilter;
import br.com.finchsolucoes.xgracco.domain.repository.SolicitacaoAndamentoJpaRepository;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Classe responsável por implementar todos os casos de repositório relacionados à entidade {@link SolicitacaoAndamento}.
 *
 * @author andre.baroni
 * @since 16/10/2018
 */
@Repository
public class SolicitacaoAndamentoRepositoryImpl extends AbstractJpaRepository<SolicitacaoAndamento, Long> implements SolicitacaoAndamentoJpaRepository {


    public List<SolicitacaoAndamento> find(Query<SolicitacaoAndamento> query) {
        final JPAQuery<SolicitacaoAndamento> jpaQuery = createQuery(query);

        super.applySorter(jpaQuery, query.getSorter());
        super.applyPagination(jpaQuery, query.getPage(), query.getLimit());

        return jpaQuery.fetch();
    }


    public long count(Query<SolicitacaoAndamento> query) {
        return this.createQuery(query).fetchCount();
    }


    public Optional<SolicitacaoAndamento> findBySolicitacao(String solicitacao) {
        QSolicitacaoAndamento qSolicitacaoAndamento = QSolicitacaoAndamento.solicitacaoAndamento;
        QCapturaAndamento qCapturaAndamento = QCapturaAndamento.capturaAndamento;
        QSolicitacaoAndamentoLog qSolicitacaoAndamentoLog = QSolicitacaoAndamentoLog.solicitacaoAndamentoLog;
        QProcesso qProcesso = QProcesso.processo1;

        return Optional.ofNullable(new JPAQueryFactory(this.entityManager)
                .select(qSolicitacaoAndamento)
                .from(qSolicitacaoAndamento)
                .join(qSolicitacaoAndamento.capturaAndamento, qCapturaAndamento).fetchJoin()
                .join(qSolicitacaoAndamento.solicitacaoAndamentoLogs, qSolicitacaoAndamentoLog).fetchJoin()
                .join(qSolicitacaoAndamentoLog.processo, qProcesso).fetchJoin()
                .where(qSolicitacaoAndamento.solicitacao.eq(solicitacao))
                .fetchOne());
    }


    private JPAQuery<SolicitacaoAndamento> createQuery(Query<SolicitacaoAndamento> query) {
        final QSolicitacaoAndamento qSolicitacaoAndamento = QSolicitacaoAndamento.solicitacaoAndamento;
        final QSolicitacaoAndamentoLog qQuantidadeProcessosEnviados = new QSolicitacaoAndamentoLog("processosEnviados");
        final QSolicitacaoAndamentoLog qQuantidadeProcessosEncontrados = new QSolicitacaoAndamentoLog("processosEncontrados");
        final QSolicitacaoAndamentoLog qQuantidadeNovosAndamentos = new QSolicitacaoAndamentoLog("novosAndamentos");

        final JPAQuery<SolicitacaoAndamento> jpaQuery = new JPAQueryFactory(entityManager)
                .select(QSolicitacaoAndamento.create(
                        qSolicitacaoAndamento.id,
                        qSolicitacaoAndamento.capturaAndamento,
                        qSolicitacaoAndamento.solicitacao,
                        qSolicitacaoAndamento.dataSolicitacao,
                        JPAExpressions.select(qQuantidadeProcessosEnviados.countDistinct())
                                .from(qQuantidadeProcessosEnviados)
                                .where(qQuantidadeProcessosEnviados.solicitacaoAndamento.eq(qSolicitacaoAndamento)),
                        JPAExpressions.select(qQuantidadeProcessosEncontrados.countDistinct())
                                .from(qQuantidadeProcessosEncontrados)
                                .where(qQuantidadeProcessosEncontrados.solicitacaoAndamento.eq(qSolicitacaoAndamento))
                                .where(qQuantidadeProcessosEncontrados.status.eq(SolicitacaoAndamentoLogStatus.FINALIZADO)),
                        JPAExpressions.select(qQuantidadeNovosAndamentos.quantidadeNovoAndamento.sum().coalesce(0L))
                                .from(qQuantidadeNovosAndamentos)
                                .where(qQuantidadeNovosAndamentos.solicitacaoAndamento.eq(qSolicitacaoAndamento))
                                .where(qQuantidadeNovosAndamentos.status.eq(SolicitacaoAndamentoLogStatus.FINALIZADO))
                )).from(qSolicitacaoAndamento);

        if (Objects.nonNull(query.getFilter()) && query.getFilter() instanceof SolicitacaoAndamentoFilter) {
            SolicitacaoAndamentoFilter filter = (SolicitacaoAndamentoFilter) query.getFilter();

            this.applyGrupoCapturaSolicitacaoFilters(jpaQuery, filter);
            this.applyGrupoCapturaAndamentoFilters(jpaQuery, filter);
        }

        return jpaQuery;
    }

    private void applyGrupoCapturaAndamentoFilters(JPAQuery<SolicitacaoAndamento> jpaQuery, SolicitacaoAndamentoFilter filter) {
        QSolicitacaoAndamento qSolicitacaoAndamento = QSolicitacaoAndamento.solicitacaoAndamento;

        if (Objects.nonNull(filter.getCapturaAndamentoId())) {
            jpaQuery.where(qSolicitacaoAndamento.capturaAndamento.id.eq(filter.getCapturaAndamentoId()));
        }
    }

    private void applyGrupoCapturaSolicitacaoFilters(JPAQuery<SolicitacaoAndamento> jpaQuery, SolicitacaoAndamentoFilter filter) {
        QSolicitacaoAndamento qSolicitacaoAndamento = QSolicitacaoAndamento.solicitacaoAndamento;

        if (StringUtils.isNotBlank(filter.getSolicitacao())) {
            jpaQuery.where(qSolicitacaoAndamento.solicitacao.eq(filter.getSolicitacao()));
        }

        if (Objects.nonNull(filter.getDataSolicitacaoInicial())) {
            jpaQuery.where(qSolicitacaoAndamento.dataSolicitacao.goe(filter.getDataSolicitacaoInicial()));
        }

        if (Objects.nonNull(filter.getDataSolicitacaoFinal())) {
            jpaQuery.where(qSolicitacaoAndamento.dataSolicitacao.loe(filter.getDataSolicitacaoFinal()));
        }
    }

}
