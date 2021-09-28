package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.QTipoLogradouro;
import br.com.finchsolucoes.xgracco.domain.entity.TipoLogradouro;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.domain.query.impl.TipoLogradouroFilter;
import br.com.finchsolucoes.xgracco.domain.repository.TipoLogradouroJpaRepository;
import br.com.finchsolucoes.xgracco.domain.repository.TipoLogradouroRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade TipoLogradouro
 *
 * Created by felipiberdun on 19/01/2017.
 */
@Repository
public class TipoLogradouroRepositoryImpl extends AbstractJpaRepository<TipoLogradouro, Long> implements TipoLogradouroJpaRepository {


    public List<TipoLogradouro> find(Query<TipoLogradouro> query) {
        final JPAQuery<TipoLogradouro> jpaQuery = createQuery(query);
        final PathBuilder<TipoLogradouro> builder = new PathBuilder<>(TipoLogradouro.class, "tipoLogradouro");

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


    public long count(Query<TipoLogradouro> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<TipoLogradouro> createQuery(final Query<TipoLogradouro> query) {
        final QTipoLogradouro qTipoLogradouro = QTipoLogradouro.tipoLogradouro;

        final JPAQuery<TipoLogradouro> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qTipoLogradouro)
                .from(qTipoLogradouro);

        if (query.getFilter() != null && query.getFilter() instanceof TipoLogradouroFilter) {
            TipoLogradouroFilter filter = (TipoLogradouroFilter) query.getFilter();

            if (StringUtils.isNotEmpty(filter.getDescricao())) {
                jpaQuery.where(qTipoLogradouro.descricao.likeIgnoreCase("%" + filter.getDescricao() + "%"));
            }
        }

        return jpaQuery;
    }

}
