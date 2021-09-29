package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.PercentualCalculoPrecificacao;
import br.com.finchsolucoes.xgracco.domain.entity.QPercentualCalculoPrecificacao;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.PercentualCalculoPrecificacaoFilter;
import br.com.finchsolucoes.xgracco.domain.repository.PercentualCalculoPrecificacaoJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementação do repositório da entidade PercentualCalculProvisao
 * @author guilhermecamargo 
 */
@Repository
public class PercentualCalculoPrecificacaoRepositoryImpl extends AbstractJpaRepository<PercentualCalculoPrecificacao, Long> implements PercentualCalculoPrecificacaoJpaRepository {

    @Override
    public List<PercentualCalculoPrecificacao> find(Query<PercentualCalculoPrecificacao> query) {
        final PathBuilder<PercentualCalculoPrecificacao> path = new PathBuilder<>(PercentualCalculoPrecificacao.class, "percentualCalculoPrecificacao");
        final JPAQuery jpaQuery = createQuery(query);

        // order
        if (query.getSorter() != null && StringUtils.isNotBlank(query.getSorter().getProperty())) {
            if (query.getSorter().isDesc()) {
                jpaQuery.orderBy(path.getString(query.getSorter().getProperty()).desc());
            } else {
                jpaQuery.orderBy(path.getString(query.getSorter().getProperty()).asc());
            }
        } else {
            jpaQuery.orderBy(path.getString("id").asc());
        }

        // page
        if (Optional.ofNullable(query.getPage()).orElse(0L) > 0L) {
            jpaQuery.offset(((query.getPage() - 1L) * query.getLimit()));
        }

        // limit
        jpaQuery.limit(query.getLimit());
        List<PercentualCalculoPrecificacao> list = jpaQuery.fetch();
        return list;
    }

    @Override
    public long count(Query<PercentualCalculoPrecificacao> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<PercentualCalculoPrecificacao> createQuery(Query<PercentualCalculoPrecificacao> query) {
        final QPercentualCalculoPrecificacao  qPercentualCalculoPrecificacao = QPercentualCalculoPrecificacao.percentualCalculoPrecificacao;

        final JPAQuery<PercentualCalculoPrecificacao> jpaQuery = new JPAQueryFactory(entityManager)
                .selectDistinct(qPercentualCalculoPrecificacao.create(
                        qPercentualCalculoPrecificacao.id,
                        qPercentualCalculoPrecificacao.descricao,
                        qPercentualCalculoPrecificacao.percentual
                ))
                .from(qPercentualCalculoPrecificacao);

        // filter
        if (query.getFilter() != null && query.getFilter() instanceof PercentualCalculoPrecificacaoFilter) {
            final PercentualCalculoPrecificacaoFilter percentualCalculoPrecificacaoFilter = (PercentualCalculoPrecificacaoFilter) query.getFilter();

            // descricao
            if (StringUtils.isNotEmpty(percentualCalculoPrecificacaoFilter.getDescricao())) {
                jpaQuery.where(qPercentualCalculoPrecificacao.descricao.likeIgnoreCase("%" + percentualCalculoPrecificacaoFilter.getDescricao() + "%"));
            }

            // percentual
            if(Objects.nonNull(percentualCalculoPrecificacaoFilter.getPercentual())){
                jpaQuery.where(qPercentualCalculoPrecificacao.percentual.eq(percentualCalculoPrecificacaoFilter.getPercentual()));
            }
        }

        return jpaQuery;
    }
}
