package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.domain.query.impl.MateriaFilter;
import br.com.finchsolucoes.xgracco.domain.repository.MateriaJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by felipiberdun on 13/01/2017.
 */
@Repository
public class MateriaRepositoryImpl extends AbstractJpaRepository<Materia, Long> implements  MateriaJpaRepository {

    @Override
    public List<Materia> find(Query<Materia> query) {
        JPAQuery<Materia> jpaQuery = createQuery(query);
        PathBuilder<Materia> pathBuilder = new PathBuilder<>(Materia.class, "materia");

        if (query.getSorter() != null && StringUtils.isNotBlank(query.getSorter().getProperty())) {
            if (query.getSorter().getDirection().equals(Sorter.Direction.DESC)) {
                jpaQuery.orderBy(pathBuilder.getString(query.getSorter().getProperty()).desc());
            } else {
                jpaQuery.orderBy(pathBuilder.getString(query.getSorter().getProperty()).asc());
            }
        } else {
            jpaQuery.orderBy(pathBuilder.getString("id").asc());
        }

        if (Optional.ofNullable(query.getPage()).orElse(0L) > 0L) {
            jpaQuery.offset((query.getPage() - 1L) * query.getLimit());
        }

        jpaQuery.limit(query.getLimit());

        return jpaQuery.fetch();
    }

    @Override
    public List<Materia> findAllCache() {

        final QMateria qMateria = QMateria.materia;

        final JPAQuery<Materia> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qMateria)
                .from(qMateria)
                .leftJoin(qMateria.pratica).fetchJoin();

        return jpaQuery.fetch();
    }


    @Override
    public long count(Query<Materia> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<Materia> createQuery(Query<Materia> query) {
        final QMateria qMateria = QMateria.materia;
        final QPratica qPratica = QPratica.pratica;

        JPAQuery<Materia> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qMateria)
                .from(qMateria);

        if (query.getFilter() != null && query.getFilter() instanceof MateriaFilter) {
            final MateriaFilter filter = (MateriaFilter) query.getFilter();

            if (StringUtils.isNotBlank(filter.getDescricao())) {
                jpaQuery.where(qMateria.descricao.likeIgnoreCase("%" + filter.getDescricao() + "%"));
            }

            if (filter.getPratica() != null) {
                jpaQuery.join(qMateria.pratica, qPratica);
                jpaQuery.where(qPratica.eq(filter.getPratica()));
            }
        }

        return jpaQuery;
    }

    @Override
    public List<Processo> findByProcesso(Long id) {
        final QMateria qMateria = QMateria.materia;
        final QProcesso qProcesso = QProcesso.processo1;
        return new JPAQueryFactory(entityManager)
                .select(qProcesso)
                .from(qProcesso)
                .join(qProcesso.materia, qMateria).fetchJoin()
                .where(qMateria.id.eq(id))
                .fetch();

    }
}
