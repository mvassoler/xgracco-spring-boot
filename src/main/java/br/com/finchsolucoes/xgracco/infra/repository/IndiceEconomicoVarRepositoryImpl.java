package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.IndiceEconomico;
import br.com.finchsolucoes.xgracco.domain.entity.IndiceEconomicoVar;
import br.com.finchsolucoes.xgracco.domain.entity.QIndiceEconomico;
import br.com.finchsolucoes.xgracco.domain.entity.QIndiceEconomicoVar;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.domain.query.impl.IndiceEconomicoVarFilter;
import br.com.finchsolucoes.xgracco.domain.repository.IndiceEconomicoVarJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

/**
 * Implementação JPA do Repository da entidade IndiceEconomicoVar
 *
 * @author Paulo Marçon
 * @since 2.1
 */
@Repository
public class IndiceEconomicoVarRepositoryImpl extends AbstractJpaRepository<IndiceEconomicoVar, Long> implements IndiceEconomicoVarJpaRepository {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public List<IndiceEconomicoVar> findVariaveis(IndiceEconomico indiceEconomico) {
        QIndiceEconomicoVar qIndiceEconomicoVar = QIndiceEconomicoVar.indiceEconomicoVar;
        QIndiceEconomico qIndiceEconomico = QIndiceEconomico.indiceEconomico;

        return new JPAQueryFactory(entityManager)
                .select(qIndiceEconomicoVar)
                .from(qIndiceEconomicoVar)
                .join(qIndiceEconomicoVar.indiceEconomico, qIndiceEconomico)
                .where(qIndiceEconomico.eq(indiceEconomico))
                .orderBy(qIndiceEconomicoVar.dataInicial.desc())
                .fetch();
    }

    @Override
    public void create(IndiceEconomico indiceEconomico, List<IndiceEconomicoVar> indicesEconomicosVar) {
        indicesEconomicosVar
                .stream()
                .forEach(v -> {
                    entityManager.detach(v);
                    v.setId(null);
                    v.setIndiceEconomico(indiceEconomico);
                    entityManager.persist(v);
                });

    }

    @Override
    public List<IndiceEconomicoVar> find(Query<IndiceEconomicoVar> query) {
        final JPAQuery<IndiceEconomicoVar> jpaQuery = createQuery(query);
        final PathBuilder<IndiceEconomico> builder = new PathBuilder<>(IndiceEconomico.class, "indiceEconomicoVar");

        if (query.getSorter() != null && StringUtils.isNotBlank(query.getSorter().getProperty())) {
            if (query.getSorter().getDirection().equals(Sorter.Direction.ASC)) {
                jpaQuery.orderBy(builder.getString(query.getSorter().getProperty()).asc());
            } else {
                jpaQuery.orderBy(builder.getString(query.getSorter().getProperty()).desc());
            }
        } else {
            jpaQuery.orderBy(builder.getString("id").asc());
        }

        if (Optional.ofNullable(query.getPage()).orElse(0L) > 0L) {
            jpaQuery.offset((query.getPage() - 1L) * query.getLimit());
        }

        jpaQuery.limit(query.getLimit());

        return jpaQuery.fetch();
    }

    @Override
    public List<IndiceEconomicoVar> findByData(IndiceEconomicoVar indiceEconomicoVar) {

        QIndiceEconomicoVar qIndiceEconomicoVar = QIndiceEconomicoVar.indiceEconomicoVar;

        return new JPAQueryFactory(entityManager)
                .select(qIndiceEconomicoVar)
                .from(qIndiceEconomicoVar)
                .where(qIndiceEconomicoVar.indiceEconomico.eq(indiceEconomicoVar.getIndiceEconomico()))
                .where(qIndiceEconomicoVar.dataInicial.between(indiceEconomicoVar.getDataInicial(), indiceEconomicoVar.getDataFinal())
                        .or((qIndiceEconomicoVar.dataFinal.between(indiceEconomicoVar.getDataInicial(), indiceEconomicoVar.getDataFinal()))))
                .fetch();
    }

    @Override
    public void deleteByIndiceEconomico(IndiceEconomico indiceEconomico) {
        QIndiceEconomicoVar qIndiceEconomicoVar = QIndiceEconomicoVar.indiceEconomicoVar;

        new JPADeleteClause(entityManager, qIndiceEconomicoVar)
                .where(qIndiceEconomicoVar.indiceEconomico.eq(indiceEconomico))
                .execute();
    }

    @Override
    public List<IndiceEconomicoVar> findByIndiceAndData(IndiceEconomico indiceEconomico, Calendar dataInicio, Calendar dataFim) {
        final QIndiceEconomicoVar qIndiceEconomicoVar = QIndiceEconomicoVar.indiceEconomicoVar;
        final QIndiceEconomico qIndiceEconomico = QIndiceEconomico.indiceEconomico;

        return new JPAQueryFactory(entityManager)
                .select(qIndiceEconomicoVar)
                .from(qIndiceEconomicoVar)
                .join(qIndiceEconomicoVar.indiceEconomico, qIndiceEconomico)
                .where(qIndiceEconomico.eq(indiceEconomico))
                .where(qIndiceEconomicoVar.dataInicial.between(dataInicio, dataFim)).fetch();
    }

