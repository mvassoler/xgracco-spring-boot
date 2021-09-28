package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.QTipoDocumento;
import br.com.finchsolucoes.xgracco.domain.entity.TipoDocumento;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.TipoDocumentoFilter;
import br.com.finchsolucoes.xgracco.domain.repository.TipoDocumentoJpaRepository;
import br.com.finchsolucoes.xgracco.domain.repository.TipoDocumentoRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade TipoDocumento.
 *
 * @author Jordano
 * @since 2.1
 */
@Repository
public class TipoDocumentoRepositoryImpl extends AbstractJpaRepository<TipoDocumento, Long> implements TipoDocumentoJpaRepository {

    public List<TipoDocumento> find(Query<TipoDocumento> query) {
        final PathBuilder<TipoDocumento> path = new PathBuilder<>(TipoDocumento.class, "tipoDocumento");
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
        if (query.getLimit() > 0L) {
            jpaQuery.limit(query.getLimit());
        }

        List<TipoDocumento> list = jpaQuery.fetch();
        return list;
    }


    public long count(Query<TipoDocumento> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<TipoDocumento> createQuery(Query<TipoDocumento> query) {
        QTipoDocumento qTipoDocumento = QTipoDocumento.tipoDocumento;

        final JPAQuery<TipoDocumento> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qTipoDocumento)
                .from(qTipoDocumento);

        // filter
        if (query.getFilter() != null && query.getFilter() instanceof TipoDocumentoFilter) {
            final TipoDocumentoFilter tipoDocumentoFilter = (TipoDocumentoFilter) query.getFilter();

            // descricao
            if (StringUtils.isNotEmpty(tipoDocumentoFilter.getDescricao())) {
                jpaQuery.where(qTipoDocumento.descricao.likeIgnoreCase("%" + tipoDocumentoFilter.getDescricao() + "%"));
            }

            // padrao
            if (Objects.nonNull(tipoDocumentoFilter.getPadrao())) {
                jpaQuery.where(qTipoDocumento.padrao.eq(tipoDocumentoFilter.getPadrao()));
            }


        }

        return jpaQuery;
    }


    public void updateTipoDocumentoPadrao() {
        QTipoDocumento qTipoDocumento = QTipoDocumento.tipoDocumento;
        new JPAUpdateClause(entityManager, qTipoDocumento)
                .set(qTipoDocumento.padrao, Boolean.FALSE)
                .execute();
    }
}
