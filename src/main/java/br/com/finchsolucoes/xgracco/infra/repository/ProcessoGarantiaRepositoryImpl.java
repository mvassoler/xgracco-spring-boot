package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.ProcessoGarantiaFilter;
import br.com.finchsolucoes.xgracco.domain.repository.ProcessoGarantiaJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade ProcessoGarantia.
 *
 * @author Jordano
 * @since 2.2
 */


@Repository
public class ProcessoGarantiaRepositoryImpl extends AbstractJpaRepository<ProcessoGarantia, Long> implements ProcessoGarantiaJpaRepository {


    @Override
    public List<ProcessoGarantia> find(Processo processo, Query<ProcessoGarantia> query) {
        final PathBuilder<ProcessoGarantia> path = new PathBuilder<>(ProcessoGarantia.class, "garantia");
        final JPAQuery jpaQuery = createQuery(processo, query);

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
    public Long count(Processo processo, Query<ProcessoGarantia> query) {
        return createQuery(processo, query).fetchCount();
    }

    private JPAQuery createQuery(Processo processo, Query<ProcessoGarantia> query) {
        final QProcessoGarantia qProcessoGarantia = QProcessoGarantia.processoGarantia;
        final QTipoGarantia qTipoGarantia = QTipoGarantia.tipoGarantia;
        final QInformacoesAdicionais qInformacoesAdicionais = QInformacoesAdicionais.informacoesAdicionais;
        final QProcesso qProcesso = QProcesso.processo1;

        final JPAQuery<ProcessoGarantia> jpaQuery = new JPAQueryFactory(entityManager)
                .select(QProcessoGarantia.create(
                        qProcessoGarantia.id,
                        qProcessoGarantia.valor,
                        qProcessoGarantia.dataGarantia,
                        qProcessoGarantia.levantado,
                        qProcessoGarantia.dataLevantado,
                        qProcessoGarantia.obs,
                        qProcessoGarantia.dataCadastro,
                        QTipoGarantia.create(
                                qTipoGarantia.id,
                                qTipoGarantia.descricao
                        ),
                        QProcesso.create(
                                qProcesso.id,
                                qProcesso.numero),
                        qProcessoGarantia.dataAvaliacaoJudicial
                        )
                )
                .from(qProcessoGarantia)
                .join(qProcessoGarantia.tipoGarantia, qTipoGarantia)
                .join(qProcessoGarantia.processo, qProcesso)
                .where(qProcessoGarantia.processo.eq(processo));

        // filter
        if (query.getFilter() != null && query.getFilter() instanceof ProcessoGarantiaFilter) {
            final ProcessoGarantiaFilter filter = (ProcessoGarantiaFilter) query.getFilter();

            if (filter.getIdTipoGarantia() != null) {
                jpaQuery.where(qProcessoGarantia.tipoGarantia.id.eq(filter.getIdTipoGarantia()));
            }

            if (filter.getIdInformacoesAdicionais() != null) {
                jpaQuery.where(qInformacoesAdicionais.id.eq(filter.getIdInformacoesAdicionais()));
            }

        }
        return jpaQuery;
    }


    @Override
    public Optional<ProcessoGarantia> findById(Processo processo, Long id) {
        final QProcessoGarantia qProcessoGarantia = QProcessoGarantia.processoGarantia;
        final QTipoGarantia qTipoGarantia = QTipoGarantia.tipoGarantia;
        final QProcesso qProcesso = QProcesso.processo1;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .selectDistinct(QProcessoGarantia.create(
                        qProcessoGarantia.id,
                        qProcessoGarantia.valor,
                        qProcessoGarantia.dataGarantia,
                        qProcessoGarantia.levantado,
                        qProcessoGarantia.dataLevantado,
                        qProcessoGarantia.obs,
                        qProcessoGarantia.dataCadastro,
                        QTipoGarantia.create(
                                qTipoGarantia.id,
                                qTipoGarantia.descricao
                        ),
                        QProcesso.create(
                                qProcesso.id),
                        qProcessoGarantia.dataAvaliacaoJudicial
                        )
                )
                .from(qProcessoGarantia)
                .join(qProcessoGarantia.tipoGarantia, qTipoGarantia)
                .join(qProcessoGarantia.processo, qProcesso)
                .where(qProcessoGarantia.processo.eq(processo))
                .where(qProcessoGarantia.id.eq(id))
                .fetchOne());
    }
}
