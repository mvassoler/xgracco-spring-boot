package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.CampoFilter;
import br.com.finchsolucoes.xgracco.domain.repository.CampoJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade Campo.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
@Repository
public class CampoRepositoryImpl extends AbstractJpaRepository<Campo, Long> implements CampoJpaRepository {


    @Override
    public List<Campo> find(Query<Campo> query) {

        final JPAQuery<Campo> jpaQuery = createQuery(query);

        this.applyPagination(jpaQuery, query.getPage(), query.getLimit());

        this.applySorter(jpaQuery, query.getSorter());

        return jpaQuery.fetch();

    }


    @Override
    public long count(Query<Campo> query) {
        return createQuery(query).fetchCount();
    }


    @Override
    public List<Campo> findByFormularios(Collection<Formulario> formularios, Query<Campo> query) {

        final JPAQuery<Campo> jpaQuery = createQuery(query);

        this.applyPagination(jpaQuery, query.getPage(), query.getLimit());

        this.applySorter(jpaQuery, query.getSorter());

        return jpaQuery.fetch();

    }


    @Override
    public long countByFormularios(Collection<Formulario> formularios, Query<Campo> query) {
        return createQuery(query).fetchCount();
    }


    @Override
    public Optional<Campo> findByFormularioAndId(Formulario formulario, Long id) {
        final QCampo qCampo = QCampo.campo;
        final QFormulario qFormulario = QFormulario.formulario;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(qCampo)
                .from(qCampo)
                .join(qCampo.formulario, qFormulario).fetchJoin()
                .where(qFormulario.eq(formulario))
                .where(qCampo.id.eq(id))
                .fetchOne());
    }


    @Override
    public List<Campo> findByTarefaStatusFinal(TarefaStatusFinal tarefaStatusFinal) {
        QCampo qCampo = QCampo.campo;
        QTarefaStatusFinal qTarefaStatusFinal = QTarefaStatusFinal.tarefaStatusFinal;
        QFormulario qFormulario = QFormulario.formulario;

        return new JPAQueryFactory(entityManager)
                .select(qCampo)
                .from(qTarefaStatusFinal)
                .join(qTarefaStatusFinal.formulario, qFormulario)
                .join(qFormulario.campos, qCampo)
                .where(qTarefaStatusFinal.eq(tarefaStatusFinal))
                .fetch();
    }


    @Override
    public Optional<Campo> findByFormularioAndCampoPaiAndOpcaoPai(Long idFormulario, Long idCampo, Long idCampoLista) {
        final QCampo qCampo = QCampo.campo;
        final QCampo qCampoPai = new QCampo("campoPai");
        final QCampoLista qCampoLista = QCampoLista.campoLista;
        final QFormulario qFormulario = QFormulario.formulario;

        JPAQuery<Campo> query = new JPAQueryFactory(entityManager)
                .select(qCampo)
                .from(qCampo)
                .join(qCampo.formulario, qFormulario).fetchJoin()
                .join(qCampo.campoPai, qCampoPai).fetchJoin();

        if (Objects.nonNull(idCampoLista)) {
            query.join(qCampo.campoListaPai, qCampoLista).fetchJoin()
                    .where(qCampoLista.id.eq(idCampoLista));
        }

        return Optional.ofNullable(query.where(qFormulario.id.eq(idFormulario))
                .where(qCampoPai.id.eq(idCampo)).fetchFirst());
    }


    @Override
    public List<Campo> findByGrupo(Long idGrupo) {
        final QCampo qCampo = QCampo.campo;

        return new JPAQueryFactory(entityManager)
                .select(
                        QCampo.create(
                                qCampo.id,
                                qCampo.descricao,
                                qCampo.ordem,
                                qCampo.tamanho,
                                qCampo.tipoCampo,
                                qCampo.visivel,
                                qCampo.obrigatorio,
                                qCampo.ativo
                        )
                )
                .from(qCampo)
                .where(qCampo.grupoCampo.id.eq(idGrupo))
                .fetch();
    }


    @Override
    public List<Campo> findNaoPreenchidosByGrupoAndPreCadastroProcesso(Long idGrupo, Long idPreCadastroProcesso) {
        final QCampo qCampo = QCampo.campo;
        final QPreCadastroInfoAdicional qPreCadastroInfoAdicional = QPreCadastroInfoAdicional.preCadastroInfoAdicional;

        return new JPAQueryFactory(entityManager)
                .select(
                        QCampo.create(
                                qCampo.id,
                                qCampo.descricao,
                                qCampo.ordem,
                                qCampo.tamanho,
                                qCampo.tipoCampo,
                                qCampo.visivel,
                                qCampo.obrigatorio,
                                qCampo.ativo
                        )
                )
                .from(qCampo)
                .where(qCampo.grupoCampo.id.eq(idGrupo))
                .where(qCampo.obrigatorio.eq(true))
                .where(qCampo.ativo.eq(true))
                .where(qCampo.visivel.eq(true))
                .where(JPAExpressions
                        .select(qPreCadastroInfoAdicional)
                        .from(qPreCadastroInfoAdicional)
                        .where(qPreCadastroInfoAdicional.campo.eq(qCampo))
                        .where(qPreCadastroInfoAdicional.preCadastroProcesso.id.eq(idPreCadastroProcesso))
                        .notExists()
                )
                .fetch();
    }

    private JPAQuery<Campo> createQuery(Query<Campo> query) {
        final QCampo qCampo = QCampo.campo;
        final QFormulario qFormulario = QFormulario.formulario;
        final PathBuilder<Campo> path = new PathBuilder<>(Campo.class, "campo");

        final JPAQuery<Campo> jpaQuery = new JPAQueryFactory(entityManager)
                .select(
                        QCampo.create(
                                qCampo.id,
                                qCampo.descricao,
                                qCampo.ordem,
                                qCampo.tamanho,
                                qCampo.tipoCampo,
                                qCampo.visivel,
                                qCampo.obrigatorio,
                                qCampo.ativo,
                                QFormulario.create(
                                        qFormulario.id,
                                        qFormulario.nome
                                )
                        )
                )
                .from(qCampo)
                .leftJoin(qCampo.formulario, qFormulario);

        if (query != null) {
            // filter
            if (Objects.nonNull(query.getFilter()) && query.getFilter() instanceof CampoFilter) {
                final CampoFilter campoFilter = (CampoFilter) query.getFilter();

                if (CollectionUtils.isNotEmpty(campoFilter.getFormularioId())) {
                    jpaQuery.where(qFormulario.id.in(campoFilter.getFormularioId()));
                }

                if (CollectionUtils.isNotEmpty(campoFilter.getGrupoCampoId())) {
                    final QGrupoCampo qGrupoCampo = QGrupoCampo.grupoCampo;
                    jpaQuery.where(JPAExpressions
                            .select(qGrupoCampo)
                            .from(qGrupoCampo)
                            .where(qGrupoCampo.eq(qCampo.grupoCampo))
                            .where(qGrupoCampo.id.in(campoFilter.getGrupoCampoId()))
                            .exists()
                    );
                }

                // descricao
                if (StringUtils.isNotEmpty(campoFilter.getDescricao())) {
                    jpaQuery.where(qCampo.descricao.likeIgnoreCase("%" + campoFilter.getDescricao() + "%"));
                }

                // tipos
                if (CollectionUtils.isNotEmpty(campoFilter.getTipos())) {
                    jpaQuery.where(qCampo.tipoCampo.in(campoFilter.getTipos()));
                }

                //ativo
                if (campoFilter.isAtivo()) {
                    jpaQuery.where(qCampo.ativo.eq(true));
                }

                //campo atual
                if (Objects.nonNull(campoFilter.getCampoAtual())) {
                    jpaQuery.where(qCampo.id.ne(campoFilter.getCampoAtual().getId()));
                }
            }
        }

        return jpaQuery;
    }
}
