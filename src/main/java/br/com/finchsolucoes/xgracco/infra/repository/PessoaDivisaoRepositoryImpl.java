package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Pessoa;
import br.com.finchsolucoes.xgracco.domain.entity.PessoaDivisao;
import br.com.finchsolucoes.xgracco.domain.entity.QPessoa;
import br.com.finchsolucoes.xgracco.domain.entity.QPessoaDivisao;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.domain.query.impl.PessoaDivisaoFilter;
import br.com.finchsolucoes.xgracco.domain.repository.PessoaDivisaoJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade DivisaoPessoa
 * <p>
 * Created by jordano on 24/01/17.
 */
@Repository
public class PessoaDivisaoRepositoryImpl extends AbstractJpaRepository<PessoaDivisao, Long> implements PessoaDivisaoJpaRepository {

    @Override
    public List<PessoaDivisao> find(final Pessoa pessoa, final Query<PessoaDivisao> query) {
        final PathBuilder<PessoaDivisao> builder = new PathBuilder<>(PessoaDivisao.class, "pessoaDivisao");
        final JPAQuery<PessoaDivisao> jpaQuery = createQuery(pessoa, query);

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
    public List<PessoaDivisao> findAllCache(){
        final QPessoaDivisao qPessoaDivisao = QPessoaDivisao.pessoaDivisao;
        final QPessoa qPessoa = QPessoa.pessoa;
        final JPAQuery<PessoaDivisao> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qPessoaDivisao)
                .from(qPessoaDivisao)
                .join(qPessoaDivisao.pessoa, qPessoa).fetchJoin();

        return jpaQuery.fetch();
    }

    @Override
    public Long count(final Pessoa pessoa, final Query<PessoaDivisao> query) {
        return createQuery(pessoa, query).fetchCount();
    }

    private JPAQuery<PessoaDivisao> createQuery(final Pessoa pessoa, final Query<PessoaDivisao> query) {
        final QPessoaDivisao qPessoaDivisao = QPessoaDivisao.pessoaDivisao;
        final QPessoa qPessoa = QPessoa.pessoa;

        final JPAQuery<PessoaDivisao> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qPessoaDivisao)
                .from(qPessoaDivisao)
                .join(qPessoaDivisao.pessoa, qPessoa);
        if (pessoa != null) {
            jpaQuery.where(qPessoa.eq(pessoa));
        }


        if (query.getFilter() != null && query.getFilter() instanceof PessoaDivisaoFilter) {
            final PessoaDivisaoFilter filter = (PessoaDivisaoFilter) query.getFilter();

            if (StringUtils.isNotBlank(filter.getDescricao())) {
                jpaQuery.where(qPessoaDivisao.descricao.likeIgnoreCase("%" + filter.getDescricao() + "%"));
            }
        }

        return jpaQuery;
    }
}
