package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Posicao;
import br.com.finchsolucoes.xgracco.domain.entity.QPosicao;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.PosicaoFilter;
import br.com.finchsolucoes.xgracco.domain.repository.PosicaoJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade Posicao.
 *
 * @author Jordano
 * @since 2.1
 */
@Repository
public class PosicaoRepositoryImpl extends AbstractJpaRepository<Posicao, Long> implements PosicaoJpaRepository {

    @Override
    public List<Posicao> find(Query<Posicao> query) {
        final PathBuilder<Posicao> path = new PathBuilder<>(Posicao.class, "posicao");
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
        List<Posicao> list = jpaQuery.fetch();
        return list;
    }

    @Override
    public List<Posicao> findAllCache() {

        final QPosicao qPosicao = QPosicao.posicao;

        final JPAQuery<Posicao> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qPosicao)
                .from(qPosicao);

        return jpaQuery.fetch();
    }


    @Override
    public long count(Query<Posicao> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<Posicao> createQuery(Query<Posicao> query) {
        QPosicao qPosicao = QPosicao.posicao;

        final JPAQuery<Posicao> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qPosicao)
                .from(qPosicao);

        // filter
        if (query.getFilter() != null && query.getFilter() instanceof PosicaoFilter) {
            final PosicaoFilter decisaoFilter = (PosicaoFilter) query.getFilter();

            // descricao
            if (StringUtils.isNotEmpty(decisaoFilter.getDescricao())) {
                jpaQuery.where(qPosicao.descricao.likeIgnoreCase("%" + decisaoFilter.getDescricao() + "%"));
            }

            // parte contraria
            if (StringUtils.isNotEmpty(decisaoFilter.getPosicaoContraria())) {
                jpaQuery.where(qPosicao.posicaoContraria.likeIgnoreCase("%" + decisaoFilter.getPosicaoContraria() + "%"));

            }

        }

        return jpaQuery;
    }
}
