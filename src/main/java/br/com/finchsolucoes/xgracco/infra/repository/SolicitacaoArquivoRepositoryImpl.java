package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.SolicitacaoArquivoFilter;
import br.com.finchsolucoes.xgracco.domain.repository.SolicitacaoArquivoJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Thiago Fogar
 * @since 2.2.5
 */
@Repository
public class SolicitacaoArquivoRepositoryImpl extends AbstractJpaRepository<SolicitacaoArquivo, Long> implements SolicitacaoArquivoJpaRepository {


    public List<SolicitacaoArquivo> find(Query<SolicitacaoArquivo> query) {
        final PathBuilder<SolicitacaoLog> path = new PathBuilder<>(SolicitacaoLog.class, "solicitacaoArquivo");
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


    public long count(Query<SolicitacaoArquivo> query) {
        return createQuery(query).fetchCount();
    }


    public List<SolicitacaoArquivo> findBySolicitacaoBoomer(Long idSolicitacaoBoomer) {
        QSolicitacaoArquivo qSolicitacaoArquivo = QSolicitacaoArquivo.solicitacaoArquivo;
        QSolicitacaoLog qSolicitacaoLog = QSolicitacaoLog.solicitacaoLog;

        return new JPAQueryFactory(entityManager)
                .select(qSolicitacaoArquivo)
                .from(qSolicitacaoArquivo)
                .join(qSolicitacaoArquivo.solicitacaoLog, qSolicitacaoLog)
                .where(qSolicitacaoLog.idSolicitacaoBoomer.eq(idSolicitacaoBoomer))
                .fetch();
    }


    public List<SolicitacaoArquivo> findBySolicitacao(Long id) {
        QSolicitacaoArquivo qSolicitacaoArquivo = QSolicitacaoArquivo.solicitacaoArquivo;

        return new JPAQueryFactory(entityManager)
                .select(qSolicitacaoArquivo)
                .from(qSolicitacaoArquivo)
                .where(qSolicitacaoArquivo.solicitacaoLog.id.eq(id))
                .fetch();
    }

    private JPAQuery<SolicitacaoArquivo> createQuery(Query<SolicitacaoArquivo> query) {

        QSolicitacaoArquivo qSolicitacaoArquivo = QSolicitacaoArquivo.solicitacaoArquivo;
        QArquivo qArquivo = QArquivo.arquivo;
        QSolicitacaoLog qSolicitacaoLog = QSolicitacaoLog.solicitacaoLog;


        final JPAQuery<SolicitacaoArquivo> jpaQuery = new JPAQueryFactory(entityManager)
                .select(QSolicitacaoArquivo.create(
                        qSolicitacaoArquivo.id,
                        QArquivo.create(
                                qArquivo.id,
                                qArquivo.nomeArquivo,
                                qArquivo.caminhoDocumento,
                                qArquivo.tipoDocumento,
                                qArquivo.profile,
                                qArquivo.dataAnexo
                        ),
                        QSolicitacaoLog.create(
                                qSolicitacaoLog.id
                        )
                ))
                .from(qSolicitacaoArquivo)
                .join(qSolicitacaoArquivo.solicitacaoLog, qSolicitacaoLog)
                .join(qSolicitacaoArquivo.arquivo, qArquivo);

        if (query.getFilter() != null && query.getFilter() instanceof SolicitacaoArquivoFilter) {
            final SolicitacaoArquivoFilter solicitacaoArquivoFilter = (SolicitacaoArquivoFilter) query.getFilter();

            if (solicitacaoArquivoFilter.getIdProcesso() != null) {
                jpaQuery.where(qSolicitacaoLog.idProcesso.eq(solicitacaoArquivoFilter.getIdProcesso()));
            }

            if (solicitacaoArquivoFilter.getIdSolicitacao() != null) {
                jpaQuery.where(qSolicitacaoLog.id.eq(solicitacaoArquivoFilter.getIdSolicitacao()));
            }

            if (solicitacaoArquivoFilter.getIdSolicitacaoBoomer() != null) {
                jpaQuery.where(qSolicitacaoLog.idSolicitacaoBoomer.eq(solicitacaoArquivoFilter.getIdSolicitacaoBoomer()));
            }
        }

        return jpaQuery;
    }

    public Optional<SolicitacaoArquivo> findBySolicitacaoAndArquivo(SolicitacaoLog solicitacao, Arquivo arquivo) {
        final QSolicitacaoArquivo qSolicitacaoArquivo = QSolicitacaoArquivo.solicitacaoArquivo;
        final QArquivo qArquivo = QArquivo.arquivo;
        final QSolicitacaoLog qSolicitacaoLog = QSolicitacaoLog.solicitacaoLog;

        return Optional.ofNullable(new JPAQueryFactory(this.entityManager)
                .select(qSolicitacaoArquivo)
                .from(qSolicitacaoArquivo)
                .join(qSolicitacaoArquivo.arquivo, qArquivo)
                .join(qSolicitacaoArquivo.solicitacaoLog, qSolicitacaoLog)
                .where(qArquivo.eq(arquivo))
                .where(qSolicitacaoLog.eq(solicitacao))
                .fetchOne());
    }



}
