package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Decisao;
import br.com.finchsolucoes.xgracco.domain.entity.QReferenciaHonorarios;
import br.com.finchsolucoes.xgracco.domain.entity.ReferenciaHonorarios;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.ReferenciaHonorarioFilter;
import br.com.finchsolucoes.xgracco.domain.repository.ReferenciaHonorarioJpaRepository;
import br.com.finchsolucoes.xgracco.domain.repository.ReferenciaHonorarioRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementação JPA do repositório da Referencia Honorario.
 *
 * @author Jordano
 * @since 2.1
 */
@Repository
public class ReferenciaHonorarioRepositoryImpl extends AbstractJpaRepository<ReferenciaHonorarios, Long> implements ReferenciaHonorarioJpaRepository {

    public List<ReferenciaHonorarios> find(Query<ReferenciaHonorarios> query) {
        final PathBuilder<Decisao> path = new PathBuilder<>(Decisao.class, "referenciaHonorarios");
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
        List<ReferenciaHonorarios> list = jpaQuery.fetch();
        return list;
    }


    public long count(Query<ReferenciaHonorarios> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<ReferenciaHonorarios> createQuery(Query<ReferenciaHonorarios> query) {
        QReferenciaHonorarios qReferenciaHonorarios = QReferenciaHonorarios.referenciaHonorarios;

        final JPAQuery<ReferenciaHonorarios> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qReferenciaHonorarios)
                .from(qReferenciaHonorarios);


        // filter
        if (query.getFilter() != null && query.getFilter() instanceof ReferenciaHonorarioFilter) {
            final ReferenciaHonorarioFilter referenciaHonorarioFilter = (ReferenciaHonorarioFilter) query.getFilter();

            // descricao
            if (StringUtils.isNotEmpty(referenciaHonorarioFilter.getDescricao())) {
                jpaQuery.where(qReferenciaHonorarios.descricao.likeIgnoreCase("%" + referenciaHonorarioFilter.getDescricao() + "%"));
            }
        }

        return jpaQuery;
    }
}
