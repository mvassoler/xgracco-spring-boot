package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.ImportacaoPlanilha;
import br.com.finchsolucoes.xgracco.domain.entity.QImportacaoPlanilha;
import br.com.finchsolucoes.xgracco.domain.entity.QUsuario;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.ImportacaoPlanilhaFilter;
import br.com.finchsolucoes.xgracco.domain.repository.ImportacaoPlanilhaJpaRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementação JPA do repositório da entidade ImportacaoPlanilha.
 *
 * @author Alessandro Ramos, Henrique e Eloi
 */
@Repository
public class ImportacaoPlanilhaRepositoryImpl extends AbstractJpaRepository<ImportacaoPlanilha, Long> implements ImportacaoPlanilhaJpaRepository {

    @Override
    public List<ImportacaoPlanilha> find(Query<ImportacaoPlanilha> query) {
        final JPAQuery<ImportacaoPlanilha> jpaQuery = createQuery(query);

        applyPagination(jpaQuery, query.getPage(), query.getLimit());
        applySorter(jpaQuery, query.getSorter());

        return jpaQuery.fetch();
    }

    @Override
    public long count(Query<ImportacaoPlanilha> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<ImportacaoPlanilha> createQuery(Query<ImportacaoPlanilha> query) {

        QImportacaoPlanilha qImportacaoPlanilha = QImportacaoPlanilha.importacaoPlanilha;
        QUsuario qUsuario = QUsuario.usuario;

        final JPAQuery<ImportacaoPlanilha> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qImportacaoPlanilha)
                .from(qImportacaoPlanilha)
                .leftJoin(qImportacaoPlanilha.usuarioCadastro, qUsuario).fetchJoin();

        // FILTER
        if (query.getFilter() != null && query.getFilter() instanceof ImportacaoPlanilhaFilter) {
            final ImportacaoPlanilhaFilter importacaoPlanilhaFilter = (ImportacaoPlanilhaFilter) query.getFilter();

            if (importacaoPlanilhaFilter.getDataEncaminhamento() != null) {
                jpaQuery.where(qImportacaoPlanilha.dataEncaminhamento
                        .between(importacaoPlanilhaFilter.getDataEncaminhamento().atStartOfDay(),
                                importacaoPlanilhaFilter.getDataEncaminhamento().plusDays(1L).atStartOfDay()));
            }

            if (importacaoPlanilhaFilter.getIdUsuario() != null) {
                jpaQuery.where(qUsuario.id.eq(importacaoPlanilhaFilter.getIdUsuario()));
            }

            if (importacaoPlanilhaFilter.getModelo() != null) {
                jpaQuery.where(qImportacaoPlanilha.modelo.eq(importacaoPlanilhaFilter.getModelo()));
            }

            if (!importacaoPlanilhaFilter.getStatus().isEmpty()) {
                jpaQuery.where(importacaoPlanilhaFilter.getStatus()
                        .stream()
                        .map(qImportacaoPlanilha.status::eq)
                        .reduce(BooleanExpression::or)
                        .get());
            }

        }

        return jpaQuery;
    }
}
