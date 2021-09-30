package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.InformacoesAdicionaisFilter;
import br.com.finchsolucoes.xgracco.domain.repository.InformacoesAdicionaisJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade InformacoesAdicionais.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
@Repository
public class InformacoesAdicionaisRepositoryImpl extends AbstractJpaRepository<InformacoesAdicionais, Long> implements InformacoesAdicionaisJpaRepository {

    @Override
    public List<InformacoesAdicionais> find(Query<InformacoesAdicionais> query) {
        final PathBuilder<Banco> path = new PathBuilder<>(Banco.class, "informacoesAdicionais");
        final JPAQuery jpaQuery = createQuery(query);

        if (query.getSorter() != null && StringUtils.isNotBlank(query.getSorter().getProperty())) {
            if (query.getSorter().isDesc()) {
                jpaQuery.orderBy(path.getString(query.getSorter().getProperty()).desc());
            } else {
                jpaQuery.orderBy(path.getString(query.getSorter().getProperty()).asc());
            }
        } else {
            jpaQuery.orderBy(path.getString("id").asc());
        }

        if (Optional.ofNullable(query.getPage()).orElse(0L) > 0L) {
            jpaQuery.offset(((query.getPage() - 1L) * query.getLimit()));
        }

        jpaQuery.limit(query.getLimit());
        return jpaQuery.fetch();
    }

    @Override
    public long count(Query<InformacoesAdicionais> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<InformacoesAdicionais> createQuery(Query<InformacoesAdicionais> query) {
        QInformacoesAdicionais qInformacoesAdicionais = QInformacoesAdicionais.informacoesAdicionais;
        QPessoa qPessoa = QPessoa.pessoa;
        QProcesso qProcesso = QProcesso.processo1;
        QProcessoGarantia qGarantia = QProcessoGarantia.processoGarantia;
        QCampo qCampo = QCampo.campo;
        QGrupoCampo qGrupoCampo = QGrupoCampo.grupoCampo;

        final JPAQuery<InformacoesAdicionais> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qInformacoesAdicionais)
                .from(qInformacoesAdicionais)
                .leftJoin(qInformacoesAdicionais.pessoa, qPessoa)
                .leftJoin(qInformacoesAdicionais.processo, qProcesso)
                .leftJoin(qInformacoesAdicionais.processoGarantia, qGarantia)
                .leftJoin(qInformacoesAdicionais.campo, qCampo).fetchJoin()
                .leftJoin(qCampo.grupoCampo, qGrupoCampo).fetchJoin();

        if (Objects.nonNull(query.getFilter()) && query.getFilter() instanceof InformacoesAdicionaisFilter) {
            final InformacoesAdicionaisFilter informacoesFilter = (InformacoesAdicionaisFilter) query.getFilter();

            if (StringUtils.isNotEmpty(informacoesFilter.getConteudo())) {
                jpaQuery.where(qInformacoesAdicionais.conteudo.likeIgnoreCase("%" + informacoesFilter.getConteudo() + "%"));
            }

            if (informacoesFilter.getPessoa() != null) {
                jpaQuery.where(qInformacoesAdicionais.pessoa.eq(informacoesFilter.getPessoa()));
            }

            if (informacoesFilter.getProcesso() != null) {
                jpaQuery.where(qInformacoesAdicionais.processo.eq(informacoesFilter.getProcesso()));
            }

            if (informacoesFilter.getProcessoGarantia() != null) {
                jpaQuery.where(qInformacoesAdicionais.processoGarantia.eq(informacoesFilter.getProcessoGarantia()));
            }

            if (informacoesFilter.getCampo() != null) {
                jpaQuery.where(qInformacoesAdicionais.campo.eq(informacoesFilter.getCampo()));
            }

            if (informacoesFilter.getCampoAtivo() != null) {
                jpaQuery.where(qInformacoesAdicionais.campo.ativo.eq(informacoesFilter.getCampoAtivo()));
            }

        }

        return jpaQuery;
    }

    @Override
    public List<InformacoesAdicionais> findByProcessoGarantia(Processo processo, ProcessoGarantia garantia) {
        QInformacoesAdicionais qInformacoesAdicionais = QInformacoesAdicionais.informacoesAdicionais;
        QProcesso qProcesso = QProcesso.processo1;
        QProcessoGarantia qGarantia = QProcessoGarantia.processoGarantia;
        QCampo qCampo = QCampo.campo;

        return new JPAQueryFactory(entityManager)
                .select(QInformacoesAdicionais.create(
                        qInformacoesAdicionais.id,
                        QCampo.create(
                                qCampo.id,
                                qCampo.descricao
                        ),
                        qInformacoesAdicionais.conteudo,
                        QProcesso.create(
                                qProcesso.id,
                                qProcesso.numero)
                        )
                )
                .from(qInformacoesAdicionais)
                .join(qInformacoesAdicionais.campo, qCampo)
                .join(qInformacoesAdicionais.processoGarantia, qGarantia)
                .join(qGarantia.processo, qProcesso)
                .where(qProcesso.eq(processo))
                .where(qGarantia.eq(garantia))
                .fetch();
    }

    @Override
    public InformacoesAdicionais findByCampoProcesso(Campo campo, Processo processo) {
        QInformacoesAdicionais qInformacoesAdicionais = QInformacoesAdicionais.informacoesAdicionais;
        return new JPAQueryFactory(entityManager)
                .select(qInformacoesAdicionais)
                .from(qInformacoesAdicionais)
                .where(qInformacoesAdicionais.campo.eq(campo)
                        .and(qInformacoesAdicionais.processo.eq(processo))).fetchOne();
    }

    @Override
    public void removeByProcesso(Processo processo) {

        QInformacoesAdicionais qInformacoesAdicionais = QInformacoesAdicionais.informacoesAdicionais;

        new JPADeleteClause(entityManager, qInformacoesAdicionais)
                .where(qInformacoesAdicionais.processo.eq(processo))
                .execute();
    }

    @Override
    public void removeByPessoa(Pessoa pessoa) {
        QInformacoesAdicionais qInformacoesAdicionais = QInformacoesAdicionais.informacoesAdicionais;

        new JPADeleteClause(entityManager, qInformacoesAdicionais)
                .where(qInformacoesAdicionais.pessoa.eq(pessoa))
                .execute();
    }
}