    @Override
    public Optional<IndiceEconomicoVar> findLastIndiceByIndice(Long id) {
        final QIndiceEconomicoVar qIndiceEconomicoVar = QIndiceEconomicoVar.indiceEconomicoVar;
        final QIndiceEconomico qIndiceEconomico = QIndiceEconomico.indiceEconomico;

        return Optional.ofNullable(
                new JPAQueryFactory(entityManager)
                .select(qIndiceEconomicoVar)
                .from(qIndiceEconomicoVar)
                .join(qIndiceEconomicoVar.indiceEconomico, qIndiceEconomico).fetchJoin()
                .where(qIndiceEconomico.id.eq(id))
                .orderBy(qIndiceEconomicoVar.dataFinal.desc())
                .fetchFirst());
    }

    @Override
    public Optional<IndiceEconomicoVar> findByIndiceAndDataInicio(IndiceEconomico indiceEconomico, Calendar dataInicio) {
        final QIndiceEconomicoVar qIndiceEconomicoVar = QIndiceEconomicoVar.indiceEconomicoVar;
        final QIndiceEconomico qIndiceEconomico = QIndiceEconomico.indiceEconomico;


        return Optional.ofNullable(
                new JPAQueryFactory(entityManager)
                        .select(qIndiceEconomicoVar)
                        .from(qIndiceEconomicoVar)
                        .join(qIndiceEconomicoVar.indiceEconomico, qIndiceEconomico).fetchJoin()
                        .where(qIndiceEconomico.eq(indiceEconomico))
                        .where(qIndiceEconomicoVar.dataInicial.eq(dataInicio))
                        .fetchFirst());
    }

    @Override
    public BigDecimal getFatorSumIndiceEntreDatas(Calendar dataInicio, Calendar dataFim, IndiceEconomico indice) {
        final QIndiceEconomicoVar qIndiceEconomicoVar = QIndiceEconomicoVar.indiceEconomicoVar;


        return new JPAQueryFactory(entityManager)
                .select(qIndiceEconomicoVar.valor.sum())
                .from(qIndiceEconomicoVar)
                .where(qIndiceEconomicoVar.indiceEconomico.eq(indice))
                .where(qIndiceEconomicoVar.dataInicial.between(dataInicio, dataFim))
                .fetchOne();
    }

    @Override
    public List<BigDecimal> findMonthlyValuesByIndiceBetweenDates(Calendar dataInicio, Calendar dataFim, IndiceEconomico indiceEconomico) {
        final QIndiceEconomicoVar qIndiceEconomicoVar = QIndiceEconomicoVar.indiceEconomicoVar;


        return new JPAQueryFactory(entityManager)
                .select(qIndiceEconomicoVar.valor)
                .from(qIndiceEconomicoVar)
                .where(qIndiceEconomicoVar.indiceEconomico.eq(indiceEconomico))
                .where(qIndiceEconomicoVar.dataInicial.between(dataInicio, dataFim))
                .fetch();
    }

    @Override
    public long count(Query<IndiceEconomicoVar> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<IndiceEconomicoVar> createQuery(Query<IndiceEconomicoVar> query) {
        final QIndiceEconomico qIndiceEconomico = QIndiceEconomico.indiceEconomico;
        final QIndiceEconomicoVar qIndiceEconomicoVar = QIndiceEconomicoVar.indiceEconomicoVar;

        final JPAQuery<IndiceEconomicoVar> jpaQuery = new JPAQueryFactory(this.entityManager)
                .select(qIndiceEconomicoVar)
                .from(qIndiceEconomicoVar)
                .join(qIndiceEconomicoVar.indiceEconomico, qIndiceEconomico);

        if (query.getFilter() != null && query.getFilter() instanceof IndiceEconomicoVarFilter) {
            final IndiceEconomicoVarFilter filter = (IndiceEconomicoVarFilter) query.getFilter();

            if (filter.getIndiceEconomico() != null) {
                jpaQuery.where(qIndiceEconomico.eq(filter.getIndiceEconomico()));
            }

            if (filter.getDataInicial() != null) {
                jpaQuery.where(qIndiceEconomicoVar.dataInicial.month().eq(filter.getDataInicial().get(Calendar.MONTH) + 1).and(qIndiceEconomicoVar.dataInicial.year().eq(filter.getDataInicial().get(Calendar.YEAR))));
            }

            if (filter.getDataFinal() != null) {
                jpaQuery.where(qIndiceEconomicoVar.dataFinal.month().eq(filter.getDataFinal().get(Calendar.MONTH) + 1).and(qIndiceEconomicoVar.dataFinal.year().eq(filter.getDataFinal().get(Calendar.YEAR))));
            }

            if (filter.getValor() != null) {
                jpaQuery.where(qIndiceEconomicoVar.valor.eq(filter.getValor()));
            }

        }
        return jpaQuery;
    }

    @Override
    public long countVarByInidice(IndiceEconomico indiceEconomico) {
        final QIndiceEconomicoVar qIndiceEconomicoVar = QIndiceEconomicoVar.indiceEconomicoVar;
        final QIndiceEconomico qIndiceEconomico = QIndiceEconomico.indiceEconomico;

        return new JPAQueryFactory(entityManager)
                .select(qIndiceEconomicoVar)
                .from(qIndiceEconomicoVar)
                .join(qIndiceEconomicoVar.indiceEconomico, qIndiceEconomico)
                .where(qIndiceEconomico.eq(indiceEconomico)).fetchCount();
    }
}
