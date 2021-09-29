package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Pessoa;
import br.com.finchsolucoes.xgracco.domain.entity.PessoaTelefone;
import br.com.finchsolucoes.xgracco.domain.entity.QPessoa;
import br.com.finchsolucoes.xgracco.domain.entity.QPessoaTelefone;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.domain.query.impl.PessoaTelefoneFilter;
import br.com.finchsolucoes.xgracco.domain.repository.PessoaTelefoneJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade Telefone
 *
 * @author Felipi Berdun
 * @since 2.1
 */
@Repository
public class PessoaTelefoneRepositoryImpl extends AbstractJpaRepository<PessoaTelefone, Long> implements PessoaTelefoneJpaRepository {

    @Override
    public List<PessoaTelefone> find(Pessoa pessoa, Query<PessoaTelefone> query) {
        final JPAQuery<PessoaTelefone> jpaQuery = createQuery(pessoa, query);
        final PathBuilder<PessoaTelefone> builder = new PathBuilder<>(PessoaTelefone.class, "pessoaTelefone");

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
    public Long count(Pessoa pessoa, Query<PessoaTelefone> query) {
        return createQuery(pessoa, query).fetchCount();
    }

    private JPAQuery<PessoaTelefone> createQuery(Pessoa pessoa, Query<PessoaTelefone> query) {
        final QPessoaTelefone qPessoaTelefone = QPessoaTelefone.pessoaTelefone;
        final QPessoa qPessoa = QPessoa.pessoa;

        final JPAQuery<PessoaTelefone> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qPessoaTelefone)
                .from(qPessoaTelefone)
                .join(qPessoaTelefone.pessoa, qPessoa)
                .where(qPessoa.eq(pessoa));

        if (query.getFilter() != null && query.getFilter() instanceof PessoaTelefoneFilter) {
            PessoaTelefoneFilter filter = (PessoaTelefoneFilter) query.getFilter();

            if (StringUtils.isNotBlank(filter.getNumero())) {
                jpaQuery.where(qPessoaTelefone.numero.likeIgnoreCase("%" + filter.getNumero() + "%"));
            }

            if (filter.getTipoTelefone() != null) {
                jpaQuery.where(qPessoaTelefone.tipoTelefone.eq(filter.getTipoTelefone()));
            }

            if (filter.isPadrao() != null) {
                jpaQuery.where(qPessoaTelefone.padrao.eq(filter.isPadrao()));
            }
        }

        return jpaQuery;
    }
}
