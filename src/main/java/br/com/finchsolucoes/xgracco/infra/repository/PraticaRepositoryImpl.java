package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Pratica;
import br.com.finchsolucoes.xgracco.domain.entity.QPratica;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.domain.query.impl.PraticaFilter;
import br.com.finchsolucoes.xgracco.domain.repository.PraticaJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by felipiberdun on 16/01/2017.
 */
@Repository
public class PraticaRepositoryImpl extends AbstractJpaRepository<Pratica, Long> implements PraticaJpaRepository {

    @Override
    public List<Pratica> find(Query<Pratica> query) {
        final JPAQuery<Pratica> jpaQuery = createQuery(query);
        final PathBuilder<Pratica> builder = new PathBuilder<>(Pratica.class, "pratica");

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
    public List<Pratica> findAllCache() {

        final QPratica qPratica = QPratica.pratica;
        final JPAQuery<Pratica> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qPratica)
                .from(qPratica)
                .join(qPratica.tipoProcesso).fetchJoin();

        return jpaQuery.fetch();
    }

    @Override
    public long count(Query<Pratica> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<Pratica> createQuery(Query<Pratica> query) {
        final QPratica qPratica = QPratica.pratica;

        final JPAQuery<Pratica> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qPratica)
                .from(qPratica);

        if (query.getFilter() != null && query.getFilter() instanceof PraticaFilter) {
            PraticaFilter filter = (PraticaFilter) query.getFilter();

            if (StringUtils.isNotBlank(filter.getDescricao())) {
                jpaQuery.where(qPratica.descricao.likeIgnoreCase("%" + filter.getDescricao() + "%"));
            }

            if (filter.getTipoProcesso() != null) {
                jpaQuery.where(qPratica.tipoProcesso.any().eq(filter.getTipoProcesso()));
            }

            if (filter.getArea() != null) {
                jpaQuery.where(qPratica.area.eq(filter.getArea()));
            }
        }

        return jpaQuery;
    }
}
