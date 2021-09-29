package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.QRiscoCausa;
import br.com.finchsolucoes.xgracco.domain.entity.RiscoCausa;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.RiscoCausaFilter;
import br.com.finchsolucoes.xgracco.domain.repository.RiscoCausaJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade RiscoCausa
 *
 * @author Paulo Marçon
 * @since 2.1
 */
@Repository
public class RiscoCausaRepositoryImpl extends AbstractJpaRepository<RiscoCausa, Long> implements RiscoCausaJpaRepository {


    public List<RiscoCausa> find(Query<RiscoCausa> query) {
        final PathBuilder<RiscoCausa> path = new PathBuilder<>(RiscoCausa.class, "riscoCausa");
        final JPAQuery jpaQuery = createQuery(query);

        if (query.getSorter() != null && StringUtils.isNotBlank(query.getSorter().getProperty())) {
            if (query.getSorter().isDesc()) {
                jpaQuery.orderBy(path.getString(query.getSorter().getProperty()).desc());
            } else {
                jpaQuery.orderBy(path.getString(query.getSorter().getProperty()).asc());
            }
        } else {
            jpaQuery.orderBy(path.getString("id").asc());
        }

        if (Optional.ofNullable(query.getPage()).orElse(0L) > 0L) {
            jpaQuery.offset(((query.getPage() - 1L) * query.getLimit()));
        }

        jpaQuery.limit(query.getLimit());

        return jpaQuery.fetch();
    }


    public long count(Query<RiscoCausa> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<RiscoCausa> createQuery(Query<RiscoCausa> query) {
        QRiscoCausa qRiscoCausa = QRiscoCausa.riscoCausa;

        final JPAQuery<RiscoCausa> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qRiscoCausa)
                .from(qRiscoCausa);

        if (query.getFilter() != null && query.getFilter() instanceof RiscoCausaFilter) {
            final RiscoCausaFilter riscoCausaFilter = (RiscoCausaFilter) query.getFilter();

            if (StringUtils.isNotBlank(riscoCausaFilter.getDescricao())) {
                jpaQuery.where(qRiscoCausa.descricao.likeIgnoreCase("%" + riscoCausaFilter.getDescricao() + "%"));
            }
        }
        return jpaQuery;
    }

    @Override
    public Optional<RiscoCausa> findByDescricao(String descricao) {
        final QRiscoCausa qRiscoCausa = QRiscoCausa.riscoCausa;

        return Optional.ofNullable(
                new JPAQueryFactory(entityManager)
                .select(qRiscoCausa)
                .from(qRiscoCausa)
                .where(qRiscoCausa.descricao.equalsIgnoreCase(descricao)).fetchFirst()
        );
    }
}
