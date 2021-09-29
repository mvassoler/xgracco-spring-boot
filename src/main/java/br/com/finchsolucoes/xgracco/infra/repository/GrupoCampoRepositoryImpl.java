package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.GrupoCampo;
import br.com.finchsolucoes.xgracco.domain.entity.QCampo;
import br.com.finchsolucoes.xgracco.domain.entity.QGrupoCampo;
import br.com.finchsolucoes.xgracco.domain.entity.QGrupoCampoCarteira;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.GrupoCampoFilter;
import br.com.finchsolucoes.xgracco.domain.repository.GrupoCampoJpaRepository;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by felipiberdun on 29/12/2016.
 */
@Repository
public class GrupoCampoRepositoryImpl extends AbstractJpaRepository<GrupoCampo, Long> implements GrupoCampoJpaRepository {

    @Override
    public List<GrupoCampo> find(Query<GrupoCampo> query) {

        final JPAQuery<GrupoCampo> jpaQuery = createQuery(query);

        this.applyPagination(jpaQuery, query.getPage(), query.getLimit());

        this.applySorter(jpaQuery, query.getSorter());

        return jpaQuery.fetch();

    }

    @Override
    public long count(Query<GrupoCampo> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<GrupoCampo> createQuery(Query<GrupoCampo> query) {
        final QGrupoCampo qGrupoCampo = QGrupoCampo.grupoCampo;
        final QCampo qCampo = QCampo.campo;
        final QGrupoCampoCarteira qGrupoCampoCarteira = QGrupoCampoCarteira.grupoCampoCarteira;

        final JPAQuery<GrupoCampo> jpaQuery = new JPAQueryFactory(this.entityManager)
                .selectDistinct(
                        QGrupoCampo.create(
                                qGrupoCampo.id,
                                qGrupoCampo.descricao
                        )
                )
                .from(qGrupoCampo)
                .leftJoin(qGrupoCampo.campos, qCampo)
                .leftJoin(qGrupoCampo.grupoCampoCarteiras, qGrupoCampoCarteira);

        if (query.getFilter() != null && query.getFilter() instanceof GrupoCampoFilter) {
            final GrupoCampoFilter grupoCampoFilter = (GrupoCampoFilter) query.getFilter();

            if (StringUtils.isNotBlank(grupoCampoFilter.getDescricaoGrupo())) {
                jpaQuery.where(qGrupoCampo.descricao.likeIgnoreCase("%"+grupoCampoFilter.getDescricaoGrupo()+"%"));
            }

            if (StringUtils.isNotBlank(grupoCampoFilter.getDescricaoCampo())) {
                jpaQuery.where(qCampo.descricao.eq(grupoCampoFilter.getDescricaoCampo()));
            }

            if (grupoCampoFilter.getCarteiras() != null && grupoCampoFilter.getCarteiras().size() > 0) {
                jpaQuery.where(qGrupoCampoCarteira.carteira.in(grupoCampoFilter.getCarteiras()));
            }

            if (grupoCampoFilter.getTipoCampo() != null) {
                jpaQuery.where(qCampo.tipoCampo.eq(grupoCampoFilter.getTipoCampo()));
            }
        }

        return jpaQuery;
    }

    @Override
    public List<GrupoCampo> findAll() {
        final QGrupoCampo qGrupoCampo = QGrupoCampo.grupoCampo;

        final JPAQuery<GrupoCampo> jpaQuery = new JPAQueryFactory(this.entityManager)
                .selectDistinct(qGrupoCampo)
                .from(qGrupoCampo);

        return jpaQuery.fetch();
    }
}
