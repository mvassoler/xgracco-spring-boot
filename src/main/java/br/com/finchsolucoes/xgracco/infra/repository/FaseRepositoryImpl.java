package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Fase;
import br.com.finchsolucoes.xgracco.domain.entity.QFase;
import br.com.finchsolucoes.xgracco.domain.entity.QPratica;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.domain.query.impl.FaseFilter;
import br.com.finchsolucoes.xgracco.domain.repository.FaseJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade Fase
 *
 * @author felipiberdun
 * @since 2.1
 */
@Repository
public class FaseRepositoryImpl extends AbstractJpaRepository<Fase, Long> implements FaseJpaRepository {

    @Override
    public List<Fase> find(Query<Fase> query) {
        final JPAQuery<Fase> jpaQuery = createQuery(query);
        final PathBuilder<Fase> builder = new PathBuilder<>(Fase.class, "fase");

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
    public long count(Query<Fase> query) {
        final JPAQuery<Fase> jpaQuery = createQuery(query);
        return jpaQuery.fetchCount();
    }

    private JPAQuery<Fase> createQuery(Query<Fase> query) {
        final QFase qFase = QFase.fase;
        final QPratica qPratica = QPratica.pratica;

        JPAQuery<Fase> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qFase)
                .from(qFase);

        if (query.getFilter() != null && query.getFilter() instanceof FaseFilter) {
            FaseFilter filter = (FaseFilter) query.getFilter();

            if (StringUtils.isNotBlank(filter.getDescricao())) {
                jpaQuery.where(qFase.descricao.likeIgnoreCase("%" + filter.getDescricao() + "%"));
            }

            if (filter.getTipoProcesso() != null) {
                jpaQuery.where(qFase.tipoProcesso.eq(filter.getTipoProcesso()));
            }

            if (filter.getPratica() != null) {
                jpaQuery.join(qFase.praticas, qPratica);
                jpaQuery.where(qPratica.eq(filter.getPratica()));
            }
        }

        return jpaQuery;
    }

}
