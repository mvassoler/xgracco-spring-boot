package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.QTipoGarantia;
import br.com.finchsolucoes.xgracco.domain.entity.TipoGarantia;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.TipoGarantiaFilter;
import br.com.finchsolucoes.xgracco.domain.repository.TipoGarantiaJpaRepository;
import br.com.finchsolucoes.xgracco.domain.repository.TipoGarantiaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade Tipo Pedido.
 *
 * @author Jordano
 * @since 2.1
 */
@Repository
public class TipoGarantiaRepositoryImpl extends AbstractJpaRepository<TipoGarantia, Long> implements TipoGarantiaJpaRepository {


    public List<TipoGarantia> find(Query<TipoGarantia> query) {
        final PathBuilder<TipoGarantia> path = new PathBuilder<>(TipoGarantia.class, "tipoGarantia");
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


    public long count(Query<TipoGarantia> query) {
        return createQuery(query).fetchCount();
    }


    private JPAQuery<TipoGarantia> createQuery(Query<TipoGarantia> query) {
        QTipoGarantia qTipoGarantia = QTipoGarantia.tipoGarantia;

        final JPAQuery<TipoGarantia> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qTipoGarantia)
                .from(qTipoGarantia);

        // filter
        if (query.getFilter() != null && query.getFilter() instanceof TipoGarantiaFilter) {
            final TipoGarantiaFilter tipoGarantiaFilter = (TipoGarantiaFilter) query.getFilter();

            // descricao
            if (StringUtils.isNotEmpty(tipoGarantiaFilter.getDescricao())) {
                jpaQuery.where(qTipoGarantia.descricao.likeIgnoreCase("%" + tipoGarantiaFilter.getDescricao() + "%"));
            }

            if (tipoGarantiaFilter.getAtivo() != null) {
                jpaQuery.where(qTipoGarantia.ativo.eq(tipoGarantiaFilter.getAtivo()));
            }

        }

        return jpaQuery;
    }

}
