package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Cidade;
import br.com.finchsolucoes.xgracco.domain.entity.QCidade;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.domain.query.impl.CidadeFilter;
import br.com.finchsolucoes.xgracco.domain.repository.CidadeJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade Cidade
 *
 * @author Felipi Berdun
 * @since 2.1
 */
@Repository
public class CidadeRepositoryImpl extends AbstractJpaRepository<Cidade, Long> implements CidadeJpaRepository {

    @Override
    public List<Cidade> find(Query<Cidade> query) {
        final JPAQuery<Cidade> jpaQuery = createQuery(query);
        final PathBuilder<Cidade> builder = new PathBuilder<>(Cidade.class, "cidade");

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
    public long count(Query<Cidade> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<Cidade> createQuery(Query<Cidade> query) {
        final QCidade qCidade = QCidade.cidade;

        final JPAQuery<Cidade> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qCidade)
                .from(qCidade);

        if (query.getFilter() != null && query.getFilter() instanceof CidadeFilter) {
            CidadeFilter filter = (CidadeFilter) query.getFilter();

            if (StringUtils.isNotBlank(filter.getDescricao())) {
                jpaQuery.where(qCidade.descricao.likeIgnoreCase("%" + filter.getDescricao() + "%"));
            }

            if (filter.getUf() != null) {
                jpaQuery.where(qCidade.uf.id.eq(filter.getUf().getId()));
            }
        }

        return jpaQuery;
    }

}
