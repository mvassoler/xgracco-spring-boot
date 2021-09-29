package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.domain.query.impl.ReparticaoFilter;
import br.com.finchsolucoes.xgracco.domain.repository.ReparticaoJpaRepository;
import br.com.finchsolucoes.xgracco.domain.repository.ReparticaoRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by felipiberdun on 17/01/2017.
 */
@Repository
public class ReparticaoRepositoryImpl extends AbstractJpaRepository<Reparticao, Long> implements ReparticaoJpaRepository {


    public List<Reparticao> find(final Query<Reparticao> query) {
        final JPAQuery<Reparticao> jpaQuery = createQuery(query);
        final PathBuilder<Reparticao> builder = new PathBuilder<>(Reparticao.class, "reparticao");

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

    public List<Reparticao> findAllCache() {

        final QReparticao qReparticao = QReparticao.reparticao;

        final JPAQuery<Reparticao> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qReparticao)
                .from(qReparticao)
                .join(qReparticao.comarca).fetchJoin()
                .join(qReparticao.praticas).fetchJoin();

        return jpaQuery.fetch();
    }



    public long count(final Query<Reparticao> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<Reparticao> createQuery(final Query<Reparticao> query) {
        final QReparticao qReparticao = QReparticao.reparticao;
        final QPratica qPratica = QPratica.pratica;
        final QComarca qComarca = QComarca.comarca;
        final QUf qUf = QUf.uf;

        JPAQuery<Reparticao> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qReparticao)
                .from(qReparticao)
                .leftJoin(qReparticao.comarca, qComarca).fetchJoin()
                .leftJoin(qComarca.uf, qUf).fetchJoin();

        if (query.getFilter() != null && query.getFilter() instanceof ReparticaoFilter) {
            ReparticaoFilter filter = (ReparticaoFilter) query.getFilter();

            if (StringUtils.isNotBlank(filter.getDescricao())) {
                jpaQuery.where(qReparticao.descricao.likeIgnoreCase("%" + filter.getDescricao() + "%"));
            }

            if (filter.getArea() != null) {
                jpaQuery.where(qReparticao.areas.any().eq(filter.getArea()));
            }

            if (filter.getPratica() != null) {
                jpaQuery.join(qReparticao.praticas, qPratica);
                jpaQuery.where(qPratica.eq(filter.getPratica()));
            }

            if (filter.getComarca() != null) {
                jpaQuery.where(qComarca.eq(filter.getComarca()));
            }
        }

        return jpaQuery;
    }

}
