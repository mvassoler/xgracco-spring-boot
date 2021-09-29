package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Feriado;
import br.com.finchsolucoes.xgracco.domain.entity.QFeriado;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.FeriadoFilter;
import br.com.finchsolucoes.xgracco.domain.repository.FeriadoJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade TipoDocumento.
 *
 * @author Jordano
 * @since 2.1
 */
@Repository
public class FeriadoRepositoryImpl extends AbstractJpaRepository<Feriado, Long> implements  FeriadoJpaRepository {
    @Override
    public List<Feriado> find(Query<Feriado> query) {
        final PathBuilder<Feriado> path = new PathBuilder<>(Feriado.class, "feriado");
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

    @Override
    public long count(Query<Feriado> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<Feriado> createQuery(Query<Feriado> query) {
        QFeriado qFeriado = QFeriado.feriado;

        final JPAQuery<Feriado> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qFeriado)
                .from(qFeriado);

        // filter
        if (query.getFilter() != null && query.getFilter() instanceof FeriadoFilter) {
            final FeriadoFilter feriadoFilter = (FeriadoFilter) query.getFilter();

            // descricao
            if (StringUtils.isNotEmpty(feriadoFilter.getDescricao())) {
                jpaQuery.where(qFeriado.descricao.likeIgnoreCase("%" + feriadoFilter.getDescricao() + "%"));
            }

            if (feriadoFilter.getDia() != null) {
                jpaQuery.where(qFeriado.dia.eq(feriadoFilter.getDia()));
            }

            if (feriadoFilter.getMes() != null) {
                jpaQuery.where(qFeriado.mes.eq(feriadoFilter.getMes()));
            }

            if (feriadoFilter.getAno() != null) {
                jpaQuery.where(qFeriado.ano.eq(feriadoFilter.getAno()));
            }

            if (Objects.nonNull(feriadoFilter.getComarca())) {
                jpaQuery.where(qFeriado.comarca.eq(feriadoFilter.getComarca()));
            }

            if (Objects.nonNull(feriadoFilter.getUf())) {
                jpaQuery.where(qFeriado.uf.eq(feriadoFilter.getUf()));
            }

            if (Objects.nonNull(feriadoFilter.getArea())) {
                jpaQuery.where(qFeriado.area.eq(feriadoFilter.getArea()));
            }
        }

        return jpaQuery;
    }


}
