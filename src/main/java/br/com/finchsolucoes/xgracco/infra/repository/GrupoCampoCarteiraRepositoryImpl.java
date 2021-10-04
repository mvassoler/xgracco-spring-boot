package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.core.handler.exception.IdNullException;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.GrupoCampoCarteiraFilter;
import br.com.finchsolucoes.xgracco.domain.repository.GrupoCampoCarteiraJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Jordano on 23/02/2016.
 */
@Repository
public class GrupoCampoCarteiraRepositoryImpl extends AbstractJpaRepository<GrupoCampoCarteira, Long> implements GrupoCampoCarteiraJpaRepository {

    @Override
    public List<GrupoCampoCarteira> find(Query<GrupoCampoCarteira> query) {
        final JPAQuery<GrupoCampoCarteira> jpaQuery = createQuery(query);

        this.applyPagination(jpaQuery, query.getPage(), query.getLimit());

        this.applySorter(jpaQuery, query.getSorter());

        return jpaQuery.fetch();

    }

    @Override
    public long count(Query<GrupoCampoCarteira> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<GrupoCampoCarteira> createQuery(Query<GrupoCampoCarteira> query) {
        final QGrupoCampoCarteira qGrupoCampoCarteira = QGrupoCampoCarteira.grupoCampoCarteira;
        final QGrupoCampo qGrupoCampo = QGrupoCampo.grupoCampo;
        final PathBuilder<GrupoCampo> builder = new PathBuilder<>(GrupoCampo.class, "grupoCampo");
        final QCarteira qCarteira = QCarteira.carteira;

        final JPAQuery<GrupoCampoCarteira> jpaQuery = new JPAQueryFactory(this.entityManager)
                .select(qGrupoCampoCarteira)
                .from(qGrupoCampoCarteira)
                .join(qGrupoCampoCarteira.grupoCampo, qGrupoCampo).fetchJoin()
                .join(qGrupoCampoCarteira.carteira, qCarteira).fetchJoin();


        if (query.getFilter() != null && query.getFilter() instanceof GrupoCampoCarteiraFilter) {
            final GrupoCampoCarteiraFilter grupoCampoCarteiraFilter = (GrupoCampoCarteiraFilter) query.getFilter();


            if (grupoCampoCarteiraFilter.getCarteira() != null) {
                jpaQuery.where(qGrupoCampoCarteira.carteira.eq(grupoCampoCarteiraFilter.getCarteira()));

            }

            if (grupoCampoCarteiraFilter.getGrupoCampo() != null) {
                jpaQuery.where(qGrupoCampoCarteira.grupoCampo.eq(grupoCampoCarteiraFilter.getGrupoCampo()));
            }

        }

        jpaQuery.orderBy(builder.getString("descricao").asc());

        return jpaQuery;
    }

    @Override
    public List<GrupoCampoCarteira> findAll(Query<GrupoCampoCarteira> query) {
        return createQuery(query).fetch();

    }

    @Override
    public void create(GrupoCampoCarteira grupoCampoCarteira) {
        if (!Optional.ofNullable(grupoCampoCarteira).map(GrupoCampoCarteira::getCarteira).map(Carteira::getId).isPresent()
                || !Optional.ofNullable(grupoCampoCarteira).map(GrupoCampoCarteira::getGrupoCampo).map(GrupoCampo::getId).isPresent()) {
            throw new IdNullException();
        }
        grupoCampoCarteira.setCarteira(entityManager.find(Carteira.class, grupoCampoCarteira.getCarteira().getId()));
        grupoCampoCarteira.setGrupoCampo(entityManager.find(GrupoCampo.class, grupoCampoCarteira.getGrupoCampo().getId()));
        entityManager.persist(grupoCampoCarteira);
        entityManager.flush();
    }
}
