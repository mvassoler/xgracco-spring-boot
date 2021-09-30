package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Decisao;
import br.com.finchsolucoes.xgracco.domain.entity.QDecisao;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.DecisaoFilter;
import br.com.finchsolucoes.xgracco.domain.repository.DecisaoJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade Decisao.
 *
 * @author Jordano
 * @since 2.1
 */
@Repository
public class DecisaoRepositoryImpl extends AbstractJpaRepository<Decisao, Long> implements DecisaoJpaRepository {
    @Override
    public List<Decisao> find(Query<Decisao> query) {
        final PathBuilder<Decisao> path = new PathBuilder<>(Decisao.class, "decisao");
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
        List<Decisao> list = jpaQuery.fetch();
        return list;
    }

    @Override
    public long count(Query<Decisao> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<Decisao> createQuery(Query<Decisao> query) {
        QDecisao qDecisao = QDecisao.decisao;

        final JPAQuery<Decisao> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qDecisao)
                .from(qDecisao);


        // filter
        if (query.getFilter() != null && query.getFilter() instanceof DecisaoFilter) {
            final DecisaoFilter decisaoFilter = (DecisaoFilter) query.getFilter();

            // descricao
            if (StringUtils.isNotEmpty(decisaoFilter.getDescricao())) {
                jpaQuery.where(qDecisao.descricao.likeIgnoreCase("%" + decisaoFilter.getDescricao() + "%"));
            }

            // polo ativo
            if (decisaoFilter.getPoloAtivo() != null) {
                jpaQuery.where(qDecisao.decisaoPoloAtivo.eq(decisaoFilter.getPoloAtivo()));
            }

            // polo passivo
            if (decisaoFilter.getPoloPassivo() != null) {
                jpaQuery.where(qDecisao.decisaoPoloPassivo.eq(decisaoFilter.getPoloPassivo()));
            }

            if (decisaoFilter.getInstacia() != null) {
                jpaQuery.where(qDecisao.instancias.contains(decisaoFilter.getInstacia()));
            }
        }

        return jpaQuery;
    }

    @Override
    public List<Decisao> findbyDescricao(String decisao) {
        QDecisao qDecisao = QDecisao.decisao;

        return new JPAQueryFactory(entityManager)
                .select(qDecisao)
                .from(qDecisao)
                .where(qDecisao.descricao.eq(decisao))
                .fetch();
    }

    @Override
    public List<Decisao> findAllCache() {
        QDecisao qDecisao = QDecisao.decisao;

        return new JPAQueryFactory(this.entityManager)
                .select(qDecisao)
                .from(qDecisao)
                .fetch();
    }
}
