package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.PossibilidadePerda;
import br.com.finchsolucoes.xgracco.domain.entity.QCarteira;
import br.com.finchsolucoes.xgracco.domain.entity.QPossibilidadePerda;
import br.com.finchsolucoes.xgracco.domain.entity.QRiscoCausa;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.PossibilidadePerdaFilter;
import br.com.finchsolucoes.xgracco.domain.repository.PossibilidadePerdaJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade PossibilidadePerda.
 *
 * @author Paulo Marçon
 * @since 2.1
 */
@Repository
public class PossibilidadePerdaRepositoryImpl extends AbstractJpaRepository<PossibilidadePerda, Long> implements PossibilidadePerdaJpaRepository {


    @Override
    public List<PossibilidadePerda> find(Query<PossibilidadePerda> query) {
        final PathBuilder<PossibilidadePerda> path = new PathBuilder<>(PossibilidadePerda.class, "possibilidadePerda");
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

    @Override
    public long count(Query<PossibilidadePerda> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<PossibilidadePerda> createQuery(Query<PossibilidadePerda> query) {
        QPossibilidadePerda qPossibilidadePerda = QPossibilidadePerda.possibilidadePerda;
        QRiscoCausa qRiscoCausa = QRiscoCausa.riscoCausa;
        QCarteira qCarteira = QCarteira.carteira;

        final JPAQuery<PossibilidadePerda> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qPossibilidadePerda)
                .from(qPossibilidadePerda)
                .leftJoin(qPossibilidadePerda.riscoCausa, qRiscoCausa).fetchJoin()
                .leftJoin(qPossibilidadePerda.carteira, qCarteira).fetchJoin();

        // filter
        if (query.getFilter() != null && query.getFilter() instanceof PossibilidadePerdaFilter) {
            final PossibilidadePerdaFilter possibilidadePerdaFilter = (PossibilidadePerdaFilter) query.getFilter();

            // risco causa
            if (possibilidadePerdaFilter.getRiscoCausa() != null) {
                jpaQuery.where(qPossibilidadePerda.riscoCausa.eq(possibilidadePerdaFilter.getRiscoCausa()));
            }

            // carteira
            if (possibilidadePerdaFilter.getCarteira() != null) {
                jpaQuery.where(qPossibilidadePerda.carteira.eq(possibilidadePerdaFilter.getCarteira()));
            }
        }

        return jpaQuery;
    }
}
