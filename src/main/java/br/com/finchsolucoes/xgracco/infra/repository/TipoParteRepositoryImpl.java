package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.QTipoParte;
import br.com.finchsolucoes.xgracco.domain.entity.TipoParte;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.TipoParteFilter;
import br.com.finchsolucoes.xgracco.domain.repository.TipoParteJpaRepository;
import br.com.finchsolucoes.xgracco.domain.repository.TipoParteRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade TipoParteRequest.
 *
 * @author Renan Gigliotti
 * @since 2.1
 */
@Repository
public class TipoParteRepositoryImpl extends AbstractJpaRepository<TipoParte, Long> implements TipoParteJpaRepository {


    public List<TipoParte> find(Query<TipoParte> query) {
        final PathBuilder<TipoParte> path = new PathBuilder<>(TipoParte.class, "tipoParte");
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
        if (query.getLimit() != Query.NO_LIMIT) {
            jpaQuery.limit(query.getLimit());
        }

        return jpaQuery.fetch();
    }


    public long count(Query<TipoParte> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<TipoParte> createQuery(Query<TipoParte> query) {
        QTipoParte qTipoParte = QTipoParte.tipoParte;

        final JPAQuery<TipoParte> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qTipoParte)
                .from(qTipoParte);

        // filter
        if (query.getFilter() != null && query.getFilter() instanceof TipoParteFilter) {
            final TipoParteFilter tipoParteFilter = (TipoParteFilter) query.getFilter();

            // descricao
            if (StringUtils.isNotEmpty(tipoParteFilter.getDescricao())) {
                jpaQuery.where(qTipoParte.descricao.likeIgnoreCase("%" + tipoParteFilter.getDescricao() + "%"));
            }
        }

        return jpaQuery;
    }


    public List<TipoParte> findAll() {
        QTipoParte qTipoParte = QTipoParte.tipoParte;

        final JPAQuery<TipoParte> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qTipoParte)
                .from(qTipoParte);

        return jpaQuery.fetch();
    }
}
