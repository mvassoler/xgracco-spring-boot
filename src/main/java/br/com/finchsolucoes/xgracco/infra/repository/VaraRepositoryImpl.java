package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.QVara;
import br.com.finchsolucoes.xgracco.domain.entity.Vara;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.VaraFilter;
import br.com.finchsolucoes.xgracco.domain.repository.VaraJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class VaraRepositoryImpl extends AbstractJpaRepository<Vara,Long> implements VaraJpaRepository {

    public List<Vara> find(Query<Vara> query) {
        final JPAQuery<Vara> jpaQuery = createQuery(query);
        final PathBuilder<Vara> entityPath = new PathBuilder<>(Vara.class, "vara");

        if (query.getSorter() != null && StringUtils.isNotBlank(query.getSorter().getProperty())) {

            if (query.getSorter().isDesc()) {
                jpaQuery.orderBy(entityPath.getString(query.getSorter().getProperty()).desc());
            } else {
                jpaQuery.orderBy(entityPath.getString(query.getSorter().getProperty()).asc());
            }
        } else {
            jpaQuery.orderBy(entityPath.getString("id").asc());
        }

        if (Optional.ofNullable(query.getPage()).orElse(0L) > 0L) {
            jpaQuery.offset((query.getPage() - 1L) * query.getLimit());
        }

        jpaQuery.limit(query.getLimit());

        return jpaQuery.fetch();
    }


    public List<Vara> findAllCache() {

        final QVara qVara = QVara.vara;

        final JPAQuery<Vara> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qVara)
                .from(qVara)
                .join(qVara.instancias).fetchJoin();

        return jpaQuery.fetch();
    }

    @Override
    public Optional<Vara> findByDescricao(String descricao) {
        final QVara qVara = QVara.vara;

        return Optional.ofNullable(
                new JPAQueryFactory(entityManager)
                        .select(qVara)
                        .from(qVara)
                        .where(qVara.descricao.likeIgnoreCase("%"+descricao+"%")).fetchFirst());
    }



    public long count(Query<Vara> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<Vara> createQuery(Query<Vara> query) {
        final QVara qVara = QVara.vara;

        final JPAQuery<Vara> jpaQuery = new JPAQueryFactory(this.entityManager)
                .select(qVara)
                .from(qVara);

        if (query.getFilter() != null && query.getFilter() instanceof VaraFilter) {
            final VaraFilter varaFilter = (VaraFilter) query.getFilter();

            if (StringUtils.isNotBlank(varaFilter.getDescricao())) {
                jpaQuery.where(qVara.descricao.likeIgnoreCase("%" + varaFilter.getDescricao() + "%"));
            }

            if (varaFilter.getTipoJustica() != null) {
                jpaQuery.where(qVara.tiposJustica.any().eq(varaFilter.getTipoJustica()));
            }

            if (varaFilter.getInstancia() != null) {
                jpaQuery.where(qVara.instancias.any().eq(varaFilter.getInstancia()));
            }
        }

        return jpaQuery;
    }
}
