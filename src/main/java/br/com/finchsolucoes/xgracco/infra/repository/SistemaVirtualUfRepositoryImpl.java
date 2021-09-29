package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.QSistemaVirtual;
import br.com.finchsolucoes.xgracco.domain.entity.QSistemaVirtualUf;
import br.com.finchsolucoes.xgracco.domain.entity.QUf;
import br.com.finchsolucoes.xgracco.domain.entity.SistemaVirtualUf;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.SistemaVirtualUfFilter;
import br.com.finchsolucoes.xgracco.domain.repository.SistemaVirtualUfJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SistemaVirtualUfRepositoryImpl extends AbstractJpaRepository<SistemaVirtualUf, Long> implements SistemaVirtualUfJpaRepository {


    public List<SistemaVirtualUf> find(Query<SistemaVirtualUf> query) {
        final JPAQuery<SistemaVirtualUf> jpaQuery = createQuery(query);
        final PathBuilder<SistemaVirtualUf> entityPath = new PathBuilder<>(SistemaVirtualUf.class, "sistemaVirtualUf");

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

        List<SistemaVirtualUf> list = jpaQuery.fetch();
        return list;
    }


    public long count(Query<SistemaVirtualUf> query) {
        return createQuery(query).fetchCount();
    }


    private JPAQuery<SistemaVirtualUf> createQuery(Query<SistemaVirtualUf> query) {
        final QSistemaVirtualUf qSistemaVirtualUf = QSistemaVirtualUf.sistemaVirtualUf;
        final QSistemaVirtual qSistemaVirtual = QSistemaVirtual.sistemaVirtual;
        final QUf qUf = QUf.uf;


        final JPAQuery<SistemaVirtualUf> jpaQuery = new JPAQueryFactory(this.entityManager)
                .select(qSistemaVirtualUf)
                .from(qSistemaVirtualUf)
                .join(qSistemaVirtualUf.sistemaVirtual, qSistemaVirtual).fetchJoin()
                .join(qSistemaVirtualUf.uf, qUf).fetchJoin();

        if (query.getFilter() != null && query.getFilter() instanceof SistemaVirtualUfFilter) {
            final SistemaVirtualUfFilter sistemaVirtualUfFilter = (SistemaVirtualUfFilter) query.getFilter();


            if (sistemaVirtualUfFilter.getSistemaVirtual() != null) {
                jpaQuery.where(qSistemaVirtualUf.sistemaVirtual.descricao.likeIgnoreCase("%" + sistemaVirtualUfFilter.getSistemaVirtual().getDescricao() + "%"));
            }

            if (sistemaVirtualUfFilter.getUf() != null) {
                jpaQuery.where(qSistemaVirtualUf.uf.id.eq(sistemaVirtualUfFilter.getUf().getId()));
            }

        }

        return jpaQuery;
    }

}