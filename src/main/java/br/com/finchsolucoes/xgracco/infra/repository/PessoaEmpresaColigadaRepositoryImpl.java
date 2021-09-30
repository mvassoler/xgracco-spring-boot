package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Pessoa;
import br.com.finchsolucoes.xgracco.domain.entity.PessoaEmpresaColigada;
import br.com.finchsolucoes.xgracco.domain.entity.QPessoa;
import br.com.finchsolucoes.xgracco.domain.entity.QPessoaEmpresaColigada;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.domain.query.impl.PessoaEmpresaColigadaFilter;
import br.com.finchsolucoes.xgracco.domain.repository.PessoaEmpresaColigadaJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade EmpresaColigada
 *
 * @author Felipi Berdun
 * @since 2.1
 */
@Repository
public class PessoaEmpresaColigadaRepositoryImpl extends AbstractJpaRepository<PessoaEmpresaColigada, Long> implements PessoaEmpresaColigadaJpaRepository {

    @Override
    public List<PessoaEmpresaColigada> find(Pessoa pessoa, Query<PessoaEmpresaColigada> query) {
        final JPAQuery<PessoaEmpresaColigada> jpaQuery = createQuery(pessoa, query);
        final PathBuilder<PessoaEmpresaColigada> builder = new PathBuilder<>(PessoaEmpresaColigada.class, "pessoaEmpresaColigada");

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
    public Long count(Pessoa pessoa, Query<PessoaEmpresaColigada> query) {
        return createQuery(pessoa, query).fetchCount();
    }

    private JPAQuery<PessoaEmpresaColigada> createQuery(Pessoa pessoa, Query<PessoaEmpresaColigada> query) {
        final String EMPRESA_COLIGADA = "empresa";
        final QPessoa qEmpresa = new QPessoa(EMPRESA_COLIGADA);
        final QPessoa qPessoa = QPessoa.pessoa;
        final QPessoaEmpresaColigada qPessoaEmpresaColigada = QPessoaEmpresaColigada.pessoaEmpresaColigada;

        final JPAQuery<PessoaEmpresaColigada> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qPessoaEmpresaColigada)
                .from(qPessoaEmpresaColigada)
                .join(qPessoaEmpresaColigada.pessoa, qPessoa).fetchJoin()
                .join(qPessoaEmpresaColigada.empresa, qEmpresa).fetchJoin()
                .where(qPessoa.eq(pessoa));

        if (query.getFilter() != null && query.getFilter() instanceof PessoaEmpresaColigadaFilter) {
            PessoaEmpresaColigadaFilter filter = (PessoaEmpresaColigadaFilter) query.getFilter();

            if (StringUtils.isNotBlank(filter.getNomeRazaoSocial())) {
                jpaQuery.where(qPessoa.nomeRazaoSocial.likeIgnoreCase("%" + filter.getNomeRazaoSocial() + "%"));
            }

            if (StringUtils.isNotBlank(filter.getApelidoFantasia())) {
                jpaQuery.where(qPessoa.apelidoFantasia.likeIgnoreCase("%" + filter.getApelidoFantasia() + "%"));
            }

            if (StringUtils.isNotBlank(filter.getCpfCnpj())) {
                jpaQuery.where(qPessoa.cpfCnpj.likeIgnoreCase("%" + filter.getCpfCnpj() + "%"));
            }
        }

        return jpaQuery;
    }
}
