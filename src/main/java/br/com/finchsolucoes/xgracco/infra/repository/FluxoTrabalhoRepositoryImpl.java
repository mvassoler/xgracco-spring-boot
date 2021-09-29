package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.FluxoTrabalhoFilter;
import br.com.finchsolucoes.xgracco.domain.repository.FluxoTrabalhoJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade FluxoTrabalho.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
@Repository
public class FluxoTrabalhoRepositoryImpl extends AbstractJpaRepository<FluxoTrabalho, Long> implements FluxoTrabalhoJpaRepository {

    @Override
    public List<FluxoTrabalho> find(Query<FluxoTrabalho> query) {
        final PathBuilder<FluxoTrabalho> path = new PathBuilder<>(FluxoTrabalho.class, "fluxoTrabalho");
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
    public long count(Query<FluxoTrabalho> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<FluxoTrabalho> createQuery(Query<FluxoTrabalho> query) {
        QFluxoTrabalho qFluxoTrabalho = QFluxoTrabalho.fluxoTrabalho;

        final JPAQuery<FluxoTrabalho> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qFluxoTrabalho)
                .from(qFluxoTrabalho);

        // filter
        if (query.getFilter() != null && query.getFilter() instanceof FluxoTrabalhoFilter) {
            final FluxoTrabalhoFilter fluxoTrabalhoFilter = (FluxoTrabalhoFilter) query.getFilter();

            // descricao
            if (StringUtils.isNotEmpty(fluxoTrabalhoFilter.getDescricao())) {
                jpaQuery.where(qFluxoTrabalho.descricao.likeIgnoreCase("%" + fluxoTrabalhoFilter.getDescricao() + "%"));
            }
        }

        return jpaQuery;
    }

    @Override
    public Optional<FluxoTrabalho> findByCarteira(Carteira carteira) {
        QFluxoTrabalho qFluxoTrabalho = QFluxoTrabalho.fluxoTrabalho;
        QCarteira qCarteira = QCarteira.carteira;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(qFluxoTrabalho)
                .from(qCarteira)
                .join(qCarteira.fluxoTrabalho, qFluxoTrabalho)
                .where(qCarteira.id.eq(carteira.getId()))
                .fetchOne());
    }

    @Override
    public void updateDeployId(FluxoTrabalho fluxoTrabalho, String deployId) {
        QFluxoTrabalho qFluxoTrabalho = QFluxoTrabalho.fluxoTrabalho;

        new JPAUpdateClause(entityManager, qFluxoTrabalho)
                .set(qFluxoTrabalho.deployId, deployId)
                .where(qFluxoTrabalho.id.eq(fluxoTrabalho.getId()))
                .execute();
    }

    @Override
    public Optional<FluxoTrabalho> findByProcessoCaseInstanceId(String caseInstanceId){
        final QFluxoTrabalho qFluxoTrabalho = QFluxoTrabalho.fluxoTrabalho;
        final QCarteira qCarteira = QCarteira.carteira;
        final QProcesso qProcesso = QProcesso.processo1;
        final QProcessoCaseInstance qProcessoCaseInstance = QProcessoCaseInstance.processoCaseInstance;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                                    .select(qFluxoTrabalho)
                                    .from(qFluxoTrabalho)
                                    .join(qCarteira).on(qFluxoTrabalho.id.eq(qCarteira.fluxoTrabalho.id))
                                    .join(qProcesso).on(qCarteira.id.eq(qProcesso.carteira.id))
                                    .join(qProcessoCaseInstance).on(qProcesso.id.eq(qProcessoCaseInstance.processo.id))
                                    .where(qProcessoCaseInstance.caseInstanceId.eq(caseInstanceId))
                                    .fetchOne());
    }

    @Override
    public Optional<FluxoTrabalho> findByFluxoTrabalhoTarefa(FluxoTrabalhoTarefa fluxoTrabalhoTarefa) {
        QFluxoTrabalho qFluxoTrabalho = QFluxoTrabalho.fluxoTrabalho;
        QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;
        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(qFluxoTrabalho)
                .from(qFluxoTrabalho)
                .join(qFluxoTrabalho.fluxoTrabalhoTarefas, qFluxoTrabalhoTarefa)
                .where(qFluxoTrabalhoTarefa.id.eq(fluxoTrabalhoTarefa.getId()))
                .fetchOne());
    }
}
