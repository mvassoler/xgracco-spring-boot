package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.ComarcaFilter;
import br.com.finchsolucoes.xgracco.domain.repository.ComarcaJpaRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by felipiberdun on 18/01/2017.
 */
@Repository
public class ComarcaRepositoryImpl extends AbstractJpaRepository<Comarca, Long> implements ComarcaJpaRepository {

    //TODO - REVISAR ESTA CLASSE

    //@Autowired
    //private UfService ufService;

    @Override
    public List<Comarca> find(Query<Comarca> query) {
        return createQuery(query, true).fetch();
    }

    @Override
    public List<Comarca> findAllCache() {

        final QComarca qComarca = QComarca.comarca;

        final JPAQuery<Comarca> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qComarca)
                .from(qComarca)
                .join(qComarca.uf).fetchJoin();

        return jpaQuery.fetch();
    }

    @Override
    public long count(Query<Comarca> query) {
        return createQuery(query, false).fetchCount();
    }

    private JPAQuery<Comarca> createQuery(Query<Comarca> query, boolean sortAndPaging) {
        final QComarca qComarca = QComarca.comarca;
        final QUf qUf = QUf.uf;
        final PathBuilder<Comarca> path = new PathBuilder<>(Comarca.class, "comarca");

        final JPAQuery<Comarca> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qComarca)
                .from(qComarca)
                .join(qComarca.uf, qUf).fetchJoin();

        if (query != null) {
            // filter
            if (query.getFilter() != null && query.getFilter() instanceof ComarcaFilter) {
                final ComarcaFilter filter = (ComarcaFilter) query.getFilter();

                // descricao
                if (CollectionUtils.isNotEmpty(filter.getDescricao())) {
                    jpaQuery.where(filter.getDescricao()
                            .stream()
                            .map(descricao -> "%" + descricao + "%")
                            .map(qComarca.descricao::likeIgnoreCase)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // regiao
                if (CollectionUtils.isNotEmpty(filter.getRegiao())) {
                    if(!CollectionUtils.isNotEmpty(filter.getUf())){
                        filter.setUf(new HashSet<>());
                    }
                    filter.getRegiao().forEach(regiao-> regiao.getUfs().forEach(uf -> filter.getUf().add(Long.valueOf(uf.getId()))));
                }

                // uf
                if (CollectionUtils.isNotEmpty(filter.getUf())) {
                    Set<Uf> ufs = new HashSet<>();
                    //filter.getUf().forEach(uf-> ufs.add(ufService.findById(uf).orElseThrow(EntityNotFoundException::new)));
                    filter.getUf().forEach(uf-> ufs.add(null));
                    jpaQuery.where(ufs
                            .stream()
                            .map(qComarca.uf::eq)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // tipoJustica
                if (CollectionUtils.isNotEmpty(filter.getTipoJustica())) {
                    jpaQuery.where(qComarca.tiposJustica.any().in(filter.getTipoJustica()));
                }

                // pessoa
                if (CollectionUtils.isNotEmpty(filter.getPessoa())) {
                    final QPessoa qPessoa = QPessoa.pessoa;
                    final QComarca qPessoaComarca = new QComarca("pessoaComarca");

                    final JPQLQuery<Long> pessoaQuery = JPAExpressions
                            .select(qPessoa.count())
                            .from(qPessoa)
                            .join(qPessoa.comarcas, qPessoaComarca)
                            .where(qPessoaComarca.eq(qComarca));

                    jpaQuery.where(filter.getPessoa()
                            .stream()
                            .map(pessoa -> pessoa ? pessoaQuery.gt(0L) : pessoaQuery.eq(0L))
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // pessoa.id
                if (CollectionUtils.isNotEmpty(filter.getPessoaId())) {
                    final QPessoa qPessoa = QPessoa.pessoa;
                    final QComarca qPessoaComarca = new QComarca("pessoaComarca");

                    jpaQuery.where(filter.getPessoaId()
                            .stream()
                            .map(id -> JPAExpressions
                                    .select(qPessoa.count())
                                    .from(qPessoa)
                                    .join(qPessoa.comarcas, qPessoaComarca)
                                    .where(qPessoaComarca.eq(qComarca))
                                    .where(qPessoa.id.eq(id))
                                    .gt(0L))
                            .reduce(BooleanExpression::or)
                            .get());
                }

                // pessoa.nomeRazaoSocial
                if (CollectionUtils.isNotEmpty(filter.getPessoaNomeRazaoSocial())) {
                    final QPessoa qPessoa = QPessoa.pessoa;
                    final QComarca qPessoaComarca = new QComarca("pessoaComarca");

                    jpaQuery.where(filter.getPessoaNomeRazaoSocial()
                            .stream()
                            .map(nomeRazaoSocial -> "%" + nomeRazaoSocial + "%")
                            .map(nomeRazaoSocial -> JPAExpressions
                                    .select(qPessoa.count())
                                    .from(qPessoa)
                                    .join(qPessoa.comarcas, qPessoaComarca)
                                    .where(qPessoaComarca.eq(qComarca))
                                    .where(qPessoa.nomeRazaoSocial.likeIgnoreCase(nomeRazaoSocial))
                                    .gt(0L))
                            .reduce(BooleanExpression::or)
                            .get());
                }
            }

            if (sortAndPaging) {
                // order
                if (query.getSorter() != null && !StringUtils.isEmpty(query.getSorter().getProperty())) {
                    if (query.getSorter().isDesc()) {
                        jpaQuery.orderBy(path.getString(query.getSorter().getProperty()).desc());
                    } else {
                        jpaQuery.orderBy(path.getString(query.getSorter().getProperty()).asc());
                    }
                }

                // page
                if (Optional.ofNullable(query.getPage()).orElse(0L) > 0L) {
                    jpaQuery.offset(((query.getPage() - 1L) * query.getLimit()));
                }

                // limit
                jpaQuery.limit(query.getLimit());
            }
        }

        return jpaQuery;
    }

    @Override
    public List<Comarca> findComarcaByDescricaoAndUf(String descricao, Uf uf) {
        final QComarca qComarca = QComarca.comarca;
        final JPAQuery<Comarca> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qComarca)
                .from(qComarca)
                .where(qComarca.descricao.eq(descricao))
                .where(qComarca.uf.eq(uf));
        return jpaQuery.fetch();
    }

    @Override
    public Optional<Comarca> findById(Long id){
        final QComarca qComarca = QComarca.comarca;
        final QUf qUf = QUf.uf;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                    .select(qComarca)
                    .from(qComarca)
                    .join(qComarca.uf, qUf).fetchJoin()
                    .where(qComarca.id.eq(id)).fetchOne());
    }


    @Override
    public Optional<Comarca> findComarcaByDescricaoAndDescricaoUF(String comarca, String uf) {
        final QComarca qComarca = QComarca.comarca;
        final QUf qUf = QUf.uf;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(qComarca)
                .from(qComarca)
                .join(qComarca.uf, qUf).fetchJoin()
                .where(qComarca.descricao.equalsIgnoreCase(comarca))
                .where(qUf.nome.equalsIgnoreCase(uf)).fetchOne());
    }
}
