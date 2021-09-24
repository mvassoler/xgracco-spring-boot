package br.com.finchsolucoes.xgracco.domain.repository.persistences;

import br.com.finchsolucoes.xgracco.domain.entity.Acao;
import br.com.finchsolucoes.xgracco.domain.entity.QAcao;
import br.com.finchsolucoes.xgracco.domain.entity.QPratica;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.AcaoFilter;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by felipiberdun on 29/12/2016.
 */
@Repository
public class AcaoJpaRepository extends AbstractJpaRepository<Acao, Long>  {

    @Override
    public List<Acao> find(Query<Acao> query) {
        final JPAQuery<Acao> jpaQuery = createQuery(query);
        final PathBuilder<Acao> entityPath = new PathBuilder<>(Acao.class, "acao");

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

        jpaQuery.limit(query.getLimit());

        return jpaQuery.fetch();
    }

    public List<Acao> findAllCache() {

        final QAcao qAcao = QAcao.acao;
        final QPratica qPratica = QPratica.pratica;

        final JPAQuery<Acao> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qAcao)
                .from(qAcao)
                .join(qAcao.praticas, qPratica).fetchJoin();

        return jpaQuery.fetch();
    }


    @Override
    public long count(Query<Acao> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<Acao> createQuery(Query<Acao> query) {
        final QAcao qAcao = QAcao.acao;
        final QPratica qPratica = QPratica.pratica;

        final JPAQuery<Acao> jpaQuery = new JPAQueryFactory(this.entityManager)
                .select(qAcao)
                .from(qAcao);

        if (query.getFilter() != null && query.getFilter() instanceof AcaoFilter) {
            final AcaoFilter acaoFilter = (AcaoFilter) query.getFilter();

            if (StringUtils.isNotBlank(acaoFilter.getDescricao())) {
                jpaQuery.where(qAcao.descricao.likeIgnoreCase("%" + acaoFilter.getDescricao() + "%"));
            }

            if (acaoFilter.getInstancia() != null) {
                jpaQuery.where(qAcao.instancias.any().eq(acaoFilter.getInstancia()));
            }

            if (acaoFilter.getPratica() != null) {
                jpaQuery.join(qAcao.praticas, qPratica);
                jpaQuery.where(qPratica.eq(acaoFilter.getPratica()));
            }
        }

        return jpaQuery;
    }

}
