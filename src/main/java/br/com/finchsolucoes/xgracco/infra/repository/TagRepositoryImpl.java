package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.domain.query.impl.TagFilter;
import br.com.finchsolucoes.xgracco.domain.repository.TagJpaRepository;
import br.com.finchsolucoes.xgracco.domain.repository.TagRepository;
import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementação JPA do repositório da entidade Tag
 *
 * @author Felipi Berdun
 * @since 2.1
 */
@Repository
public class TagRepositoryImpl extends AbstractJpaRepository<Tag, Long> implements TagJpaRepository {

    private static final int MAX_SIZE_IN = 100;


    public List<Tag> find(Query<Tag> query) {
        final JPAQuery<Tag> jpaQuery = createQuery(query);
        final PathBuilder<Tag> builder = new PathBuilder<>(Tag.class, "tag");

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

        return jpaQuery.fetch();
    }


    public long count(Query<Tag> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<Tag> createQuery(Query<Tag> query) {
        final QTag qTag = QTag.tag;
        final JPAQuery<Tag> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qTag)
                .from(qTag);

        if (query.getFilter() != null && query.getFilter() instanceof TagFilter) {
            TagFilter filter = (TagFilter) query.getFilter();

            if (StringUtils.isNotBlank(filter.getNome())) {
                jpaQuery.where(qTag.nome.likeIgnoreCase("%" + filter.getNome() + "%"));
            }
        }

        return jpaQuery;
    }


    public Map<Tag, Long> findByProcessos(List<Processo> processos) {
        final QTag qTag = QTag.tag;
        final QProcesso qProcesso = QProcesso.processo1;

        return new JPAQueryFactory(entityManager)
                .selectDistinct(
                        qTag.id,
                        qTag.nome,
                        qProcesso.id.count()
                )
                .from(qProcesso)
                .join(qProcesso.tags, qTag)
                .where(qProcesso.in(processos))
                .groupBy(qTag.id, qTag.nome)
                .fetch()
                .stream()
                .collect(Collectors.toMap(
                        tuple -> new Tag(tuple.get(0, Long.class), tuple.get(1, String.class)),
                        tuple -> tuple.get(2, Long.class)));
    }

