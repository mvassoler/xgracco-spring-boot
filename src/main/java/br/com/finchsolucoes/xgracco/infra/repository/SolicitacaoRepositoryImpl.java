package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.enums.EnumFuncao;
import br.com.finchsolucoes.xgracco.domain.enums.EnumStatusSolicitacao;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.SolicitacaoLogFilter;
import br.com.finchsolucoes.xgracco.domain.repository.SolicitacaoJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Thiago Fogar
 * @since
 */
@Repository
public class SolicitacaoRepositoryImpl extends AbstractJpaRepository<SolicitacaoLog, Long> implements SolicitacaoJpaRepository {


    public List<SolicitacaoLog> find(Query<SolicitacaoLog> query) {
        final PathBuilder<SolicitacaoLog> path = new PathBuilder<>(SolicitacaoLog.class, "solicitacaoLog");
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


    public long count(Query<SolicitacaoLog> query) {
        return createQuery(query).fetchCount();
    }


    public long countSolicitacoesProcessoTutela(Query<SolicitacaoLog> query) {
        return createQuerySolicitacoesProcessoTutela(query).fetchCount();
    }

    private JPAQuery<SolicitacaoLog> createQuery(Query<SolicitacaoLog> query) {
        QSolicitacaoLog qSolicitacaoLog = QSolicitacaoLog.solicitacaoLog;
        QProcesso qProcesso = QProcesso.processo1;
        QPessoa qPessoa = QPessoa.pessoa;

        final JPAQuery<SolicitacaoLog> jpaQuery = new JPAQueryFactory(entityManager)
                .select(QSolicitacaoLog.create(
                        qSolicitacaoLog.id,
                        qSolicitacaoLog.data,
                        qSolicitacaoLog.dataLimite,
                        qSolicitacaoLog.dataAudiencia,
                        qSolicitacaoLog.horaAudiencia,
                        qSolicitacaoLog.idProcesso,
                        qSolicitacaoLog.local,
                        qSolicitacaoLog.textoSolicitacao,
                        qSolicitacaoLog.idSolicitacaoBoomer,
                        qSolicitacaoLog.descTipoSolicitacao,
                        qSolicitacaoLog.statusSolicitacao,
                        QProcesso.create(
                                qProcesso.id,
                                qProcesso.processoUnico)
                        ,
                        QPessoa.create(
                                qPessoa.id,
                                qPessoa.nomeRazaoSocial)
                        )
                )
                .from(qSolicitacaoLog)
                .join(qSolicitacaoLog.processo, qProcesso)
                .join(qSolicitacaoLog.solicitante, qPessoa);


        // filter
        if (query.getFilter() != null && query.getFilter() instanceof SolicitacaoLogFilter) {
            final SolicitacaoLogFilter solicitacaoLogFilter = (SolicitacaoLogFilter) query.getFilter();

            final Usuario usuario = solicitacaoLogFilter.getUsuarioLogado();
            final Boolean visualizarTodasSolicitacoes = usuario.hasPermissao(Permissao.NOTIFICACOES_SOLICITACOES_QUALQUER_USUARIO);
            final Boolean visualizarSomenteMinhasSolicitacoes = usuario.hasPermissao(Permissao.NOTIFICACOES_SOLICITACOES_MINHAS_SOLICITACOES);

            if (!visualizarTodasSolicitacoes) {
                if (!visualizarSomenteMinhasSolicitacoes) {
                    if (!usuario.getFuncoes().contains(EnumFuncao.COORDENADOR_DEPARTAMENTO)) {
                        if (usuario.hasFuncao(EnumFuncao.COORDENADOR_OPERACIONAL, EnumFuncao.COORDENADOR_ESTEIRA)) {
                            jpaQuery.where(qProcesso.escritorio.in(usuario.getEscritorios().stream().map(ue -> ue.getEscritorio()).collect(Collectors.toSet())));
                        } else if (usuario.hasFuncao(EnumFuncao.OPERACIONAL)) {
                            jpaQuery.where(qSolicitacaoLog.solicitante.eq(usuario.getPessoa()))
                                    .where(qProcesso.escritorio.in(usuario.getEscritorios().stream().map(ue -> ue.getEscritorio()).collect(Collectors.toSet())));
                        }
                    }
                } else {
                    jpaQuery.where(qSolicitacaoLog.solicitante.eq(usuario.getPessoa()));
                }
            }

            //id
            if (solicitacaoLogFilter.getId() != null) {
                jpaQuery.where(qSolicitacaoLog.idSolicitacaoBoomer.eq(solicitacaoLogFilter.getId()));
            }

            //solicitante
            if (solicitacaoLogFilter.getSolicitante().getId() != null) {
                jpaQuery.where(qSolicitacaoLog.solicitante.eq(solicitacaoLogFilter.getSolicitante()));
            }

            //idprocesso
            if (solicitacaoLogFilter.getIdProcesso() != null) {
                jpaQuery.where(qSolicitacaoLog.idProcesso.eq(solicitacaoLogFilter.getIdProcesso()));
            }

            if (solicitacaoLogFilter.getStatusSolicitacao() != null) {
                jpaQuery.where(qSolicitacaoLog.statusSolicitacao.eq(solicitacaoLogFilter.getStatusSolicitacao()));
            } else {
                if (!solicitacaoLogFilter.isIncluirConcluidas())
                    jpaQuery.where(qSolicitacaoLog.statusSolicitacao.ne(EnumStatusSolicitacao.CONCLUIDA));
            }

            //sempre filtra pela carteira do usuário
            jpaQuery.where(qProcesso.carteira.in(solicitacaoLogFilter.getUsuarioLogado().getPessoa().getCarteiras()));
        }

        return jpaQuery;
    }


    public Optional<SolicitacaoLog> findBySolicitacaoBoomer(Long idSolicitacao) {
        QSolicitacaoLog qSolicitacaoLog = QSolicitacaoLog.solicitacaoLog;
        QProcesso qProcesso = QProcesso.processo1;
        QCarteira qCarteira = QCarteira.carteira;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(qSolicitacaoLog)
                .from(qSolicitacaoLog)
                .join(qSolicitacaoLog.processo, qProcesso).fetchJoin()
                .join(qProcesso.carteira, qCarteira).fetchJoin()
                .where(qSolicitacaoLog.idSolicitacaoBoomer.eq(idSolicitacao))
                .fetchOne());
    }


