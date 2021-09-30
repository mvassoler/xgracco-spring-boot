package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.domain.query.impl.PessoaOabFilter;
import br.com.finchsolucoes.xgracco.domain.repository.PessoaOabJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade Oab
 *
 * @author Felipi Berdun
 * @since 2.1
 */
@Repository
public class PessoaOabRepositoryImpl extends AbstractJpaRepository<PessoaOab, Long> implements PessoaOabJpaRepository {

    @Override
    public List<PessoaOab> find(Pessoa pessoa, Query<PessoaOab> query) {
        final JPAQuery<PessoaOab> jpaQuery = createQuery(pessoa, query);
        final PathBuilder<PessoaOab> builder = new PathBuilder<>(PessoaOab.class, "pessoaOab");

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
    public Long count(Pessoa pessoa, Query<PessoaOab> query) {
        return createQuery(pessoa, query).fetchCount();
    }

    private JPAQuery<PessoaOab> createQuery(Pessoa pessoa, Query<PessoaOab> query) {
        final QPessoaOab qPessoaOab = QPessoaOab.pessoaOab;
        final QPessoa qPessoa = QPessoa.pessoa;
        final QUf qUf = QUf.uf;

        final JPAQuery<PessoaOab> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qPessoaOab)
                .from(qPessoaOab)
                .join(qPessoaOab.pessoa, qPessoa)
                .join(qPessoaOab.uf, qUf).fetchJoin()
                .where(qPessoa.eq(pessoa));

        if (query.getFilter() != null && query.getFilter() instanceof PessoaOabFilter) {
            PessoaOabFilter filter = (PessoaOabFilter) query.getFilter();

            if (filter.getUf() != null) {
                jpaQuery.where(qPessoaOab.uf.eq(filter.getUf()));
            }

            if (StringUtils.isNotBlank(filter.getNumero())) {
                jpaQuery.where(qPessoaOab.numero.likeIgnoreCase("%" + filter.getNumero() + "%"));
            }
        }

        return jpaQuery;
    }

}
