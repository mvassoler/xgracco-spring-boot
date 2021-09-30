package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.domain.query.impl.EsteiraFilter;
import br.com.finchsolucoes.xgracco.domain.repository.EsteiraJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade Esteira
 *
 * @author Felipi Berdun
 * @since 2.1
 */
@Repository
public class EsteiraRepositoryImpl extends AbstractJpaRepository<Esteira, Long> implements  EsteiraJpaRepository {

    @Override
    public List<Esteira> find(Query<Esteira> query) {
        final JPAQuery<Esteira> jpaQuery = createQuery(query);
        final PathBuilder<Esteira> builder = new PathBuilder<>(Esteira.class, "esteira");

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

    @Override
    public long count(Query<Esteira> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<Esteira> createQuery(Query<Esteira> query) {
        final QEsteira qEsteira = QEsteira.esteira;
        final QPessoa qPessoa = QPessoa.pessoa;
        final QEsteiraTarefa qEsteiraTarefa = QEsteiraTarefa.esteiraTarefa;
        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;

        final JPAQuery<Esteira> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qEsteira)
                .from(qEsteira);

        if (query.getFilter() != null && query.getFilter() instanceof EsteiraFilter) {
            EsteiraFilter filter = (EsteiraFilter) query.getFilter();

            if (StringUtils.isNotBlank(filter.getDescricao())) {
                jpaQuery.where(qEsteira.descricao.likeIgnoreCase("%" + filter.getDescricao() + "%"));
            }

            if (filter.getPessoa() != null) {
                jpaQuery.join(qEsteira.pessoas, qPessoa);
                jpaQuery.where(qPessoa.eq(filter.getPessoa()));
            }

            if (filter.getTarefa() != null) {
                jpaQuery.join(qEsteira.tarefas, qEsteiraTarefa)
                        .join(qEsteiraTarefa.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefa);
                jpaQuery.where(qFluxoTrabalhoTarefa.tarefa.eq(filter.getTarefa()));
            }
        }

        return jpaQuery;
    }

    @Override
    public List<Esteira> findByFluxoTrabalhoTarefa(FluxoTrabalhoTarefa ftt) {
        final QEsteira qEsteira = QEsteira.esteira;
        final QEsteiraTarefa qEsteiraTarefa = QEsteiraTarefa.esteiraTarefa;

        return new JPAQueryFactory(entityManager)
                .select(qEsteira)
                .from(qEsteira)
                .join(qEsteira.tarefas, qEsteiraTarefa)
                .where(qEsteiraTarefa.fluxoTrabalhoTarefa.eq(ftt))
                .fetch();
    }
}
