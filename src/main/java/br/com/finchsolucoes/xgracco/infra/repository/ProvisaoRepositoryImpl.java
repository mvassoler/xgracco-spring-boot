package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Provisao;
import br.com.finchsolucoes.xgracco.domain.entity.QProvisao;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.ProvisaoFilter;
import br.com.finchsolucoes.xgracco.domain.repository.ProvisaoJpaRepository;
import br.com.finchsolucoes.xgracco.domain.repository.ProvisaoRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade Provisão.
 *
 * @author Paulo Marçon
 * @since 2.1.04
 */
@Repository
public class ProvisaoRepositoryImpl extends AbstractJpaRepository<Provisao, Long> implements ProvisaoJpaRepository {


    public List<Provisao> find(Query<Provisao> query) {
        final PathBuilder<Provisao> path = new PathBuilder<>(Provisao.class, "provisao");
        final JPAQuery jpaQuery = createQuery(query);

        if (query.getSorter() != null && StringUtils.isNotBlank(query.getSorter().getProperty())) {
            if (query.getSorter().isDesc()) {
                jpaQuery.orderBy(path.getString(query.getSorter().getProperty()).desc());
            } else {
                jpaQuery.orderBy(path.getString(query.getSorter().getProperty()).asc());
            }
        } else {
            jpaQuery.orderBy(path.getString("id").asc());
        }

        if (Optional.ofNullable(query.getPage()).orElse(0L) > 0L) {
            jpaQuery.offset(((query.getPage() - 1L) * query.getLimit()));
        }

        jpaQuery.limit(query.getLimit());

        return jpaQuery.fetch();
    }


    public long count(Query<Provisao> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<Provisao> createQuery(Query<Provisao> query) {
        QProvisao qProvisao = QProvisao.provisao;

        final JPAQuery<Provisao> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qProvisao)
                .from(qProvisao);

        if (query.getFilter() != null && query.getFilter() instanceof ProvisaoFilter) {
            final ProvisaoFilter provisaoFilter = (ProvisaoFilter) query.getFilter();

            if (provisaoFilter.getData() != null) {
                jpaQuery.where(qProvisao.data.eq(provisaoFilter.getData()));
            }

            if (provisaoFilter.getValorPedido() != null) {
                jpaQuery.where(qProvisao.valorPedido.eq(provisaoFilter.getValorPedido()));
            }

            if (provisaoFilter.getValorProvisao() != null) {
                jpaQuery.where(qProvisao.valorProvisao.eq(provisaoFilter.getValorProvisao()));
            }
        }
        return jpaQuery;
    }

    @Override
    public Provisao retornarProvisaoMesAno(Integer mes, Integer ano) {
        QProvisao qProvisao = QProvisao.provisao;

        return new JPAQueryFactory(this.entityManager)
                .select(qProvisao)
                .from(qProvisao)
                .where(qProvisao.data.month().eq(mes))
                .where(qProvisao.data.year().eq(ano)).fetchOne();
    }

    @Override
    public List<Provisao> retornarValoresProvisionamento(Calendar dataInicio, Calendar dataFim, Boolean baseAtiva) {
        QProvisao qProvisao = QProvisao.provisao;

        return new JPAQueryFactory(this.entityManager)
                .select(qProvisao)
                .from(qProvisao)
                .where(qProvisao.data.between(dataFim, dataInicio))
                .where(qProvisao.BaseAtiva.eq(baseAtiva))
                .orderBy(qProvisao.data.asc())
                .fetch();
    }
}
