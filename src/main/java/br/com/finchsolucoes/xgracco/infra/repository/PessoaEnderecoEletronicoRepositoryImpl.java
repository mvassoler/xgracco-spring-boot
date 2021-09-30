package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.domain.query.impl.PessoaEnderecoEletronicoFilter;
import br.com.finchsolucoes.xgracco.domain.repository.PessoaEnderecoEletronicoJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade EnderecoEletronico
 *
 * @author Felipi Berdun
 * @since 2.1
 */
@Repository
public class PessoaEnderecoEletronicoRepositoryImpl extends AbstractJpaRepository<PessoaEnderecoEletronico, Long> implements PessoaEnderecoEletronicoJpaRepository {

    @Override
    public List<PessoaEnderecoEletronico> find(Pessoa pessoa, Query<PessoaEnderecoEletronico> query) {
        final JPAQuery<PessoaEnderecoEletronico> jpaQuery = createQuery(pessoa, query);
        final PathBuilder<PessoaEnderecoEletronico> builder = new PathBuilder<>(PessoaEnderecoEletronico.class, "pessoaEnderecoEletronico");

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
    public Long count(Pessoa pessoa, Query<PessoaEnderecoEletronico> query) {
        return createQuery(pessoa, query).fetchCount();
    }

    private JPAQuery<PessoaEnderecoEletronico> createQuery(Pessoa pessoa, Query<PessoaEnderecoEletronico> query) {
        final QPessoaEnderecoEletronico qPessoaEnderecoEletronico = QPessoaEnderecoEletronico.pessoaEnderecoEletronico;
        final QPessoa qPessoa = QPessoa.pessoa;
        final QPapel qPapel = QPapel.papel;

        final JPAQuery<PessoaEnderecoEletronico> jpaQuery = new JPAQueryFactory(entityManager)
                .selectDistinct(qPessoaEnderecoEletronico)
                .from(qPessoaEnderecoEletronico)
                .join(qPessoaEnderecoEletronico.pessoa, qPessoa)
                .leftJoin(qPessoa.papeis, qPapel);

        if (Objects.nonNull(pessoa)) {
            jpaQuery.where(qPessoa.eq(pessoa));
        }

        if (query.getFilter() != null && query.getFilter() instanceof PessoaEnderecoEletronicoFilter) {
            PessoaEnderecoEletronicoFilter filter = (PessoaEnderecoEletronicoFilter) query.getFilter();

            if (StringUtils.isNotBlank(filter.getDescricao())) {
                jpaQuery.where(qPessoaEnderecoEletronico.descricao.likeIgnoreCase("%" + filter.getDescricao() + "%"));
            }

            if (filter.isPadrao() != null) {
                jpaQuery.where(qPessoaEnderecoEletronico.padrao.eq(filter.isPadrao()));
            }

            if (Objects.nonNull(filter.getPapel())) {
                jpaQuery.where(qPapel.eq(filter.getPapel()));
            }


        }

        return jpaQuery;
    }
}
