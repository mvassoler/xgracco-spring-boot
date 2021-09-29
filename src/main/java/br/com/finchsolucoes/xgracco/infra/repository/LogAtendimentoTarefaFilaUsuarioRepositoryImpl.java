package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.enums.EnumAcaoLogAtendimentoTarefa;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.LogAtendimentoTarefaFilaUsuarioFilter;
import br.com.finchsolucoes.xgracco.domain.repository.LogAtendimentoTarefaFilaUsuarioJpaRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade LogAtendimentoTarefaFilaUsuario
 *
 * @author paulo.marcon
 * @since 5.4.0
 */
@Repository
public class LogAtendimentoTarefaFilaUsuarioRepositoryImpl extends AbstractJpaRepository<LogAtendimentoTarefaFilaUsuario, Long> implements LogAtendimentoTarefaFilaUsuarioJpaRepository {

    @Override
    public List<LogAtendimentoTarefaFilaUsuario> find(Query<LogAtendimentoTarefaFilaUsuario> query) {
        final PathBuilder<LogAtendimentoTarefaFilaUsuario> path = new PathBuilder<>(LogAtendimentoTarefaFilaUsuario.class, "logAtendimentoTarefaFilaUsuario");
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
        jpaQuery.limit(query.getLimit());

        return jpaQuery.fetch();
    }

    @Override
    public long count(Query<LogAtendimentoTarefaFilaUsuario> query) {
        return this.createQuery(query).fetchCount();
    }

    private JPAQuery<LogAtendimentoTarefaFilaUsuario> createQuery(Query<LogAtendimentoTarefaFilaUsuario> query) {
        final QLogAtendimentoTarefaFilaUsuario qLogAtendimentoTarefaFilaUsuario = QLogAtendimentoTarefaFilaUsuario.logAtendimentoTarefaFilaUsuario;
        final QDadosBasicosTarefa qDadosBasicosTarefa = QDadosBasicosTarefa.dadosBasicosTarefa;
        final QUsuario qUsuario = QUsuario.usuario;
        final QFila qFila = QFila.fila;

        final JPAQuery<LogAtendimentoTarefaFilaUsuario> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qLogAtendimentoTarefaFilaUsuario)
                .from(qLogAtendimentoTarefaFilaUsuario)
                .join(qLogAtendimentoTarefaFilaUsuario.dadosBasicosTarefa, qDadosBasicosTarefa)
                .join(qLogAtendimentoTarefaFilaUsuario.usuario, qUsuario)
                .join(qLogAtendimentoTarefaFilaUsuario.fila, qFila);

        if (Objects.nonNull(query)) {
            if (Objects.nonNull(query.getFilter()) && query.getFilter() instanceof LogAtendimentoTarefaFilaUsuarioFilter) {
                final LogAtendimentoTarefaFilaUsuarioFilter filter = (LogAtendimentoTarefaFilaUsuarioFilter) query.getFilter();

                if (Objects.nonNull(filter.getDataInicio()) && Objects.nonNull(filter.getDataFinal())) {
                    jpaQuery.where(qLogAtendimentoTarefaFilaUsuario.dataInicio.goe(filter.getDataInicio())
                            .and(qLogAtendimentoTarefaFilaUsuario.dataEncerramento.loe(filter.getDataFinal())));
                }

                if (CollectionUtils.isNotEmpty(filter.getAcoes())) {
                    jpaQuery.where(filter.getAcoes()
                            .stream()
                            .map(qLogAtendimentoTarefaFilaUsuario.acao::eq)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                if (CollectionUtils.isNotEmpty(filter.getDadosBasicosTarefas())) {
                    jpaQuery.where(filter.getDadosBasicosTarefas()
                            .stream()
                            .map(qLogAtendimentoTarefaFilaUsuario.dadosBasicosTarefa.id::eq)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                if (CollectionUtils.isNotEmpty(filter.getUsuarios())) {
                    jpaQuery.where(filter.getUsuarios()
                            .stream()
                            .map(qLogAtendimentoTarefaFilaUsuario.usuario.id::eq)
                            .reduce(BooleanExpression::or)
                            .get());
                }

                if (CollectionUtils.isNotEmpty(filter.getFilas())) {
                    jpaQuery.where(filter.getUsuarios()
                            .stream()
                            .map(qLogAtendimentoTarefaFilaUsuario.usuario.id::eq)
                            .reduce(BooleanExpression::or)
                            .get());
                }
            }
        }

        return jpaQuery;
    }

    @Override
    public Optional<LogAtendimentoTarefaFilaUsuario> find(DadosBasicosTarefa dbt, Usuario usuario, Fila fila, EnumAcaoLogAtendimentoTarefa acao) {
        QLogAtendimentoTarefaFilaUsuario qLog = QLogAtendimentoTarefaFilaUsuario.logAtendimentoTarefaFilaUsuario;
        final JPAQuery<LogAtendimentoTarefaFilaUsuario> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qLog)
                .from(qLog);

        if (Objects.nonNull(dbt)) {
            jpaQuery.where(qLog.dadosBasicosTarefa.eq(dbt));
        }

        if (Objects.nonNull(usuario)) {
            jpaQuery.where(qLog.usuario.eq(usuario));
        }

        if (Objects.nonNull(fila)) {
            jpaQuery.where(qLog.fila.eq(fila));
        }

        if (Objects.nonNull(acao)) {
            jpaQuery.where(qLog.acao.eq(acao));
        }

        return Optional.ofNullable(jpaQuery.fetchOne());
    }

}
