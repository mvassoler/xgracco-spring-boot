package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.enums.EnumRotinaStatus;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.RotinaExecucaoFilter;
import br.com.finchsolucoes.xgracco.domain.repository.RotinaExecucaoJpaRepository;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade RotinaExecucao.
 *
 * @author Renan Gigliotti
 * @since 2.0
 */
@Repository
public class RotinaExecucaoRepositoryImpl extends AbstractJpaRepository<RotinaExecucao, Long> implements RotinaExecucaoJpaRepository {


    public List<RotinaExecucao> find(Query<RotinaExecucao> query) {
        final PathBuilder<RotinaExecucao> path = new PathBuilder<>(RotinaExecucao.class, "rotinaExecucao");
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


    public long count(Query<RotinaExecucao> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<RotinaExecucao> createQuery(Query<RotinaExecucao> query) {
        QRotinaExecucao qRotinaExecucao = QRotinaExecucao.rotinaExecucao;
        QRotinaExecucaoLog qDetalheSucesso = new QRotinaExecucaoLog("rotinaExecucaoLogSucesso");
        QRotinaExecucaoLog qDetalheErro = new QRotinaExecucaoLog("rotinaExecucaoLogErro");

        final JPAQuery<RotinaExecucao> jpaQuery = new JPAQueryFactory(entityManager)
                .select(QRotinaExecucao.create(
                        qRotinaExecucao.id,
                        QRotina.create(qRotinaExecucao.rotina.id),
                        qRotinaExecucao.descricao,
                        qRotinaExecucao.job,
                        qRotinaExecucao.status,
                        qRotinaExecucao.dataInicioExecucao,
                        qRotinaExecucao.dataFimExecucao,
                        QUsuario.create(qRotinaExecucao.usuario.id),
                        qRotinaExecucao.dataAcao,
                        JPAExpressions.select(qDetalheSucesso.count())
                                .from(qDetalheSucesso)
                                .where(qDetalheSucesso.execucao.id.eq(qRotinaExecucao.id)
                                        .and(qDetalheSucesso.status.eq(EnumRotinaStatus.SUCESSO))),
                        JPAExpressions.select(qDetalheErro.count())
                                .from(qDetalheErro)
                                .where(qDetalheErro.execucao.id.eq(qRotinaExecucao.id)
                                        .and(qDetalheErro.status.eq(EnumRotinaStatus.ERRO)))))
                .from(qRotinaExecucao);

        // filter
        if (query.getFilter() != null && query.getFilter() instanceof RotinaExecucaoFilter) {
            final RotinaExecucaoFilter rotinaExecucaoFilter = (RotinaExecucaoFilter) query.getFilter();

            // id
            if (rotinaExecucaoFilter.getId() != null) {
                jpaQuery.where(qRotinaExecucao.id.eq(rotinaExecucaoFilter.getId()));
            }

            // rotina
            if (rotinaExecucaoFilter.getRotina() != null) {
                jpaQuery.where(qRotinaExecucao.rotina.id.eq(rotinaExecucaoFilter.getRotina().getId()));
            }

            // descricao
            if (StringUtils.isNotEmpty(rotinaExecucaoFilter.getDescricao())) {
                jpaQuery.where(qRotinaExecucao.descricao.likeIgnoreCase("%" + rotinaExecucaoFilter.getDescricao() + "%"));
            }

            // job
            if (rotinaExecucaoFilter.getJob() != null) {
                jpaQuery.where(qRotinaExecucao.job.eq(rotinaExecucaoFilter.getJob()));
            }

            // status
            if (rotinaExecucaoFilter.getStatus() != null) {
                jpaQuery.where(qRotinaExecucao.status.eq(rotinaExecucaoFilter.getStatus()));
            }

            // dataExecucao
            if (rotinaExecucaoFilter.getDataExecucao() != null) {
                Calendar dataInicio = Calendar.getInstance();
                dataInicio.setTime(rotinaExecucaoFilter.getDataExecucao().getTime());
                Util.correcaoInicioDia(dataInicio);
                Calendar dataFim = Calendar.getInstance();
                dataFim.setTime(rotinaExecucaoFilter.getDataExecucao().getTime());
                Util.correcaoFimDia(dataFim);
                jpaQuery.where(qRotinaExecucao.dataInicioExecucao.between(dataInicio, dataFim));
            }

            if (rotinaExecucaoFilter.getCiencia() != null) {
                jpaQuery.where(rotinaExecucaoFilter.getCiencia() ? qRotinaExecucao.dataAcao.isNotNull() : qRotinaExecucao.dataAcao.isNull());
            }
        }

        return jpaQuery;
    }
}
