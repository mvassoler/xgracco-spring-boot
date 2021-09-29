package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.FluxoTrabalhoTarefa;
import br.com.finchsolucoes.xgracco.domain.entity.Formulario;
import br.com.finchsolucoes.xgracco.domain.entity.QFormulario;
import br.com.finchsolucoes.xgracco.domain.entity.QTarefaStatusFinal;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.FormularioFilter;
import br.com.finchsolucoes.xgracco.domain.repository.FormularioJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade Formulario.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
@Repository
public class FormularioRepositoryImpl extends AbstractJpaRepository<Formulario, Long> implements FormularioJpaRepository {

    @Override
    public List<Formulario> find(Query<Formulario> query) {
        final PathBuilder<Formulario> path = new PathBuilder<>(Formulario.class, "formulario");
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
    public long count(Query<Formulario> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<Formulario> createQuery(Query<Formulario> query) {
        QFormulario qFormulario = QFormulario.formulario;

        final JPAQuery<Formulario> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qFormulario)
                .from(qFormulario);

        // filter
        if (query.getFilter() != null && query.getFilter() instanceof FormularioFilter) {
            final FormularioFilter formularioFilter = (FormularioFilter) query.getFilter();

            // nome
            if (StringUtils.isNotEmpty(formularioFilter.getNome())) {
                jpaQuery.where(qFormulario.nome.likeIgnoreCase("%" + formularioFilter.getNome() + "%"));
            }
        }

        return jpaQuery;
    }

    @Override
    public Optional<Formulario> findPai(Formulario formulario) {
        QFormulario qFormulario = QFormulario.formulario;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(qFormulario.formularioPai)
                .from(qFormulario)
                .where(qFormulario.eq(formulario))
                .fetchOne());
    }

    @Override
    public List<Formulario> findByTarefa(FluxoTrabalhoTarefa fluxoTrabalhoTarefa) {
        final QFormulario qFormulario = QFormulario.formulario;
        final QTarefaStatusFinal qTarefaStatusFinal = QTarefaStatusFinal.tarefaStatusFinal;

        return new JPAQueryFactory(entityManager)
                .select(qFormulario)
                .from(qFormulario)
                .where(JPAExpressions.select(qTarefaStatusFinal.count())
                        .from(qTarefaStatusFinal)
                        .where(qTarefaStatusFinal.formulario.eq(qFormulario))
                        .where(qTarefaStatusFinal.fluxoTrabalhoTarefa.eq(fluxoTrabalhoTarefa))
                        .gt(0L))
                .fetch();
    }
}
