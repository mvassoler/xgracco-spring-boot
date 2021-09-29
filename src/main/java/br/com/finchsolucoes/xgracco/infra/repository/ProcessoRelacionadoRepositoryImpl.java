package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Processo;
import br.com.finchsolucoes.xgracco.domain.entity.ProcessoRelacionado;
import br.com.finchsolucoes.xgracco.domain.entity.QProcesso;
import br.com.finchsolucoes.xgracco.domain.entity.QProcessoRelacionado;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.ProcessoRelacionadoFilter;
import br.com.finchsolucoes.xgracco.domain.repository.ProcessoRelacionadoJpaRepository;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class ProcessoRelacionadoRepositoryImpl extends AbstractJpaRepository<ProcessoRelacionado, Long> implements ProcessoRelacionadoJpaRepository {


    public List<ProcessoRelacionado> find(Query<ProcessoRelacionado> query) {
        JPAQuery<ProcessoRelacionado> jpaQuery = this.createQuery(query);
        this.applyPagination(jpaQuery, query.getPage(), query.getLimit());
        this.applySorter(jpaQuery, query.getSorter());

        return jpaQuery.fetch();
    }


    public long count(Query<ProcessoRelacionado> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<ProcessoRelacionado> createQuery(Query<ProcessoRelacionado> query) {
        QProcessoRelacionado qProcessoRelacionado = QProcessoRelacionado.processoRelacionado;
        QProcesso qProcesso = QProcesso.processo1;
        QProcesso qRelacionado = QProcesso.processo1;

        final JPAQuery<ProcessoRelacionado> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qProcessoRelacionado)
                .from(qProcessoRelacionado)
                .join(qProcessoRelacionado.processo, qProcesso)
                .join(qProcessoRelacionado.relacionado, qRelacionado);


        if (Objects.nonNull(query.getFilter()) && query.getFilter() instanceof ProcessoRelacionadoFilter) {

            final ProcessoRelacionadoFilter filter = (ProcessoRelacionadoFilter) query.getFilter();

            if (Objects.nonNull(filter.getIdProcesso())) {
                jpaQuery.where(qProcessoRelacionado.processo.id.eq(filter.getIdProcesso()));
            }

            if (Objects.nonNull(filter.getIdRelacionado())) {
                jpaQuery.where(qProcessoRelacionado.relacionado.id.eq(filter.getIdRelacionado()));
            }
        }

        return jpaQuery;
    }



    public void insertProcessoRelacionado(Long idProcesso, Long idRelacionado) {
        javax.persistence.Query query = entityManager.createNativeQuery("insert into PROCESSO_RELACIONADO (FK_PROCESSO, FK_PROCESSO_RELACIONADO) values (:idProcesso, :idRelacionado)");
        query.setParameter("idProcesso", idProcesso);
        query.setParameter("idRelacionado", idRelacionado);
        query.executeUpdate();
    }


    public Optional<ProcessoRelacionado> findOnlyProcessoRelacionadoByProcessoAndRelacionado(Processo processo, Processo relacionado) {
        final QProcessoRelacionado qProcessoRelacionado = QProcessoRelacionado.processoRelacionado;
        final QProcesso qProcesso = new QProcesso("processo");
        final QProcesso qRelacionado = new QProcesso("relacionado");

        JPAQuery<ProcessoRelacionado> jpaQuery = new JPAQueryFactory(this.entityManager)
                .select(QProcessoRelacionado.create(
                        QProcesso.create(
                                qProcesso.id,
                                qProcesso.numero
                        ),
                        QProcesso.create(
                                qRelacionado.id,
                                qRelacionado.numero
                        )
                ))
                .select(qProcessoRelacionado)
                .join(qProcessoRelacionado.processo, qProcesso)
                .join(qProcessoRelacionado.relacionado, qRelacionado)
                .where(qProcessoRelacionado.processo.eq(processo))
                .where(qProcessoRelacionado.relacionado.eq(relacionado));

        return Optional.ofNullable(jpaQuery.fetchOne());
    }


    public List<ProcessoRelacionado> findByProcesso(Processo processo) {
        final QProcessoRelacionado qProcessoRelacionado = QProcessoRelacionado.processoRelacionado;
        final QProcesso qProcesso = new QProcesso("processo");
        final QProcesso qRelacionado = new QProcesso("relacionado");

        JPAQuery<ProcessoRelacionado> jpaQuery = new JPAQueryFactory(this.entityManager)
                .select(QProcessoRelacionado.create(
                        QProcesso.create(
                                qProcesso.id,
                                qProcesso.numero
                        ),
                        QProcesso.create(
                                qRelacionado.id,
                                qRelacionado.numero
                        )
                ))
                .select(qProcessoRelacionado)
                .from(qProcessoRelacionado)
                .join(qProcessoRelacionado.processo, qProcesso)
                .join(qProcessoRelacionado.relacionado, qRelacionado)
                .where(qProcessoRelacionado.processo.in(processo));


        return jpaQuery.fetch();
    }

}