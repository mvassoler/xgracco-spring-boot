package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.ParteFilter;
import br.com.finchsolucoes.xgracco.domain.repository.ProcessoParteJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade Parte.
 *
 * @author Renan Gigliotti
 * @since 2.1
 */

@Repository
public class ProcessoParteRepositoryImpl extends AbstractJpaRepository<ProcessoParte, Long> implements ProcessoParteJpaRepository {


    public List<ProcessoParte> find(Query<ProcessoParte> query) {
        return find(null, query);
    }


    public List<ProcessoParte> find(Processo processo, Query<ProcessoParte> query) {

        final PathBuilder<ProcessoParte> path = new PathBuilder<>(ProcessoParte.class, "processoParte");
        final JPAQuery jpaQuery = createQuery(processo, query);

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

        return jpaQuery.fetch();
    }


    public Long count(Processo processo, Query<ProcessoParte> query) {
        return createQuery(processo, query).fetchCount();
    }

    private JPAQuery<ProcessoParte> createQuery(Processo processo, Query<ProcessoParte> query) {
        final QProcessoParte qProcessoParte = QProcessoParte.processoParte;
        final QTipoParte qTipoParte = QTipoParte.tipoParte;
        final QPessoa qPessoa = QPessoa.pessoa;
        final QProcesso qProcesso = QProcesso.processo1;

        final JPAQuery<ProcessoParte> jpaQuery = new JPAQueryFactory(entityManager)
                .select(QProcessoParte.create(
                        qProcessoParte.id,
                        QTipoParte.create(
                                qTipoParte.id,
                                qTipoParte.descricao
                        ),
                        QPessoa.create(
                                qPessoa.id,
                                qPessoa.nomeRazaoSocial,
                                qPessoa.apelidoFantasia,
                                qPessoa.cpfCnpj
                        ),
                        QProcesso.create(
                                qProcesso.id,
                                qProcesso.processoUnico,
                                qProcesso.processoJudicialAntigo
                        )
                ))
                .from(qProcessoParte)
                .join(qProcessoParte.pessoa, qPessoa)
                .join(qProcessoParte.tipoParte, qTipoParte)
                .join(qProcessoParte.processo, qProcesso);

        if(!Objects.isNull(processo)){
            jpaQuery.where(qProcesso.eq(processo));
        }

        // filter
        if (query.getFilter() != null && query.getFilter() instanceof ParteFilter) {
            final ParteFilter parteFilter = (ParteFilter) query.getFilter();

            // tipoParte
            if (parteFilter.getIdTipoParte() != null) {
                jpaQuery.where(qTipoParte.id.eq(parteFilter.getIdTipoParte()));
            }

            // pessoa
            if (parteFilter.getPessoa() != null) {
                jpaQuery.where(qPessoa.eq(parteFilter.getPessoa()));
            }
        }

        return jpaQuery;
    }


    public Optional<ProcessoParte> findById(Processo processo, Long id) {
        final QProcessoParte qProcessoParte = QProcessoParte.processoParte;
        final QTipoParte qTipoParte = QTipoParte.tipoParte;
        final QPessoa qPessoa = QPessoa.pessoa;
        final QProcesso qProcesso = QProcesso.processo1;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(QProcessoParte.create(
                        qProcessoParte.id,
                        QTipoParte.create(
                                qTipoParte.id,
                                qTipoParte.descricao
                        ),
                        QPessoa.create(
                                qPessoa.id,
                                qPessoa.nomeRazaoSocial,
                                qPessoa.apelidoFantasia,
                                qPessoa.cpfCnpj
                        ),
                        QProcesso.create(
                                qProcesso.id
                        )
                ))
                .from(qProcessoParte)
                .join(qProcessoParte.pessoa, qPessoa)
                .join(qProcessoParte.tipoParte, qTipoParte)
                .join(qProcessoParte.processo, qProcesso)
                .where(qProcesso.eq(processo))
                .where(qProcessoParte.id.eq(id))
                .fetchOne());
    }


    public List<ProcessoParte> findByProcesso(Processo processo) {
        final QProcessoParte qProcessoParte = QProcessoParte.processoParte;
        final QTipoParte qTipoParte = QTipoParte.tipoParte;
        final QPessoa qPessoa = QPessoa.pessoa;
        final QProcesso qProcesso = QProcesso.processo1;

        return new JPAQueryFactory(entityManager)
                .select(QProcessoParte.create(
                        qProcessoParte.id,
                        QTipoParte.create(
                                qTipoParte.id,
                                qTipoParte.descricao
                        ),
                        QPessoa.create(
                                qPessoa.id,
                                qPessoa.nomeRazaoSocial
                        ),
                        QProcesso.create(
                                qProcesso.id,
                                qProcesso.numero
                        )
                ))
                .from(qProcessoParte)
                .join(qProcessoParte.tipoParte, qTipoParte)
                .join(qProcessoParte.processo, qProcesso)
                .join(qProcessoParte.pessoa, qPessoa)
                .where(qProcesso.eq(processo))
                .orderBy(qTipoParte.descricao.asc())
                .fetch();
    }


    public List<ProcessoParte> findAllRelations(ProcessoParte parte){
        final QProcessoParte qProcessoParte = QProcessoParte.processoParte;

        return new JPAQueryFactory(entityManager)
                .select(qProcessoParte)
                .from(qProcessoParte)

                .where(qProcessoParte.pessoa.id.eq(parte.getPessoa().getId()))
                .where(qProcessoParte.tipoParte.id.eq(parte.getTipoParte().getId()))
                .where(qProcessoParte.processo.id.eq(parte.getProcesso().getId())).fetch();
    }

}
