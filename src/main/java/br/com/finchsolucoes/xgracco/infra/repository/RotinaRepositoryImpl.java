package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.QRotina;
import br.com.finchsolucoes.xgracco.domain.entity.Rotina;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.RotinaFilter;
import br.com.finchsolucoes.xgracco.domain.repository.RotinaJpaRepository;
import br.com.finchsolucoes.xgracco.domain.repository.RotinaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade Rotina.
 *
 * @author Renan Gigliotti
 * @since 2.1
 */
@Repository
public class RotinaRepositoryImpl extends AbstractJpaRepository<Rotina, Long> implements RotinaJpaRepository {

    /**
     * Consulta uma entidade através do ID.
     *
     * @param id
     * @return
     */

    public Optional<Rotina> findById(Long id) {
        Query<Rotina> query = Query.<Rotina>builder().filter(new RotinaFilter(id, null, null)).build();

        final JPAQuery jpaQuery = createQuery(query);

        return Optional.ofNullable((Rotina) jpaQuery.fetchOne());
    }


    public List<Rotina> find(Query<Rotina> query) {
        final PathBuilder<Rotina> path = new PathBuilder<>(Rotina.class, "rotina");
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
        if (query.getLimit() != Query.NO_LIMIT) {
            jpaQuery.limit(query.getLimit());
        }

        return jpaQuery.fetch();
    }


    public long count(Query<Rotina> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<Rotina> createQuery(Query<Rotina> query) {
        QRotina qRotina = QRotina.rotina;

        final JPAQuery<Rotina> jpaQuery = new JPAQueryFactory(entityManager)
                .selectDistinct(qRotina)
                .from(qRotina)
                .leftJoin(qRotina.dias).fetchJoin()
                .leftJoin(qRotina.semanas).fetchJoin()
                .leftJoin(qRotina.meses).fetchJoin()
                .leftJoin(qRotina.parametros).fetchJoin()
                .leftJoin(qRotina.capturaAndamento).fetchJoin();

        // filter
        if (query.getFilter() != null && query.getFilter() instanceof RotinaFilter) {
            final RotinaFilter rotinaFilter = (RotinaFilter) query.getFilter();

            // id
            if (rotinaFilter.getId() != null) {
                jpaQuery.where(qRotina.id.eq(rotinaFilter.getId()));
            }

            // descricao
            if (StringUtils.isNotEmpty(rotinaFilter.getDescricao())) {
                jpaQuery.where(qRotina.descricao.likeIgnoreCase("%" + rotinaFilter.getDescricao() + "%"));
            }

            // ativo
            if (rotinaFilter.getAtivo() != null) {
                jpaQuery.where(qRotina.ativo.eq(rotinaFilter.getAtivo()));
            }

            // captura andamento id
            if (Objects.nonNull(rotinaFilter.getCapturaAndamentoId())) {
                jpaQuery.where(qRotina.capturaAndamento.id.eq(rotinaFilter.getCapturaAndamentoId()));
            }
        }

        return jpaQuery;
    }
}
