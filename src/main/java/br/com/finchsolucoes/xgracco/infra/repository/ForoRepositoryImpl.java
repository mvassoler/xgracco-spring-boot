package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Foro;
import br.com.finchsolucoes.xgracco.domain.entity.QComarca;
import br.com.finchsolucoes.xgracco.domain.entity.QForo;
import br.com.finchsolucoes.xgracco.domain.entity.QUf;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.ForoFilter;
import br.com.finchsolucoes.xgracco.domain.repository.ForoJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by paulomarcon
 */
@Repository
public class ForoRepositoryImpl extends AbstractJpaRepository<Foro, Long> implements ForoJpaRepository {

    @Override
    public List<Foro> find(Query<Foro> query) {
        final JPAQuery<Foro> jpaQuery = createQuery(query);
        final PathBuilder<Foro> entityPath = new PathBuilder<>(Foro.class, "foro");

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

    @Override
    public List<Foro> findAllCache() {

        final QForo qForo = QForo.foro;

        final JPAQuery<Foro> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qForo)
                .from(qForo)
                .join(qForo.comarca).fetchJoin();

        return jpaQuery.fetch();
    }


    @Override
    public long count(Query<Foro> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<Foro> createQuery(Query<Foro> query) {
        final QForo qForo = QForo.foro;
        final QComarca qComarca = QComarca.comarca;
        final QUf qUf = QUf.uf;

        final JPAQuery<Foro> jpaQuery = new JPAQueryFactory(this.entityManager)
                .select(qForo)
                .from(qForo)
                .leftJoin(qForo.comarca, qComarca).fetchJoin()
                .leftJoin(qComarca.uf, qUf).fetchJoin();

        if (query.getFilter() != null && query.getFilter() instanceof ForoFilter) {
            final ForoFilter foroFilter = (ForoFilter) query.getFilter();

            if (StringUtils.isNotBlank(foroFilter.getDescricao())) {
                jpaQuery.where(qForo.descricao.likeIgnoreCase("%" + foroFilter.getDescricao() + "%"));
            }

            if (foroFilter.getInstancia() != null) {
                jpaQuery.where(qForo.instancias.any().eq(foroFilter.getInstancia()));
            }

            if (foroFilter.getTipoJustica() != null) {
                jpaQuery.where(qForo.tiposJustica.any().eq(foroFilter.getTipoJustica()));
            }

            if (foroFilter.getComarca() != null) {
                jpaQuery.where(qForo.comarca.eq(foroFilter.getComarca()));
            }
        }
        return jpaQuery;
    }
}