    public List<SolicitacaoLog> findByProcesso(Long idProcesso) {
        QSolicitacaoLog qSolicitacaoLog = QSolicitacaoLog.solicitacaoLog;
        QProcesso qProcesso = QProcesso.processo1;
        QPessoa qPessoa = QPessoa.pessoa;

        return new JPAQueryFactory(entityManager)
                .select(QSolicitacaoLog.create(
                        qSolicitacaoLog.id,
                        qSolicitacaoLog.data,
                        qSolicitacaoLog.dataLimite,
                        qSolicitacaoLog.dataAudiencia,
                        qSolicitacaoLog.horaAudiencia,
                        qSolicitacaoLog.idProcesso,
                        qSolicitacaoLog.local,
                        qSolicitacaoLog.textoSolicitacao,
                        qSolicitacaoLog.idSolicitacaoBoomer,
                        qSolicitacaoLog.descTipoSolicitacao,
                        qSolicitacaoLog.statusSolicitacao,
                        QProcesso.create(
                                qProcesso.id,
                                qProcesso.processoUnico)
                        ,
                        QPessoa.create(
                                qPessoa.id,
                                qPessoa.nomeRazaoSocial)
                        )
                )
                .from(qSolicitacaoLog)
                .join(qSolicitacaoLog.processo, qProcesso)
                .join(qSolicitacaoLog.solicitante, qPessoa)
                .where(qSolicitacaoLog.idProcesso.eq(idProcesso))
                .fetch();
    }


    public List<SolicitacaoLog> findSolicitacoesProcesso(Query<SolicitacaoLog> query) {
        final PathBuilder<SolicitacaoLog> path = new PathBuilder<>(SolicitacaoLog.class, "solicitacaoLog");
        final JPAQuery jpaQuery = createQuerySolicitacoesProcessoTutela(query);

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

    private JPAQuery<SolicitacaoLog> createQuerySolicitacoesProcessoTutela(Query<SolicitacaoLog> query) {
        QSolicitacaoLog qSolicitacaoLog = QSolicitacaoLog.solicitacaoLog;
        QProcesso qProcesso = QProcesso.processo1;
        QPessoa qPessoa = QPessoa.pessoa;

        final JPAQuery<SolicitacaoLog> jpaQuery = new JPAQueryFactory(entityManager)
                .select(QSolicitacaoLog.create(
                        qSolicitacaoLog.id,
                        qSolicitacaoLog.data,
                        qSolicitacaoLog.dataLimite,
                        qSolicitacaoLog.dataAudiencia,
                        qSolicitacaoLog.horaAudiencia,
                        qSolicitacaoLog.idProcesso,
                        qSolicitacaoLog.local,
                        qSolicitacaoLog.textoSolicitacao,
                        qSolicitacaoLog.idSolicitacaoBoomer,
                        qSolicitacaoLog.descTipoSolicitacao,
                        qSolicitacaoLog.statusSolicitacao,
                        QProcesso.create(
                                qProcesso.id,
                                qProcesso.processoUnico)
                        ,
                        QPessoa.create(
                                qPessoa.id,
                                qPessoa.nomeRazaoSocial)
                        )
                )
                .from(qSolicitacaoLog)
                .join(qSolicitacaoLog.processo, qProcesso)
                .join(qSolicitacaoLog.solicitante, qPessoa);


//        // filter
        if (query.getFilter() != null && query.getFilter() instanceof SolicitacaoLogFilter) {
            final SolicitacaoLogFilter solicitacaoLogFilter = (SolicitacaoLogFilter) query.getFilter();

            //id
            if (solicitacaoLogFilter.getId() != null) {
                jpaQuery.where(qSolicitacaoLog.idSolicitacaoBoomer.eq(solicitacaoLogFilter.getId()));
            }

            //solicitante
            if (solicitacaoLogFilter.getSolicitante().getId() != null) {
                jpaQuery.where(qSolicitacaoLog.solicitante.eq(solicitacaoLogFilter.getSolicitante()));
            }

            //idprocesso
            if (solicitacaoLogFilter.getIdProcesso() != null) {
                jpaQuery.where(qSolicitacaoLog.idProcesso.eq(solicitacaoLogFilter.getIdProcesso()));

            }

            //sempre filtra pela carteira do usuário
            jpaQuery.where(qProcesso.carteira.in(solicitacaoLogFilter.getUsuarioLogado().getPessoa().getCarteiras()));
        }

        return jpaQuery;
    }
}
