package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.IndiceEconomico;
import br.com.finchsolucoes.xgracco.domain.entity.PedidoIndice;
import br.com.finchsolucoes.xgracco.domain.entity.QIndiceEconomico;
import br.com.finchsolucoes.xgracco.domain.entity.QPedidoIndice;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoIndiceEconomico;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.domain.query.impl.IndiceEconomicoFilter;
import br.com.finchsolucoes.xgracco.domain.repository.IndiceEconomicoJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementação JPA do Repository da entidade IndiceEconomico
 *
 * @author Paulo Marçon
 * @since 2.1
 */
@Repository
public class IndiceEconomicoRepositoryImpl extends AbstractJpaRepository<IndiceEconomico, Long> implements IndiceEconomicoJpaRepository {

    @Override
    public List<IndiceEconomico> find(Query<IndiceEconomico> query) {
        final JPAQuery<IndiceEconomico> jpaQuery = createQuery(query);
        final PathBuilder<IndiceEconomico> builder = new PathBuilder<>(IndiceEconomico.class, "indiceEconomico");

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
    public long count(Query<IndiceEconomico> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<IndiceEconomico> createQuery(Query<IndiceEconomico> query) {
        final QIndiceEconomico qIndiceEconomico = QIndiceEconomico.indiceEconomico;

        final JPAQuery<IndiceEconomico> jpaQuery = new JPAQueryFactory(this.entityManager)
                .select(qIndiceEconomico)
                .from(qIndiceEconomico);

        if (query.getFilter() != null && query.getFilter() instanceof IndiceEconomicoFilter) {
            final IndiceEconomicoFilter indiceFilter = (IndiceEconomicoFilter) query.getFilter();

            if (StringUtils.isNotBlank(indiceFilter.getDescricao())) {
                jpaQuery.where(qIndiceEconomico.descricao.likeIgnoreCase("%" + indiceFilter.getDescricao() + "%"));
            }

            if (indiceFilter.getPeriodoJurosCorrecao() != null) {
                jpaQuery.where(qIndiceEconomico.periodoJurosCorrecao.eq(indiceFilter.getPeriodoJurosCorrecao()));
            }

            if (indiceFilter.getTipoIndiceEconomico() != null) {
                jpaQuery.where(qIndiceEconomico.tipoIndiceEconomico.in(indiceFilter.getTipoIndiceEconomico()));
            }
        }
        return jpaQuery;
    }

    @Override
    public void update(IndiceEconomico entity) {
        entityManager.merge(entity);
        entityManager.flush();
    }

    @Override
    public List<PedidoIndice> validarIndice(Long id) {
        final QIndiceEconomico qIndiceEconomico  = QIndiceEconomico.indiceEconomico;
        final QPedidoIndice qPedidoIndice = QPedidoIndice.pedidoIndice;

        return new JPAQueryFactory(entityManager)
                .select(qPedidoIndice)
                .from(qPedidoIndice)
                .join(qPedidoIndice.indiceEconomico, qIndiceEconomico).fetchJoin()
                .where(qIndiceEconomico.id.eq(id))
                .fetch();
    }

    @Override
    public Long validaTipoIndiceExistente(EnumTipoIndiceEconomico tipoIndiceEconomico, Long idIndice) {
        final QIndiceEconomico qIndiceEconomico = QIndiceEconomico.indiceEconomico;

        JPAQuery<IndiceEconomico> query = new JPAQueryFactory(entityManager)
                .select(qIndiceEconomico)
                .from(qIndiceEconomico)
                .where(qIndiceEconomico.tipoIndiceEconomico.eq(tipoIndiceEconomico));

        if(Objects.nonNull(idIndice)){
            query.where(qIndiceEconomico.id.ne(idIndice));
        }

        return query.fetchCount();
    }

    @Override
    public Optional<IndiceEconomico> findByTipoIndiceAndDescricao(String descricao, EnumTipoIndiceEconomico tipoIndiceEconomico) {
        final QIndiceEconomico qIndiceEconomico = QIndiceEconomico.indiceEconomico;

        return Optional.ofNullable(
                new JPAQueryFactory(entityManager)
                .select(qIndiceEconomico)
                .from(qIndiceEconomico)
                .where(qIndiceEconomico.tipoIndiceEconomico.eq(tipoIndiceEconomico))
                .where(qIndiceEconomico.descricao.equalsIgnoreCase(descricao))
                .fetchFirst());
    }

    @Override
    public Optional<IndiceEconomico> findByDescricao(String descricao) {
        final QIndiceEconomico qIndiceEconomico = QIndiceEconomico.indiceEconomico;

        return Optional.ofNullable(
                new JPAQueryFactory(entityManager)
                .select(qIndiceEconomico)
                .from(qIndiceEconomico)
                .where(qIndiceEconomico.descricao.equalsIgnoreCase(descricao)).fetchFirst());
    }

}