    @Override
    public void createProcessos(Tag tag, List<Processo> processos) {
        final QProcesso qProcesso = QProcesso.processo1;
        final QProcesso qProcessoTag = new QProcesso("processotag");
        final QTag qTag = QTag.tag;

        final List<Processo> processosSemTag = Lists.partition(processos, MAX_SIZE_IN)
                .stream()
                .flatMap(subProcessos -> new JPAQueryFactory(entityManager)
                        .select(QProcesso.create(qProcesso.id))
                        .from(qProcesso)
                        .where(qProcesso.in(subProcessos))
                        .where(JPAExpressions
                                .select(qTag.count())
                                .from(qProcessoTag)
                                .join(qProcessoTag.tags, qTag)
                                .where(qProcessoTag.eq(qProcesso))
                                .where(qTag.eq(tag))
                                .eq(0L))
                        .fetch()
                        .stream())
                .collect(Collectors.toList());

        if (!processosSemTag.isEmpty()) {
            final String sql = "INSERT INTO PROCESSO_TAG (FK_PROCESSO, FK_TAG) VALUES (?, ?)";

            entityManager.unwrap(Session.class).doWork(connection -> {
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    for (Processo processo : processosSemTag) {
                        statement.setLong(1, processo.getId());
                        statement.setLong(2, tag.getId());
                        statement.addBatch();
                        statement.clearParameters();
                    }

                    statement.executeBatch();
                }
            });

            entityManager.flush();
            entityManager.clear();
        }
    }

    @Override
    public void removeProcessos(Tag tag, List<Processo> processos) {
        entityManager.unwrap(Session.class).doWork(connection -> {
            for (List<Processo> subProcessos : Lists.partition(processos, MAX_SIZE_IN)) {
                final String sql = String.format("DELETE FROM PROCESSO_TAG WHERE FK_TAG = ? AND FK_PROCESSO IN (%s)",
                        subProcessos.stream().map(p -> "?").collect(Collectors.joining(",")));

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setLong(1, tag.getId());

                    int i = 2;
                    for (Processo subProcesso : subProcessos) {
                        statement.setLong(i, subProcesso.getId());
                        i++;
                    }

                    statement.execute();
                }
            }
        });

        entityManager.flush();
        entityManager.clear();
    }

    @Override
    public List<Tag> findByFila(Fila fila) {

        final QFila qFila = QFila.fila;
        final QTag qTag = QTag.tag;
        final QFilaTag qFilaTag = QFilaTag.filaTag;
        final QFilaEspera qFilaEspera = QFilaEspera.filaEspera;

        JPAQuery<Tag> jpaQuery = new JPAQueryFactory(entityManager)
                .selectDistinct(qTag)
                .from(qFilaTag)
                .join(qFilaTag.fila, qFila)
                .join(qFilaTag.tag, qTag)
                .where(qFila.ativo.eq(Boolean.TRUE).and(JPAExpressions
                        .select(qFilaEspera)
                        .from(qFilaEspera)
                        .where(qFilaEspera.filaAtivaEspera.eq(qFila))
                        .notExists()))
                .where(qFila.eq(fila));

        return jpaQuery.fetch();
    }

    @Override
    public List<Tag> findByAtendimentoFila(Fila fila) {

        final QDadosBasicosTarefa qDadosBasicosTarefa = QDadosBasicosTarefa.dadosBasicosTarefa;
        final QProcesso qProcesso = QProcesso.processo1;
        final QFilaTarefa qFilaTarefa = QFilaTarefa.filaTarefa;
        final QFila qFila = QFila.fila;
        final QTag qTag = QTag.tag;
        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;
        final QFilaEspera qFilaEspera = QFilaEspera.filaEspera;

        JPAQuery<Tag> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qTag)
                .from(qDadosBasicosTarefa)
                .join(qDadosBasicosTarefa.processo, qProcesso)
                .join(qProcesso.tags, qTag)
                .join(qDadosBasicosTarefa.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefa)
                .join(qFluxoTrabalhoTarefa.filas, qFilaTarefa)
                .join(qFilaTarefa.fila, qFila)
                .where(qDadosBasicosTarefa.dataConclusao.isNull())
                .where(qDadosBasicosTarefa.responsavel.isNull())
                .where(qFila.ativo.eq(Boolean.TRUE).and(JPAExpressions
                        .select(qFilaEspera)
                        .from(qFilaEspera)
                        .where(qFilaEspera.filaAtivaEspera.eq(qFila))
                        .notExists()))
                .where(qFila.eq(fila));

        return jpaQuery.fetch();
    }

    @Override
    public List<Tag> findByProcesso(Processo processo) {

        final QProcesso qProcesso = QProcesso.processo1;
        final QTag qTag = QTag.tag;
        final QProcessoTag qProcessoTag = QProcessoTag.processoTag;

        JPAQuery<Tag> jpaQuery = new JPAQueryFactory(entityManager)
                .selectDistinct(qTag)
                .from(qProcessoTag)
                .join(qProcessoTag.processo, qProcesso)
                .join(qProcessoTag.tag, qTag)
                .where(qProcesso.eq(processo));

        return jpaQuery.fetch();
    }

    @Override
    public List<Tag> findAllCache() {
        final QTag qTag = QTag.tag;

        JPAQuery<Tag> jpaQuery = new JPAQueryFactory(this.entityManager)
                .select(qTag)
                .from(qTag);

        return jpaQuery.fetch();
    }

    @Override
    public Optional<Tag> findOnlyTagByNome(String nome) {

        final QTag qTag = QTag.tag;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(QTag.create(
                        qTag.id,
                        qTag.nome
                ))
                .from(qTag)
                .where(qTag.nome.eq(nome)).fetchOne());
    }

}
