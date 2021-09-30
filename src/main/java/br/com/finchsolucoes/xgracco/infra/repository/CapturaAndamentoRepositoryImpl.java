package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.CapturaAndamento;
import br.com.finchsolucoes.xgracco.domain.entity.QCapturaAndamento;
import br.com.finchsolucoes.xgracco.domain.entity.QCapturaAndamentoProcesso;
import br.com.finchsolucoes.xgracco.domain.entity.QProcesso;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.CapturaAndamentoFilter;
import br.com.finchsolucoes.xgracco.domain.repository.CapturaAndamentoJpaRepository;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

/**
 * Implementação da camada de repository da entidade {@link CapturaAndamento}.
 *
 * @author andre.baroni
 */
@Repository
public class CapturaAndamentoRepositoryImpl extends AbstractJpaRepository<CapturaAndamento, Long> implements CapturaAndamentoJpaRepository {

    @Override
    public List<CapturaAndamento> find(Query<CapturaAndamento> query) {
        JPAQuery<CapturaAndamento> jpaQuery = this.createQuery(query);

        this.applyPagination(jpaQuery, query.getPage(), query.getLimit());
        this.applySorter(jpaQuery, query.getSorter());

        return jpaQuery.fetch();
    }

    @Override
    public boolean exists(CapturaAndamento capturaAndamento){

        Query<CapturaAndamento> capturaAndamentoQuery = Query.<CapturaAndamento>builder()
                .filter(new CapturaAndamentoFilter(capturaAndamento.getDescricao()))
                .build();

        return Objects.isNull(createQueryExists(capturaAndamentoQuery).fetchFirst());

    }

    private JPAQuery<CapturaAndamento> createQueryExists(Query<CapturaAndamento> query) {

        QCapturaAndamento qCapturaAndamento = QCapturaAndamento.capturaAndamento;
        QCapturaAndamentoProcesso qCapturaAndamentoProcesso = QCapturaAndamentoProcesso.capturaAndamentoProcesso;

        JPAQuery<CapturaAndamento> jpaQuery = new JPAQueryFactory(entityManager)
                .select(QCapturaAndamento.create(
                        qCapturaAndamento.id,
                        qCapturaAndamento.descricao,
                        JPAExpressions.select(qCapturaAndamentoProcesso.countDistinct())
                                .from(qCapturaAndamentoProcesso)
                                .where(qCapturaAndamentoProcesso.ativo.eq(true))
                                .where(qCapturaAndamentoProcesso.capturaAndamento.eq(qCapturaAndamento))
                ))
                .from(qCapturaAndamento);

        if (Objects.nonNull(query.getFilter()) && query.getFilter() instanceof CapturaAndamentoFilter) {
            final CapturaAndamentoFilter filter = (CapturaAndamentoFilter) query.getFilter();

            if (StringUtils.isNotBlank(filter.getDescricao())) {
                jpaQuery.where(qCapturaAndamento.descricao.equalsIgnoreCase(filter.getDescricao()));
            }
        }

        return jpaQuery;
    }

    @Override
    public long count(Query<CapturaAndamento> query) {
        return this.createQuery(query).fetchCount();
    }

    private JPAQuery<CapturaAndamento> createQuery(Query<CapturaAndamento> query) {
        QCapturaAndamento qCapturaAndamento = QCapturaAndamento.capturaAndamento;
        QCapturaAndamentoProcesso qCapturaAndamentoProcesso = QCapturaAndamentoProcesso.capturaAndamentoProcesso;
        QProcesso qProcesso = QProcesso.processo1;

        JPAQuery<CapturaAndamento> jpaQuery = new JPAQueryFactory(entityManager)
                .select(QCapturaAndamento.create(
                        qCapturaAndamento.id,
                        qCapturaAndamento.descricao,
                        JPAExpressions.select(qCapturaAndamentoProcesso.countDistinct())
                                .from(qCapturaAndamentoProcesso)
                                .where(qCapturaAndamentoProcesso.ativo.eq(true))
                                .where(qCapturaAndamentoProcesso.capturaAndamento.eq(qCapturaAndamento))
                ))
                .from(qCapturaAndamento);

        if (Objects.nonNull(query.getFilter()) && query.getFilter() instanceof CapturaAndamentoFilter) {
            final CapturaAndamentoFilter filter = (CapturaAndamentoFilter) query.getFilter();

            if (StringUtils.isNotBlank(filter.getDescricao())) {
                jpaQuery.where(qCapturaAndamento.descricao.likeIgnoreCase("%" + filter.getDescricao() + "%"));
            }
            if(StringUtils.isNotBlank(filter.getProcessoUnico())){

                jpaQuery.where(JPAExpressions
                        .select(qCapturaAndamentoProcesso)
                        .from(qCapturaAndamentoProcesso)
                        .innerJoin(qCapturaAndamentoProcesso.processo, qProcesso)
                        .where(qCapturaAndamentoProcesso.ativo.eq(true))
                        .where(qCapturaAndamentoProcesso.capturaAndamento.eq(qCapturaAndamento))
                        .where(qCapturaAndamentoProcesso.processo.eq(qProcesso))
                        .where(qProcesso.processoUnico.likeIgnoreCase("%" + filter.getProcessoUnico() + "%"))
                        .exists());
            }
        }
        return jpaQuery;
    }

}
