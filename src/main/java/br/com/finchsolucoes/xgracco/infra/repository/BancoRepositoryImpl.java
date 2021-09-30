package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Banco;
import br.com.finchsolucoes.xgracco.domain.entity.QBanco;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.BancoFilter;
import br.com.finchsolucoes.xgracco.domain.repository.BancoJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade Banco.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
@Repository
public class BancoRepositoryImpl extends AbstractJpaRepository<Banco, Long> implements BancoJpaRepository {

    public List<Banco> find(Query<Banco> query) {
        final PathBuilder<Banco> path = new PathBuilder<>(Banco.class, "banco");
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

    public long count(Query<Banco> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<Banco> createQuery(Query<Banco> query) {
        QBanco qBanco = QBanco.banco;

        final JPAQuery<Banco> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qBanco)
                .from(qBanco);

        // filter
        if (query.getFilter() != null && query.getFilter() instanceof BancoFilter) {
            final BancoFilter bancoFilter = (BancoFilter) query.getFilter();

            // codigo
            if (StringUtils.isNotEmpty(bancoFilter.getCodigo())) {
                jpaQuery.where(qBanco.codigo.likeIgnoreCase("%" + bancoFilter.getCodigo() + "%"));
            }

            // descricao
            if (StringUtils.isNotEmpty(bancoFilter.getDescricao())) {
                jpaQuery.where(qBanco.descricao.likeIgnoreCase("%" + bancoFilter.getDescricao() + "%"));
            }

            // site
            if (StringUtils.isNotEmpty(bancoFilter.getSite())) {
                jpaQuery.where(qBanco.site.likeIgnoreCase("%" + bancoFilter.getSite() + "%"));
            }
        }

        return jpaQuery;
    }
}
