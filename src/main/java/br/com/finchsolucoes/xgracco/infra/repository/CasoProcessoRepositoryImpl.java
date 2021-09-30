package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.exception.EntityNotFoundException;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.domain.query.impl.CasoProcessoFilter;
import br.com.finchsolucoes.xgracco.domain.repository.CasoProcessoJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade CasoProcesso
 *
 * @author Jordano
 * @since 2.1
 */
@Repository
public class CasoProcessoRepositoryImpl implements CasoProcessoJpaRepository {

    @PersistenceContext
    protected EntityManager entityManager;

    /**
     * Consulta os relacionamentos entre caso e processo
     *
     * @param caso
     * @param query
     * @return
     */
    @Override
    public List<CasoProcesso> find(Caso caso, Query<CasoProcesso> query) {
        return createQuery(caso, query, true).fetch();
    }

    /**
     * Retorna quantidade de relacionamentos entre caso e processo
     *
     * @param caso
     * @param query
     * @return
     */
    @Override
    public long count(Caso caso, Query<CasoProcesso> query) {
        return createQuery(caso, query, false).fetchCount();
    }

    /**
     * Cria query para ser utilizada no método find
     *
     * @param caso
     * @param query
     * @return
     */
    private JPAQuery<CasoProcesso> createQuery(Caso caso, Query<CasoProcesso> query, boolean sortAndPaging) {
        final QCaso qCaso = QCaso.caso;
        final QUsuario qUsuario = QUsuario.usuario;
        final QPessoa qPessoa = QPessoa.pessoa;
        final QProcesso qProcesso = QProcesso.processo1;
        final QCarteira qCarteira = QCarteira.carteira;
        final QCasoProcesso qCasoProcesso = new QCasoProcesso("casoProcesso");
        final QCasoProcesso qCasoProcessoFilhos = new QCasoProcesso("casoProcessoFilhos");
        final QCasoProcesso qCasoProcessoPai = new QCasoProcesso("casoProcessoPai");
        final PathBuilder<CasoProcesso> builder = new PathBuilder<>(CasoProcesso.class, "casoProcesso");

        final JPAQuery<CasoProcesso> jpaQuery;

        if (sortAndPaging) {
            jpaQuery = new JPAQueryFactory(entityManager)
                    .select(QCasoProcesso.create(
                            qCasoProcesso.id,
                            QCaso.create(qCaso.id, qCaso.descricao, QUsuario.create(qUsuario.id, qUsuario.login, QPessoa.create(qPessoa.id, qPessoa.nomeRazaoSocial))),
                            QProcesso.create(qProcesso.id, qProcesso.status, qProcesso.processoUnico, QCarteira.create(qCarteira.id, qCarteira.uid, qCarteira.descricao)),
                            QCasoProcesso.create(qCasoProcessoPai.id),
                            qCasoProcessoFilhos.count()
                    ));
        } else {
            jpaQuery = new JPAQueryFactory(entityManager)
                    .selectDistinct(qCasoProcesso);
        }

        jpaQuery.from(qCasoProcesso)
                .innerJoin(qCasoProcesso.caso, qCaso)
                .innerJoin(qCasoProcesso.processo, qProcesso)
                .innerJoin(qProcesso.carteira, qCarteira)
                .leftJoin(qCaso.responsavel, qUsuario)
                .leftJoin(qUsuario.pessoa, qPessoa)
                .leftJoin(qCasoProcesso.processos, qCasoProcessoFilhos)
                .leftJoin(qCasoProcesso.casoProcessoPai, qCasoProcessoPai);

        Optional.ofNullable(caso).map(Caso::getId).ifPresent(c -> jpaQuery.where(qCaso.id.eq(c)));

        if (query.getFilter() != null && query.getFilter() instanceof CasoProcessoFilter) {
            final CasoProcessoFilter filter = (CasoProcessoFilter) query.getFilter();

            if (filter.getProcessoId() == null) {
                jpaQuery.where(filter.getCasoProcessoPaiId() == null ? qCasoProcessoPai.id.isNull() : qCasoProcessoPai.id.eq(filter.getCasoProcessoPaiId()));
            } else {
                jpaQuery.where(qCasoProcesso.processo.id.eq(filter.getProcessoId()));
            }
        }

        if (sortAndPaging) {
            jpaQuery.groupBy(qCasoProcesso.id)
                    .groupBy(qCaso.id)
                    .groupBy(qCaso.descricao)
                    .groupBy(qUsuario.id)
                    .groupBy(qUsuario.login)
                    .groupBy(qPessoa.id)
                    .groupBy(qPessoa.nomeRazaoSocial)
                    .groupBy(qProcesso.id)
                    .groupBy(qProcesso.status)
                    .groupBy(qProcesso.processoUnico)
                    .groupBy(qCarteira.id)
                    .groupBy(qCarteira.uid)
                    .groupBy(qCarteira.descricao)
                    .groupBy(qCasoProcessoPai.id);

            if (query.getSorter() != null && StringUtils.isNotBlank(query.getSorter().getProperty())) {
                if (query.getSorter().getDirection().equals(Sorter.Direction.ASC)) {
                    jpaQuery.orderBy(builder.getString(query.getSorter().getProperty()).asc());
                } else {
                    jpaQuery.orderBy(builder.getString(query.getSorter().getProperty()).desc());
                }
            } else {
                jpaQuery.orderBy(builder.getString("id").asc());
            }

            if (Optional.ofNullable(query.getPage()).orElse(0L) > 0L) {
                jpaQuery.offset((query.getPage() - 1L) * query.getLimit());
            }

            jpaQuery.limit(query.getLimit());
        }

        return jpaQuery;
    }

