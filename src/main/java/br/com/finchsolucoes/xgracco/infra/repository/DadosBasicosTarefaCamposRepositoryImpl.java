package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTipoCampo;
import br.com.finchsolucoes.xgracco.domain.exception.EntityNotFoundException;
import br.com.finchsolucoes.xgracco.domain.exception.IdNullException;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.DadosBasicosTarefaCampoFilter;
import br.com.finchsolucoes.xgracco.domain.repository.DadosBasicosTarefaCamposJpaRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Implementação JPA do repositório da entidade DadosBasicosTarefaCampos.
 *
 * @author Thiago Fogar
 * @since 4.0
 */
@Repository
public class DadosBasicosTarefaCamposRepositoryImpl extends AbstractJpaRepository<DadosBasicosTarefaCampos, Long> implements DadosBasicosTarefaCamposJpaRepository {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public List<DadosBasicosTarefaCampos> find(Query<DadosBasicosTarefaCampos> query) {
        return createQuery(query, true).fetch();
    }

    @Override
    public long count(Query<DadosBasicosTarefaCampos> query) {
        return createQuery(query, false).fetchCount();
    }

    private JPAQuery<DadosBasicosTarefaCampos> createQuery(final Query<DadosBasicosTarefaCampos>query, boolean sortAndPaging) {
        final QDadosBasicosTarefaCampos qDadosBasicosTarefaCampos = QDadosBasicosTarefaCampos.dadosBasicosTarefaCampos;
        final QCampo qCampo = QCampo.campo;
        final QPessoa qPessoa = QPessoa.pessoa;
        final QFormulario qFormulario = QFormulario.formulario;
        final QCampoLista qCampoLista = QCampoLista.campoLista;

        final JPAQuery<DadosBasicosTarefaCampos> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qDadosBasicosTarefaCampos)
                .from(qDadosBasicosTarefaCampos)
                .join(qDadosBasicosTarefaCampos.campo, qCampo).fetchJoin()
                .innerJoin(qCampo.formulario, qFormulario).fetchJoin()
                .leftJoin(qDadosBasicosTarefaCampos.pessoa, qPessoa).fetchJoin()
                .leftJoin(qDadosBasicosTarefaCampos.campoLista, qCampoLista).fetchJoin();

        if (query.getFilter() != null && query.getFilter() instanceof DadosBasicosTarefaCampoFilter) {
            final DadosBasicosTarefaCampoFilter filter = (DadosBasicosTarefaCampoFilter) query.getFilter();

            if (filter.getIdProcesso() != null) {
                jpaQuery.where(qDadosBasicosTarefaCampos.dadosBasicosTarefa.processo.id.eq(filter.getIdProcesso()));
            }

            if (filter.getIdDadosBasicosTarefa() != null) {
                jpaQuery.where(qDadosBasicosTarefaCampos.dadosBasicosTarefa.id.eq(filter.getIdDadosBasicosTarefa()));
            }

            if (CollectionUtils.isNotEmpty(filter.getIdCampos())) {
                jpaQuery.where(filter.getIdCampos()
                        .stream()
                        .map(qDadosBasicosTarefaCampos.campo.id::eq)
                        .reduce(BooleanExpression::or)
                        .get());
            }
        }

        if (sortAndPaging) {
            applyPagination(jpaQuery, query.getPage(), query.getLimit());
            applySorter(jpaQuery, query.getSorter());
        }

        return jpaQuery;
    }

    @Override
    public Optional<DadosBasicosTarefaCampos> findById(Long id) {
        QDadosBasicosTarefaCampos qDadosBasicosTarefaCampos = QDadosBasicosTarefaCampos.dadosBasicosTarefaCampos;
        QCampo qCampo = QCampo.campo;
        QPessoa qPessoa = QPessoa.pessoa;
        QCampoLista qCampoLista = QCampoLista.campoLista;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(qDadosBasicosTarefaCampos)
                .from(qDadosBasicosTarefaCampos)
                .join(qDadosBasicosTarefaCampos.campo, qCampo)
                .leftJoin(qDadosBasicosTarefaCampos.pessoa, qPessoa)
                .leftJoin(qDadosBasicosTarefaCampos.campoLista, qCampoLista)
                .where(qDadosBasicosTarefaCampos.id.eq(id))
                .fetchOne());
    }

    @Override
    public void create(DadosBasicosTarefaCampos dadosBasicosTarefaCampos) {
        entityManager.persist(dadosBasicosTarefaCampos);
        entityManager.flush();
    }

    @Override
    public void delete(DadosBasicosTarefaCampos dadosBasicosTarefaCampos) {
        entityManager.remove(dadosBasicosTarefaCampos);
        entityManager.flush();
    }

    @Override
    public void update(DadosBasicosTarefaCampos dadosBasicosTarefaCampos) {
        Optional.ofNullable(entityManager.find(Arquivo.class, Optional.ofNullable(dadosBasicosTarefaCampos.getId()).orElseThrow(IdNullException::new))).orElseThrow(EntityNotFoundException::new);
        entityManager.merge(dadosBasicosTarefaCampos);
        entityManager.flush();
    }

    @Override
    public void deleteByDadosBasicosTarefa(DadosBasicosTarefa dadosBasicosTarefa) {

        final QDadosBasicosTarefaCampos qDadosBasicosTarefaCampos = QDadosBasicosTarefaCampos.dadosBasicosTarefaCampos;

        new JPADeleteClause(entityManager, qDadosBasicosTarefaCampos)
                .where(qDadosBasicosTarefaCampos.dadosBasicosTarefa.eq(dadosBasicosTarefa))
                .execute();
    }

    @Override
    public List<DadosBasicosTarefaCampos> findUploadsByProcessos(Set<Processo> processos) {

        final QDadosBasicosTarefaCampos qDadosBasicosTarefaCampos = QDadosBasicosTarefaCampos.dadosBasicosTarefaCampos;
        final QDadosBasicosTarefa qDadosBasicosTarefa = QDadosBasicosTarefa.dadosBasicosTarefa;
        final QCampo qCampo = QCampo.campo;
        QProcesso qProcesso = QProcesso.processo1;
        QProcessoCaseInstance qProcessoCaseInstance = QProcessoCaseInstance.processoCaseInstance;

        JPAQuery<DadosBasicosTarefaCampos> query = new JPAQueryFactory(entityManager)
                .select(qDadosBasicosTarefaCampos)
                .from(qDadosBasicosTarefaCampos)
                .join(qDadosBasicosTarefaCampos.dadosBasicosTarefa, qDadosBasicosTarefa)
                .join(qDadosBasicosTarefaCampos.campo, qCampo).fetchJoin()
                .join(qDadosBasicosTarefa.processo, qProcesso).fetchJoin()
                .where(qCampo.tipoCampo.eq(EnumTipoCampo.TIPO_CAMPO_UPLOAD))
                .where(qProcesso.in(processos));

        return query.fetch();
    }
}
