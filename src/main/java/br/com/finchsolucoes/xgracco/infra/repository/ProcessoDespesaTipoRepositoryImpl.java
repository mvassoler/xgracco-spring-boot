package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.ProcessoDespesaTipo;
import br.com.finchsolucoes.xgracco.domain.entity.QProcessoDespesaTipo;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.ProcessoDespesaTipoFilter;
import br.com.finchsolucoes.xgracco.domain.repository.ProcessoDespesaTipoJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade Processo Despesa Tipo.
 *
 * @author Jordano
 * @since 2.1
 */
@Repository
public class ProcessoDespesaTipoRepositoryImpl extends AbstractJpaRepository<ProcessoDespesaTipo, Long> implements ProcessoDespesaTipoJpaRepository {


    public List<ProcessoDespesaTipo> find(Query<ProcessoDespesaTipo> query) {
        final JPAQuery<ProcessoDespesaTipo> jpaQuery = createQuery(query);
        final PathBuilder<ProcessoDespesaTipo> entityPath = new PathBuilder<>(ProcessoDespesaTipo.class, "processoDespesaTipo");

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


    public long count(Query<ProcessoDespesaTipo> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<ProcessoDespesaTipo> createQuery(Query<ProcessoDespesaTipo> query) {
        final QProcessoDespesaTipo qProcessoDespesaTipo = QProcessoDespesaTipo.processoDespesaTipo;

        final JPAQuery<ProcessoDespesaTipo> jpaQuery = new JPAQueryFactory(this.entityManager)
                .select(qProcessoDespesaTipo)
                .from(qProcessoDespesaTipo);

        if (query.getFilter() != null && query.getFilter() instanceof ProcessoDespesaTipoFilter) {
            final ProcessoDespesaTipoFilter processoDespesaTipoFilter = (ProcessoDespesaTipoFilter) query.getFilter();

            if (StringUtils.isNotBlank(processoDespesaTipoFilter.getDescricao())) {
                jpaQuery.where(qProcessoDespesaTipo.descricao.likeIgnoreCase("%" + processoDespesaTipoFilter.getDescricao() + "%"));
            }

            if (processoDespesaTipoFilter.getReenbolsavel() != null) {
                jpaQuery.where(qProcessoDespesaTipo.reembolsavel.eq(processoDespesaTipoFilter.getReenbolsavel()));
            }
        }

        return jpaQuery;
    }

    public List<ProcessoDespesaTipo> findByNeIdAndDescricao(Long id ,String descricao){

        final QProcessoDespesaTipo qProcessoDespesaTipo = QProcessoDespesaTipo.processoDespesaTipo;

        final JPAQuery<ProcessoDespesaTipo> jpaQuery = new JPAQueryFactory(this.entityManager)
                .select(QProcessoDespesaTipo.create(
                        qProcessoDespesaTipo.id,
                        qProcessoDespesaTipo.descricao,
                        qProcessoDespesaTipo.reembolsavel
                ))
                .from(qProcessoDespesaTipo);

        if(Objects.nonNull(descricao) && !descricao.isEmpty()){
            jpaQuery.where(qProcessoDespesaTipo.descricao.equalsIgnoreCase(descricao));
        }

        if(Objects.nonNull(id)){
            jpaQuery.where(qProcessoDespesaTipo.id.ne(id));
        }

        return jpaQuery.fetch();
    }

}
