package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.ProcessoPedidoCenarios;
import br.com.finchsolucoes.xgracco.domain.entity.QProcessoPedido;
import br.com.finchsolucoes.xgracco.domain.entity.QProcessoPedidoCenarios;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.repository.ProcessoPedidoCenarioJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProcessoPedidoCenarioRepositoryImpl extends AbstractJpaRepository<ProcessoPedidoCenarios, Long> implements ProcessoPedidoCenarioJpaRepository {


    public List<ProcessoPedidoCenarios> find(Query<ProcessoPedidoCenarios> query) {
        final PathBuilder<ProcessoPedidoCenarios> path = new PathBuilder<>(ProcessoPedidoCenarios.class, "cenario");
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


    public long count(Query<ProcessoPedidoCenarios> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<ProcessoPedidoCenarios> createQuery(Query<ProcessoPedidoCenarios> query) {
        final QProcessoPedidoCenarios qProcessoPedidoCenarios = QProcessoPedidoCenarios.processoPedidoCenarios;

        return new JPAQueryFactory(entityManager)
                .select(qProcessoPedidoCenarios)
                .from(qProcessoPedidoCenarios);
    }


    public Optional<ProcessoPedidoCenarios> findCenarioByPedido(Long idCenario) {
        final QProcessoPedidoCenarios qProcessoPedidoCenarios = QProcessoPedidoCenarios.processoPedidoCenarios;
        final QProcessoPedido qProcessoPedido = QProcessoPedido.processoPedido;

        return Optional.ofNullable(
                new JPAQueryFactory(entityManager)
                .select(qProcessoPedidoCenarios)
                .from(qProcessoPedidoCenarios)
                .join(qProcessoPedidoCenarios.pedido, qProcessoPedido)
                .where(qProcessoPedido.id.eq(idCenario)).fetchOne());
    }
}
