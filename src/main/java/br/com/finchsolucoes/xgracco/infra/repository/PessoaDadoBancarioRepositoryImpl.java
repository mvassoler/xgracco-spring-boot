package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.domain.query.impl.PessoaDadoBancarioFilter;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade DadosBancarios
 *
 * @author Felipi Berdun
 * @since 2.1
 */
@Repository
public class PessoaDadoBancarioRepositoryImpl extends AbstractJpaRepository<PessoaDadoBancario, Long> implements br.com.finchsolucoes.xgracco.domain.repository.PessoaDadoBancarioJpaRepository {

    @Override
    public List<PessoaDadoBancario> find(Pessoa pessoa, Query<PessoaDadoBancario> query) {
        final JPAQuery<PessoaDadoBancario> jpaQuery = createQuery(query);
        final PathBuilder<PessoaDadoBancario> builder = new PathBuilder<>(PessoaDadoBancario.class, "pessoaDadoBancario");

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
    public Long count(Pessoa pessoa, Query<PessoaDadoBancario> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<PessoaDadoBancario> createQuery(Query<PessoaDadoBancario> query) {
        final QPessoaDadoBancario qPessoaDadoBancario = QPessoaDadoBancario.pessoaDadoBancario;
        final QBanco qBanco = QBanco.banco;
        final QPessoa qPessoa = QPessoa.pessoa;


        final JPAQuery<PessoaDadoBancario> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qPessoaDadoBancario)
                .from(qPessoaDadoBancario)
                .leftJoin(qPessoaDadoBancario.banco, qBanco).fetchJoin()
                .leftJoin(qPessoaDadoBancario.pessoa, qPessoa).fetchJoin();

        if (query.getFilter() != null && query.getFilter() instanceof PessoaDadoBancarioFilter) {
            final PessoaDadoBancarioFilter filter = (PessoaDadoBancarioFilter) query.getFilter();

            if (StringUtils.isNotBlank(filter.getDescricao())) {
                jpaQuery.where(qPessoaDadoBancario.descricao.likeIgnoreCase("%" + filter.getDescricao() + "%"));
            }

            if (filter.getBanco() != null) {
                jpaQuery.where(qPessoaDadoBancario.banco.id.eq(filter.getBanco().getId()));
            }

            if (filter.getPessoa() != null) {
                jpaQuery.where(qPessoaDadoBancario.pessoa.id.eq(filter.getPessoa().getId()));
            }
        }

        return jpaQuery;
    }

}
