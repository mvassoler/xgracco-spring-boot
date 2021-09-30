package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.PublicacaoNaoColadaAcao;
import br.com.finchsolucoes.xgracco.domain.entity.QPessoa;
import br.com.finchsolucoes.xgracco.domain.entity.QProcesso;
import br.com.finchsolucoes.xgracco.domain.entity.QPublicacaoNaoColadaAcao;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.PublicacaoNaoColadaAcaoFilter;
import br.com.finchsolucoes.xgracco.domain.repository.PublicacaoNaoColadaAcaoJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class PublicacaoNaoColadaAcaoRepositoryImpl extends AbstractJpaRepository<PublicacaoNaoColadaAcao, Long> implements PublicacaoNaoColadaAcaoJpaRepository {


    public List<PublicacaoNaoColadaAcao> find(Query<PublicacaoNaoColadaAcao> query) {
        final JPAQuery<PublicacaoNaoColadaAcao> jpaQuery = createQuery(query);
        final PathBuilder<PublicacaoNaoColadaAcao> entityPath = new PathBuilder<>(PublicacaoNaoColadaAcao.class, "publicacaoNaoColadaAcao");

        if (query.getSorter() != null && StringUtils.isNotBlank(query.getSorter().getProperty())) {
            if (query.getSorter().isDesc()) {
                jpaQuery.orderBy(entityPath.getString(query.getSorter().getProperty()).desc());
            } else {
                jpaQuery.orderBy(entityPath.getString(query.getSorter().getProperty()).asc());
            }
        } else {
            jpaQuery.orderBy(entityPath.getString("id").asc());
        }

        if (Optional.ofNullable(query.getPage()).orElse(0L) > 0L) {
            jpaQuery.offset((query.getPage() - 1L) * query.getLimit());
        }

        if (query.getLimit() != Query.NO_LIMIT) {
            jpaQuery.limit(query.getLimit());
        }

        return jpaQuery.fetch();
    }


    public long count(Query<PublicacaoNaoColadaAcao> query) {
        return createQuery(query).fetchCount();
    }

    @Override
    public Optional<PublicacaoNaoColadaAcao> findByPublicacaoNaoColada(Long id) {
        final QPublicacaoNaoColadaAcao qPublicacaoNaoColadaAcao = QPublicacaoNaoColadaAcao.publicacaoNaoColadaAcao;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                    .select(qPublicacaoNaoColadaAcao)
                    .from(qPublicacaoNaoColadaAcao)
                    .where(qPublicacaoNaoColadaAcao.publicacaoNaoColada.id.eq(id)).fetchOne());
    }

    private JPAQuery<PublicacaoNaoColadaAcao> createQuery(Query<PublicacaoNaoColadaAcao> query) {
        final QPublicacaoNaoColadaAcao qPublicacaoNaoColadaAcao = QPublicacaoNaoColadaAcao.publicacaoNaoColadaAcao;
        final QProcesso qProcesso = QProcesso.processo1;
        final QPessoa qPessoa = QPessoa.pessoa;


        final JPAQuery<PublicacaoNaoColadaAcao> jpaQuery = new JPAQueryFactory(this.entityManager)
                .select(qPublicacaoNaoColadaAcao)
                .from(qPublicacaoNaoColadaAcao)
                .leftJoin(qPublicacaoNaoColadaAcao.processo, qProcesso).fetchJoin()
                .join(qPublicacaoNaoColadaAcao.pessoa, qPessoa).fetchJoin();

        if (query.getFilter() != null && query.getFilter() instanceof PublicacaoNaoColadaAcaoFilter) {
            final PublicacaoNaoColadaAcaoFilter publicacaoNaoColadaAcaoFilter = (PublicacaoNaoColadaAcaoFilter) query.getFilter();


            if (Objects.nonNull(publicacaoNaoColadaAcaoFilter.getAcao())) {
                jpaQuery.where(qPublicacaoNaoColadaAcao.acao.eq(publicacaoNaoColadaAcaoFilter.getAcao()));
            }

            if (Objects.nonNull(publicacaoNaoColadaAcaoFilter.getDataInicio()) && Objects.nonNull(publicacaoNaoColadaAcaoFilter.getDataFim())){
                jpaQuery.where(qPublicacaoNaoColadaAcao.dataAcao.between(publicacaoNaoColadaAcaoFilter.getDataInicio(), publicacaoNaoColadaAcaoFilter.getDataFim()));
            }else if(Objects.nonNull(publicacaoNaoColadaAcaoFilter.getDataInicio())){
                jpaQuery.where(qPublicacaoNaoColadaAcao.dataAcao.goe(publicacaoNaoColadaAcaoFilter.getDataInicio()));
            }else if(Objects.nonNull(publicacaoNaoColadaAcaoFilter.getDataFim())){
                jpaQuery.where(qPublicacaoNaoColadaAcao.dataAcao.loe(publicacaoNaoColadaAcaoFilter.getDataFim()));
            }

        }

        return jpaQuery;
    }

}
