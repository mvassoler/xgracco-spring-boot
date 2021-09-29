package br.com.finchsolucoes.xgracco.infra.repository;


import br.com.finchsolucoes.xgracco.domain.entity.QTipoPedido;
import br.com.finchsolucoes.xgracco.domain.entity.TipoPedido;
import br.com.finchsolucoes.xgracco.domain.enums.EnumArea;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.TipoPedidoFilter;
import br.com.finchsolucoes.xgracco.domain.repository.TipoPedidoJpaRepository;
import br.com.finchsolucoes.xgracco.domain.repository.TipoPedidoRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade Tipo Pedido.
 *
 * @author Jordano
 * @since 2.1
 */
@Repository
public class TipoPedidoRepositoryImpl extends AbstractJpaRepository<TipoPedido, Long> implements TipoPedidoJpaRepository {

    public List<TipoPedido> find(Query<TipoPedido> query) {
        final PathBuilder<TipoPedido> path = new PathBuilder<>(TipoPedido.class, "tipoPedido");
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


    public long count(Query<TipoPedido> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<TipoPedido> createQuery(Query<TipoPedido> query) {
        QTipoPedido qTipoPedido = QTipoPedido.tipoPedido;

        final JPAQuery<TipoPedido> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qTipoPedido)
                .from(qTipoPedido);

        // filter
        if (query.getFilter() != null && query.getFilter() instanceof TipoPedidoFilter) {
            final TipoPedidoFilter tipoPedidoFilter = (TipoPedidoFilter) query.getFilter();

            // descricao
            if (StringUtils.isNotEmpty(tipoPedidoFilter.getDescricao())) {
                jpaQuery.where(qTipoPedido.descricao.likeIgnoreCase("%" + tipoPedidoFilter.getDescricao() + "%"));
            }

            // area
            if (Objects.nonNull(tipoPedidoFilter.getArea())) {
                jpaQuery.where(qTipoPedido.area.eq(tipoPedidoFilter.getArea()));
            }
        }

        return jpaQuery;
    }



    public Optional<TipoPedido> findByDescricaoAndArea(String descricao, EnumArea area) {
        final QTipoPedido qTipoPedido = QTipoPedido.tipoPedido;

        return Optional.ofNullable(
                new JPAQueryFactory(entityManager)
                        .select(qTipoPedido)
                        .from(qTipoPedido)
                        .where(qTipoPedido.area.eq(area))
                        .where(qTipoPedido.descricao.equalsIgnoreCase(descricao)).fetchFirst());

    }
}