    /**
     * Consulta CasoProcesso através do ID.
     *
     * @param id
     * @return
     */
    @Override
    public Optional<CasoProcesso> findById(Long id) {
        return Optional.ofNullable(entityManager.find(CasoProcesso.class, id));
    }

    /**
     * Consulta processo através do ID do caso e do ID do processo
     *
     * @param caso
     * @param id
     */
    @Override
    public Optional<CasoProcesso> findById(Caso caso, Long id) {
        final QCaso qCaso = QCaso.caso;
        final QUsuario qUsuario = QUsuario.usuario;
        final QPessoa qPessoa = QPessoa.pessoa;
        final QProcesso qProcesso = QProcesso.processo1;
        final QCarteira qCarteira = QCarteira.carteira;
        final QCasoProcesso qCasoProcesso = new QCasoProcesso("casoProcesso");
        final QCasoProcesso qCasoProcessoFilhos = new QCasoProcesso("casoProcessoFilhos");
        final QCasoProcesso qCasoProcessoPai = new QCasoProcesso("casoProcessoPai");

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(QCasoProcesso.create(
                        qCasoProcesso.id,
                        QCaso.create(qCaso.id, qCaso.descricao, QUsuario.create(qUsuario.id, qUsuario.login, QPessoa.create(qPessoa.id, qPessoa.nomeRazaoSocial))),
                        QProcesso.create(qProcesso.id, qProcesso.status, qProcesso.processoUnico, QCarteira.create(qCarteira.id, qCarteira.uid, qCarteira.descricao)),
                        QCasoProcesso.create(qCasoProcessoPai.id),
                        qCasoProcessoFilhos.count()
                ))
                .from(qCasoProcesso)
                .innerJoin(qCasoProcesso.caso, qCaso)
                .innerJoin(qCasoProcesso.processo, qProcesso)
                .innerJoin(qProcesso.carteira, qCarteira)
                .leftJoin(qCaso.responsavel, qUsuario)
                .leftJoin(qUsuario.pessoa, qPessoa)
                .leftJoin(qCasoProcesso.processos, qCasoProcessoFilhos)
                .leftJoin(qCasoProcesso.casoProcessoPai, qCasoProcessoPai)
                .where(qCaso.eq(caso))
                .where(qCasoProcesso.id.eq(id))
                .groupBy(qCasoProcesso.id)
                .groupBy(qCaso.id)
                .groupBy(qCaso.descricao)
                .groupBy(qUsuario.id)
                .groupBy(qUsuario.login)
                .groupBy(qPessoa.id)
                .groupBy(qPessoa.nomeRazaoSocial)
                .groupBy(qProcesso.id)
                .groupBy(qProcesso.status)
                .groupBy(qProcesso.processoUnico)
                .groupBy(qCarteira.id)
                .groupBy(qCarteira.uid)
                .groupBy(qCarteira.descricao)
                .groupBy(qCasoProcessoPai.id)
                .fetchOne());
    }

    /**
     * Consulta processo através do ID do caso e do ID do processo
     *
     * @param caso
     * @param idProcesso
     */
    @Override
    public Optional<CasoProcesso> findByProcesso(Caso caso, Long idProcesso) {
        final QCaso qCaso = QCaso.caso;
        final QProcesso qProcesso = QProcesso.processo1;
        final QCasoProcesso qCasoProcesso = QCasoProcesso.casoProcesso;
        final QPessoa qParteInteressada = new QPessoa("parteInteressada");
        final QPessoa qParteContraria = new QPessoa("parteContraria");
        final QCarteira qCarteira = QCarteira.carteira;
        final QAcao qAcao = QAcao.acao;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(QCasoProcesso.create(
                        qCasoProcesso.id,
                        QCaso.create(qCaso.id),
                        QProcesso.create(
                                qProcesso.id,
                                qProcesso.numero,
                                qProcesso.processoUnico,
                                qProcesso.tipoProcesso,
                                QAcao.create(qAcao.id, qAcao.descricao),
                                QPessoa.create(qParteInteressada.id, qParteInteressada.nomeRazaoSocial),
                                QPessoa.create(qParteContraria.id, qParteContraria.nomeRazaoSocial),
                                qCarteira)))
                .from(qCasoProcesso)
                .innerJoin(qCasoProcesso.caso, qCaso)
                .innerJoin(qCasoProcesso.processo, qProcesso)
                .innerJoin(qProcesso.parteInteressada, qParteInteressada)
                .innerJoin(qProcesso.parteContraria, qParteContraria)
                .innerJoin(qProcesso.carteira, qCarteira)
                .innerJoin(qProcesso.acao, qAcao)
                .where(qCaso.eq(caso))
                .where(qProcesso.id.eq(idProcesso))
                .fetchOne());
    }

    /**
     * Cria relacionamento entre caso e processo
     *
     * @param casoProcesso
     */
    @Override
    public void create(CasoProcesso casoProcesso) {
        entityManager.persist(casoProcesso);
        entityManager.flush();
    }

    /**
     * Atualiza o relacionamento entre caso e processo
     *
     * @param casoProcesso
     */
    @Override
    public void update(CasoProcesso casoProcesso) {
        entityManager.merge(casoProcesso);
        entityManager.flush();
    }

    /**
     * Remove CasoProcesso através do ID.
     *
     * @param id
     */
    @Override
    public void removeById(Long id, boolean recursivo) {
        CasoProcesso casoProcesso = findById(id).orElseThrow(EntityNotFoundException::new);
        QCasoProcesso qCasoProcesso = QCasoProcesso.casoProcesso;

        if (recursivo) {
            List<Long> ids = new ArrayList<>();
            recuperaIdsFilhosRecursivamente(casoProcesso, ids);

            new JPAQueryFactory(entityManager)
                    .delete(qCasoProcesso)
                    .where(qCasoProcesso.id.in(ids))
                    .execute();
        } else {
            Long idCasoProcessoPai = casoProcesso.getCasoProcessoPai() != null ? casoProcesso.getCasoProcessoPai().getId() : null;
            new JPAQueryFactory(entityManager)
                    .update(qCasoProcesso)
                    .set(qCasoProcesso.casoProcessoPai.id, idCasoProcessoPai)
                    .where(qCasoProcesso.casoProcessoPai.id.eq(id))
                    .execute();

            entityManager.remove(casoProcesso);
        }

        entityManager.flush();
    }

    /**
     * Consulta os ids dos filhos dos relacionamentos entre caso e processo recursivamente
     *
     * @param casoProcesso
     * @param ids
     */
    private void recuperaIdsFilhosRecursivamente(CasoProcesso casoProcesso, List<Long> ids) {
        ids.add(casoProcesso.getId());

        if (casoProcesso.getProcessos() != null && !casoProcesso.getProcessos().isEmpty()) {
            for (CasoProcesso cp : casoProcesso.getProcessos()) {
                recuperaIdsFilhosRecursivamente(cp, ids);
            }
        }
    }
}