package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Juros;
import br.com.finchsolucoes.xgracco.domain.entity.QJuros;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.JurosFilter;
import br.com.finchsolucoes.xgracco.domain.repository.JurosJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by jordano on 10/01/17.
 */
@Repository
public class JurosRepositoryImpl extends AbstractJpaRepository<Juros, Long> implements JurosJpaRepository {
    @Override
    public List<Juros> find(Query<Juros> query) {
        final PathBuilder<Juros> path = new PathBuilder<>(Juros.class, "juros");
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
        List<Juros> list = jpaQuery.fetch();
        return list;
    }

    @Override
    public long count(Query<Juros> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<Juros> createQuery(Query<Juros> query) {
        QJuros qJuros = QJuros.juros;

        final JPAQuery<Juros> jpaQuery = new JPAQueryFactory(entityManager)
                .selectDistinct(QJuros.create(
                        qJuros.id,
                        qJuros.descricao,
                        qJuros.percentual,
                        qJuros.tipoJuros,
                        qJuros.periodoJurosCorrecao
                ))
                .from(qJuros);

        // filter
        if (query.getFilter() != null && query.getFilter() instanceof JurosFilter) {
            final JurosFilter jurosFilter = (JurosFilter) query.getFilter();

            // descricao
            if (StringUtils.isNotEmpty(jurosFilter.getDescricao())) {
                jpaQuery.where(qJuros.descricao.likeIgnoreCase("%" + jurosFilter.getDescricao() + "%"));
            }

            // periodo
            if (Objects.nonNull(jurosFilter.getPeriodicidadeJurosCorrecao())) {
                jpaQuery.where(qJuros.periodoJurosCorrecao.eq(jurosFilter.getPeriodicidadeJurosCorrecao()));
            }

            // tipo
            if (Objects.nonNull(jurosFilter.getTipoJuros())) {
                jpaQuery.where(qJuros.tipoJuros.eq(jurosFilter.getTipoJuros()));
            }
        }

        return jpaQuery;
    }
}
