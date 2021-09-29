package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.CalculoPrecificacao;
import br.com.finchsolucoes.xgracco.domain.entity.QCalculoPrecificacao;
import br.com.finchsolucoes.xgracco.domain.enums.EnumMes;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.CalculoPrecificacaoFilter;
import br.com.finchsolucoes.xgracco.domain.repository.CalculoPrecificacaoJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementação do repositório da entidade CalculoPrecificacao
 * @author guilhermecamargo 
 */
@Repository
public class CalculoPrecificacaoRepositoryImpl extends AbstractJpaRepository<CalculoPrecificacao, Long> implements CalculoPrecificacaoJpaRepository {

    @Override
    public List<CalculoPrecificacao> find(Query<CalculoPrecificacao> query) {
        final PathBuilder<CalculoPrecificacao> path = new PathBuilder<>(CalculoPrecificacao.class, "calculoPrecificacao");
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
        List<CalculoPrecificacao> list = jpaQuery.fetch();
        return list;
    }

    @Override
    public long count(Query<CalculoPrecificacao> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<CalculoPrecificacao> createQuery(Query<CalculoPrecificacao> query) {
        final QCalculoPrecificacao qCalculoPrecificacao = QCalculoPrecificacao.calculoPrecificacao;

        final JPAQuery<CalculoPrecificacao> jpaQuery = new JPAQueryFactory(entityManager)
                .selectDistinct(qCalculoPrecificacao.create(
                        qCalculoPrecificacao.id,
                        qCalculoPrecificacao.valorMediaMensal,
                        qCalculoPrecificacao.mes,
                        qCalculoPrecificacao.ano,
                        qCalculoPrecificacao.rotinaExecutada
                ))
                .from(qCalculoPrecificacao);

        // filter
        if (query.getFilter() != null && query.getFilter() instanceof CalculoPrecificacaoFilter) {
            final CalculoPrecificacaoFilter calculoPrecificacaoFilter = (CalculoPrecificacaoFilter) query.getFilter();

            //valor da média mensal
            if(Objects.nonNull(calculoPrecificacaoFilter.getValorMediaMensal())){
                jpaQuery.where(qCalculoPrecificacao.valorMediaMensal.eq(calculoPrecificacaoFilter.getValorMediaMensal()));
            }

            //mes
            if(Objects.nonNull(calculoPrecificacaoFilter.getMes())){
                jpaQuery.where(qCalculoPrecificacao.mes.eq(calculoPrecificacaoFilter.getMes()));
            }

            //ano
            if(Objects.nonNull(calculoPrecificacaoFilter.getAno())){
                jpaQuery.where(qCalculoPrecificacao.ano.eq(calculoPrecificacaoFilter.getAno()));
            }

            //rotinaExecutada
            if(Objects.nonNull(calculoPrecificacaoFilter.getRotinaExecutada())){
                jpaQuery.where(qCalculoPrecificacao.rotinaExecutada.eq(calculoPrecificacaoFilter.getRotinaExecutada()));
            }

        }
        return jpaQuery;
    }

    @Override
    public Long findByMesAndAno(CalculoPrecificacao calculoPrecificacao, boolean executado) {
        final QCalculoPrecificacao qCalculoPrecificacao = QCalculoPrecificacao.calculoPrecificacao;

        JPAQuery<CalculoPrecificacao> query = new JPAQueryFactory(entityManager)
                .select(qCalculoPrecificacao)
                .from(qCalculoPrecificacao)
                .where(qCalculoPrecificacao.mes.eq(calculoPrecificacao.getMes()))
                .where(qCalculoPrecificacao.ano.eq(calculoPrecificacao.getAno()));

        if(executado){
            query.where(qCalculoPrecificacao.rotinaExecutada.eq(true));
        }

        return query.fetchCount();
    }

    @Override
    public Optional<CalculoPrecificacao> findCalculoMesCorrente(EnumMes mes, int ano) {
        final QCalculoPrecificacao qCalculoPrecificacao = QCalculoPrecificacao.calculoPrecificacao;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(qCalculoPrecificacao)
                .from(qCalculoPrecificacao)
                .where(qCalculoPrecificacao.mes.eq(mes))
                .where(qCalculoPrecificacao.ano.eq(ano)).fetchOne());
    }
}