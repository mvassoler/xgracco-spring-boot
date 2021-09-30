package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.enums.EnumArea;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.domain.query.impl.GrupoAgendamentoFilter;
import br.com.finchsolucoes.xgracco.domain.repository.GrupoAgendamentoJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade GrupoAgendamento
 *
 * @author Felipi Berdun
 * @since 2.1
 */
@Repository
public class GrupoAgendamentoRepositoryImpl extends AbstractJpaRepository<GrupoAgendamento, Long> implements GrupoAgendamentoJpaRepository {

    @Override
    public List<GrupoAgendamento> find(Query<GrupoAgendamento> query) {
        final JPAQuery<GrupoAgendamento> jpaQuery = createQuery(query);
        final PathBuilder<GrupoAgendamento> builder = new PathBuilder<>(GrupoAgendamento.class, "grupoAgendamento");

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
    public long count(Query<GrupoAgendamento> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<GrupoAgendamento> createQuery(Query<GrupoAgendamento> query) {
        final QGrupoAgendamento qGrupoAgendamento = QGrupoAgendamento.grupoAgendamento;
        final QCarteira qCarteira = QCarteira.carteira;

        final JPAQuery<GrupoAgendamento> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qGrupoAgendamento)
                .from(qGrupoAgendamento)
                .join(qGrupoAgendamento.carteira, qCarteira).fetchJoin();


        if (query.getFilter() != null && query.getFilter() instanceof GrupoAgendamentoFilter) {
            GrupoAgendamentoFilter filter = (GrupoAgendamentoFilter) query.getFilter();

            if (StringUtils.isNotBlank(filter.getDescricao())) {
                jpaQuery.where(qGrupoAgendamento.descricao.likeIgnoreCase("%" + filter.getDescricao() + "%"));
            }

            if (filter.getCarteira() != null & filter.getCarteira().getId() != null) {
                jpaQuery.where(qCarteira.id.eq(filter.getCarteira().getId()));
            }
        }

        return jpaQuery;
    }

    @Override
    public GrupoAgendamento findByIdFetchAgendamentoTarefa(Long idGrupo) {
        final QGrupoAgendamento qGrupoAgendamento = QGrupoAgendamento.grupoAgendamento;
        final QModeloAgendamento qModeloAgendamento = QModeloAgendamento.modeloAgendamento;
        final QPessoa qPessoa = QPessoa.pessoa;
        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;

        return new JPAQueryFactory(entityManager)
                .selectDistinct(qGrupoAgendamento)
                .from(qGrupoAgendamento)
                .leftJoin(qGrupoAgendamento.modelosAgendamento, qModeloAgendamento).fetchJoin()
                .leftJoin(qModeloAgendamento.pessoa, qPessoa).fetchJoin()
                .leftJoin(qModeloAgendamento.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefa).fetchJoin()
                .where(qGrupoAgendamento.id.eq(idGrupo))
                .fetchOne();
    }

    @Override
    public List<GrupoAgendamento> findByCarteiraArea(Carteira carteira, EnumArea area) {
        final QGrupoAgendamento qGrupoAgendamento = QGrupoAgendamento.grupoAgendamento;
        final QCarteira qCarteira = QCarteira.carteira;

        return new JPAQueryFactory(entityManager)
                .select(QGrupoAgendamento.create(
                        qGrupoAgendamento.id,
                        qGrupoAgendamento.descricao
                        )
                )
                .from(qGrupoAgendamento)
                .innerJoin(qGrupoAgendamento.carteira, qCarteira)
                .where(qGrupoAgendamento.area.eq(area))
                .where(qGrupoAgendamento.carteira.eq(carteira))
                .fetch();
    }

}
