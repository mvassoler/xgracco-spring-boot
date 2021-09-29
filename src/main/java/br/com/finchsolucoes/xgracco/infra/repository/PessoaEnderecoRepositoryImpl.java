package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.domain.query.impl.PessoaEnderecoFilter;
import br.com.finchsolucoes.xgracco.domain.repository.PessoaEnderecoJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade Endereco
 *
 * @author Felipi Berdun
 * @since 2.1
 */
@Repository
public class PessoaEnderecoRepositoryImpl extends AbstractJpaRepository<PessoaEndereco, Long> implements PessoaEnderecoJpaRepository {

    @Override
    public List<PessoaEndereco> find(Pessoa pessoa, Query<PessoaEndereco> query) {
        final JPAQuery<PessoaEndereco> jpaQuery = createQuery(pessoa, query);
        final PathBuilder<PessoaEndereco> builder = new PathBuilder<>(PessoaEndereco.class, "pessoaEndereco");

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
    public Long count(Pessoa pessoa, Query<PessoaEndereco> query) {
        return createQuery(pessoa, query).fetchCount();
    }

    private JPAQuery<PessoaEndereco> createQuery(Pessoa pessoa, Query<PessoaEndereco> query) {
        final QPessoaEndereco qPessoaEndereco = QPessoaEndereco.pessoaEndereco;
        final QPessoa qPessoa = QPessoa.pessoa;
        final QCidade qCidade = QCidade.cidade;
        final QUf qUf = QUf.uf;

        final JPAQuery<PessoaEndereco> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qPessoaEndereco)
                .from(qPessoaEndereco)
                .join(qPessoaEndereco.pessoa, qPessoa)
                .join(qPessoaEndereco.cidade, qCidade).fetchJoin()
                .join(qCidade.uf, qUf).fetchJoin()
                .where(qPessoa.eq(pessoa));

        if (query.getFilter() != null && query.getFilter() instanceof PessoaEnderecoFilter) {
            PessoaEnderecoFilter filter = (PessoaEnderecoFilter) query.getFilter();

            if (filter.getTipoEndereco() != null) {
                jpaQuery.where(qPessoaEndereco.tipoEndereco.eq(filter.getTipoEndereco()));
            }
        }

        return jpaQuery;
    }

}
