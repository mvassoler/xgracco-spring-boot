package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.enums.EnumFuncao;
import br.com.finchsolucoes.xgracco.domain.repository.SolicitacaoAndamentoLogJpaRepository;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Classe responsável por implementar todos os casos de repositório relacionados à entidade {@link SolicitacaoAndamentoLog}.
 *
 * @author andre.baroni
 * @since 16/10/2018
 */
@Repository
public class SolicitacaoAndamentoLogRepositoryImpl extends AbstractJpaRepository<SolicitacaoAndamentoLog, Long> implements SolicitacaoAndamentoLogJpaRepository {


//    public List<SolicitacaoAndamentoLog> find(Query<SolicitacaoAndamentoLog> query) {
//        final JPAQuery<SolicitacaoAndamentoLog> jpaQuery = createQuery(query);
//
//        super.applySorter(jpaQuery, query.getSorter());
//        super.applyPagination(jpaQuery, query.getPage(), query.getLimit());
//
//        return jpaQuery.fetch();
//    }


//    public long count(Query<SolicitacaoAndamentoLog> query) {
//        return this.createQuery(query).fetchCount();
//    }

//    private JPAQuery<SolicitacaoAndamentoLog> createQuery(Query<SolicitacaoAndamentoLog> query) {
//        QSolicitacaoAndamentoLog qSolicitacaoAndamentoLog = QSolicitacaoAndamentoLog.solicitacaoAndamentoLog;
//        QSolicitacaoAndamento qSolicitacaoAndamento = QSolicitacaoAndamento.solicitacaoAndamento;
//        QCapturaAndamento qCapturaAndamento = QCapturaAndamento.capturaAndamento;
//        QProcesso qProcesso = QProcesso.processo1;
//
//        final JPAQuery<SolicitacaoAndamentoLog> jpaQuery = new JPAQueryFactory(entityManager)
//                .select(qSolicitacaoAndamentoLog)
//                .from(qSolicitacaoAndamentoLog)
//                .join(qSolicitacaoAndamentoLog.solicitacaoAndamento, qSolicitacaoAndamento).fetchJoin()
//                .join(qSolicitacaoAndamento.capturaAndamento, qCapturaAndamento).fetchJoin()
//                .join(qSolicitacaoAndamentoLog.processo, qProcesso).fetchJoin();
//
//        if (Objects.nonNull(query.getFilter()) && query.getFilter() instanceof SolicitacaoAndamentoLogFilter) {
//            SolicitacaoAndamentoLogFilter filter = (SolicitacaoAndamentoLogFilter) query.getFilter();
//
//            if (Objects.nonNull(filter.getSolicitacaoAndamentoId())) {
//                jpaQuery.where(qSolicitacaoAndamento.id.eq(filter.getSolicitacaoAndamentoId()));
//            }
//
//            if (Objects.nonNull(filter.getCapturaAndamentoId())) {
//                jpaQuery.where(qCapturaAndamento.id.eq(filter.getCapturaAndamentoId()));
//            }
//
//            this.applyUserVisualizationFilter(jpaQuery);
//        }
//
//        return jpaQuery;
//    }


    public Collection<SolicitacaoAndamentoLog> findBySolicitacaoAndamentoAndNumeroProcesso(SolicitacaoAndamento solicitacaoAndamento,
                                                                                           String processoCnj) {
        QSolicitacaoAndamentoLog qSolicitacaoAndamentoLog = QSolicitacaoAndamentoLog.solicitacaoAndamentoLog;
        QProcesso qProcesso = QProcesso.processo1;

        return new JPAQueryFactory(this.entityManager)
                .select(qSolicitacaoAndamentoLog)
                .from(qSolicitacaoAndamentoLog)
                .join(qSolicitacaoAndamentoLog.processo, qProcesso).fetchJoin()
                .where(qSolicitacaoAndamentoLog.solicitacaoAndamento.eq(solicitacaoAndamento))
                .where(qProcesso.numero.eq(processoCnj))
                .fetch();
    }


//    public void applyUserVisualizationFilter(JPAQuery jpaQuery) {
//        Pessoa pessoaAutenticada = Util.getUsuarioLogado();
//
//        if (pessoaAutenticada.getUsuarioSistema().hasPermissao(Permissao.PROCESSOS_PESQUISA_TODOS)) {
//            return;
//        }
//
//        this.applyCarteiraFilter(jpaQuery, pessoaAutenticada);
//        this.applyFuncaoFilter(jpaQuery, pessoaAutenticada);
//    }


    private void applyCarteiraFilter(JPAQuery jpaQuery, Pessoa pessoaAutenticada) {
        jpaQuery.where(QProcesso.processo1.carteira.in(pessoaAutenticada.getCarteiras()));
    }

    private void applyFuncaoFilter(JPAQuery jpaQuery, Pessoa pessoaAutenticada) {
        QUsuarioEscritorio qUsuarioEscritorio = QUsuarioEscritorio.usuarioEscritorio;

        if (pessoaAutenticada.getUsuarioSistema().hasFuncao(EnumFuncao.COORDENADOR_OPERACIONAL).equals(Boolean.TRUE)) {
            jpaQuery.where(JPAExpressions
                    .select(qUsuarioEscritorio)
                    .from(qUsuarioEscritorio)
                    .where(qUsuarioEscritorio.escritorio.eq(QProcesso.processo1.escritorio))
                    .where(qUsuarioEscritorio.usuario.id.eq(pessoaAutenticada.getId()))
                    .exists());
        } else if (pessoaAutenticada.getUsuarioSistema().hasFuncao(EnumFuncao.OPERACIONAL).equals(Boolean.TRUE)) {
            jpaQuery.where(QProcesso.processo1.operacional.id.eq(pessoaAutenticada.getId()));
        }
    }
}
