package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.CampoListaFilter;
import br.com.finchsolucoes.xgracco.domain.repository.CampoListaJpaRepository;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Repositório da entidade CampoLista
 *
 * @author guilhermecamargo
 */
@Repository
public class CampoListaRepositoryImpl extends AbstractJpaRepository<CampoLista, Long> implements CampoListaJpaRepository {

    @Override
    public List<CampoLista> find(Query<CampoLista> query) {

        final JPAQuery<CampoLista> jpaQuery = createQuery(query);

        this.applyPagination(jpaQuery, query.getPage(), query.getLimit());

        this.applySorter(jpaQuery, query.getSorter());

        return jpaQuery.fetch();

    }

    @Override
    public long count(Query<CampoLista> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<CampoLista> createQuery(Query<CampoLista> query) {
        QCampoLista qCampoLista = QCampoLista.campoLista;
        QCampo qCampo = QCampo.campo;

        final JPAQuery<CampoLista> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qCampoLista)
                .from(qCampoLista)
                .join(qCampoLista.campo, qCampo);

        // filter
        if (query.getFilter() != null && query.getFilter() instanceof CampoListaFilter) {
            final CampoListaFilter campoListaFilter = (CampoListaFilter) query.getFilter();

            if(Objects.nonNull(campoListaFilter.getIdFormulario())){
                QFormulario qFormulario = QFormulario.formulario;
                jpaQuery.join(qCampo.formulario, qFormulario);
                jpaQuery.where(qFormulario.id.eq(campoListaFilter.getIdFormulario()));
            }

            // campo
            if (Objects.nonNull(campoListaFilter.getIdCampo())) {
                jpaQuery.where(qCampo.id.eq(campoListaFilter.getIdCampo()));
            }

            //visivel
            if(Objects.nonNull(campoListaFilter.getVisivel())){
                jpaQuery.where(qCampoLista.visivel.eq(true));
            }

            //sem relacao
            if(campoListaFilter.isSemRelacao()){
                QCampo qCampo1 = new QCampo("campoN");
                jpaQuery.where(JPAExpressions.
                        select(qCampo1)
                        .from(qCampo1)
                        .where(qCampo1.campoListaPai.id.eq(qCampoLista.id))
                        .notExists());
            }
        }

        return jpaQuery;
    }

    @Override
    public Optional<CampoLista> findByCampoAndId(Campo campo, Long id) {
        final QCampoLista qCampoLista = QCampoLista.campoLista;
        final QCampo qCampo = QCampo.campo;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(qCampoLista)
                .from(qCampoLista)
                .join(qCampoLista.campo, qCampo).fetchJoin()
                .where(qCampo.eq(campo))
                .where(qCampoLista.id.eq(id)).fetchOne());
    }

    @Override
    public Optional<CampoLista> findByDescricaoAndCampo(String descricao, Campo campo) {
        QCampoLista qCampoLista = QCampoLista.campoLista;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(qCampoLista)
                .from(qCampoLista)
                .where(qCampoLista.descricao.equalsIgnoreCase(descricao))
                .where(qCampoLista.campo.eq(campo))
                .fetchOne());
    }

}
