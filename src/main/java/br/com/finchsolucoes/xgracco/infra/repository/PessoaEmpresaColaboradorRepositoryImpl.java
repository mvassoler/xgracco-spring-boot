package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Pessoa;
import br.com.finchsolucoes.xgracco.domain.entity.PessoaEmpresaColaborador;
import br.com.finchsolucoes.xgracco.domain.entity.QPessoa;
import br.com.finchsolucoes.xgracco.domain.entity.QPessoaEmpresaColaborador;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.domain.repository.PessoaEmpresaColaboradorJpaRepository;
import br.com.finchsolucoes.xgracco.domain.repository.PessoaEmpresaColaboradorRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade EmpresaColaborador
 *
 * @author Felipi Berdun
 * @since 2.1
 */
@Repository
public class PessoaEmpresaColaboradorRepositoryImpl extends AbstractJpaRepository<PessoaEmpresaColaborador, Long> implements PessoaEmpresaColaboradorJpaRepository {

    @Override
    public List<PessoaEmpresaColaborador> find(Pessoa pessoa, Query<PessoaEmpresaColaborador> query) {
        final JPAQuery<PessoaEmpresaColaborador> jpaQuery = createQuery(pessoa);
        final PathBuilder<PessoaEmpresaColaborador> builder = new PathBuilder<>(PessoaEmpresaColaborador.class, "pessoaEmpresaColaborador");

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
    public Long count(Pessoa pessoa, Query<PessoaEmpresaColaborador> query) {
        return createQuery(pessoa).fetchCount();
    }

    private JPAQuery<PessoaEmpresaColaborador> createQuery(Pessoa pessoa) {
        final String EMPRESA_ALIAS = "empresa";
        final QPessoa qEmpresa = new QPessoa(EMPRESA_ALIAS);
        final QPessoaEmpresaColaborador qPessoaEmpresaColaborador = QPessoaEmpresaColaborador.pessoaEmpresaColaborador;
        final QPessoa qPessoa = QPessoa.pessoa;

        final JPAQuery<PessoaEmpresaColaborador> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qPessoaEmpresaColaborador)
                .from(qPessoaEmpresaColaborador)
                .join(qPessoaEmpresaColaborador.pessoa, qPessoa).fetchJoin()
                .join(qPessoaEmpresaColaborador.empresa, qEmpresa).fetchJoin();

        if (pessoa != null) {
            jpaQuery.where(qPessoa.eq(pessoa));
        }

        return jpaQuery;
    }
}
