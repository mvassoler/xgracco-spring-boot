package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.Papel;
import br.com.finchsolucoes.xgracco.domain.entity.Pessoa;
import br.com.finchsolucoes.xgracco.domain.entity.QPapel;
import br.com.finchsolucoes.xgracco.domain.entity.QPessoa;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoPapel;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.domain.query.impl.PapelFilter;
import br.com.finchsolucoes.xgracco.domain.repository.PapelJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementação JPA da entidade Papel
 *
 * @author Thiago Fogar
 * @since 2.2
 */
@Repository
public class PapelRepositoryImpl extends AbstractJpaRepository<Papel, Long> implements PapelJpaRepository {

    @Override
    public List<Papel> find(Query<Papel> query) {

        final JPAQuery<Papel> jpaQuery = createQuery(query);
        final PathBuilder<Papel> builder = new PathBuilder<>(Papel.class, "papel");

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
    public long count(Query<Papel> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<Papel> createQuery(Query<Papel> query) {
        final QPapel qPapel = QPapel.papel;

        final JPAQuery<Papel> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qPapel)
                .from(qPapel);

        if (query.getFilter() != null && query.getFilter() instanceof PapelFilter) {
            final PapelFilter papelFilter = (PapelFilter) query.getFilter();

            // descricao
            if (StringUtils.isNotEmpty(papelFilter.getDescricao())) {
                jpaQuery.where(qPapel.descricao.likeIgnoreCase("%" + papelFilter.getDescricao() + "%"));
            }

        }
        return jpaQuery;
    }

    @Override
    public List<Pessoa> findByPapel(Papel papel) {
        QPapel qPapel = QPapel.papel;
        QPessoa qPessoa = QPessoa.pessoa;
        QPessoa qPessoaPapel = new QPessoa("pessoaPapel");

        return new JPAQueryFactory(entityManager)
                .select(qPessoa)
                .from(qPessoa)
                .where(JPAExpressions
                        .select(qPapel.count())
                        .from(qPapel)
                        .join(qPapel.pessoas, qPessoaPapel)
                        .where(qPessoaPapel.eq(qPessoa))
                        .where(qPapel.eq(papel))
                        .gt(0L))
                .fetch();
    }

    @Override
    public List<Papel> findPapelByPessoa(Pessoa pessoa) {
        final QPapel qPapel = QPapel.papel;
        final QPessoa qPessoa = QPessoa.pessoa;

        final JPAQuery<Papel> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qPapel)
                .from(qPapel)
                .innerJoin(qPapel.pessoas, qPessoa)
                .where(qPessoa.id.eq(pessoa.getId()));

        return jpaQuery.fetch();
    }

    @Override
    public List<Papel> findAll() {
        final QPapel qPapel = QPapel.papel;

        final JPAQuery<Papel> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qPapel)
                .from(qPapel);

        return jpaQuery.fetch();
    }

    @Override
    public Papel findByCodigoInterno(EnumTipoPapel tipoPapel) {
        QPapel qPapel = QPapel.papel;

        return new JPAQueryFactory(entityManager)
                .selectFrom(qPapel)
                .where(qPapel.tipoPapel.eq(tipoPapel))
                .fetchOne();
    }

    @Override
    public Optional<Papel> findByTipoPapelAndSistema(EnumTipoPapel tipoPapel, boolean sistema) {
        final QPapel qPapel = QPapel.papel;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .selectFrom(qPapel)
                .where(qPapel.tipoPapel.eq(tipoPapel))
                .where(qPapel.sistema.eq(true))
                .fetchOne());
    }
}
