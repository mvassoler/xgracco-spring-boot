package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.enums.EnumFuncao;
import br.com.finchsolucoes.xgracco.domain.enums.EnumStatusTarefa;
import br.com.finchsolucoes.xgracco.domain.enums.EnumTarefaStatus;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.DadosBasicosTarefaFilter;
import br.com.finchsolucoes.xgracco.domain.repository.DadosBasicosTarefaJpaRepository;
import br.com.finchsolucoes.xgracco.domain.utils.DateUtils;
import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Implementação JPA do repositório da entidade DadosBasicosTarefa.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
@Repository
public class DadosBasicosTarefaRepositoryImpl extends AbstractJpaRepository<DadosBasicosTarefa, Long> implements DadosBasicosTarefaJpaRepository {

    //TODO - ACERTAR ESTA CLASSE

    //private TagService tagService;

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public List<DadosBasicosTarefa> find(Query<DadosBasicosTarefa> query) {
        final PathBuilder<DadosBasicosTarefa> path = new PathBuilder<>(DadosBasicosTarefa.class, "dadosBasicosTarefa");
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
        if (query.getLimit() != Query.NO_LIMIT)
            jpaQuery.limit(query.getLimit());

        return jpaQuery.fetch();
    }

    private JPAQuery<DadosBasicosTarefa> createQuery(Query<DadosBasicosTarefa> query) {

        final QDadosBasicosTarefa qDadosBasicosTarefa = QDadosBasicosTarefa.dadosBasicosTarefa;
        final QDadosBasicosTarefa qDadosBasicosTarefaPai = new QDadosBasicosTarefa("dadosBasicosTarefaPai");
        final QPessoa qResponsavel = new QPessoa("responsavel");
        final QPessoa qRealizadoPor = new QPessoa("realizadoPor");
        final QProcesso qProcesso = QProcesso.processo1;
        final QTarefa qTarefa = QTarefa.tarefa;
        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;
        final QStatusFinal qStatusFinal = QStatusFinal.statusFinal;

        final JPAQuery<DadosBasicosTarefa> jpaQuery = new JPAQueryFactory(entityManager)
                .select(QDadosBasicosTarefa.create(
                        qDadosBasicosTarefa.id,
                        QFluxoTrabalhoTarefa.create(
                                qFluxoTrabalhoTarefa.id,
                                QTarefa.create(
                                        qTarefa.id,
                                        qTarefa.nome
                                )
                        ),
                        QProcesso.create(
                                qProcesso.id,
                                qProcesso.processoUnico,
                                qProcesso.processoJudicialAntigo
                        ),
                        qDadosBasicosTarefa.dataInicio,
                        qDadosBasicosTarefa.dataAgendamento,
                        qDadosBasicosTarefa.dataPrazoFatal,
                        QPessoa.create(
                                qResponsavel.id,
                                qResponsavel.nomeRazaoSocial,
                                qResponsavel.apelidoFantasia,
                                qResponsavel.rgInscricaoEstadual,
                                qResponsavel.cpfCnpj
                        ),
                        QPessoa.create(
                                qRealizadoPor.id,
                                qRealizadoPor.nomeRazaoSocial,
                                qRealizadoPor.apelidoFantasia,
                                qRealizadoPor.rgInscricaoEstadual,
                                qRealizadoPor.cpfCnpj
                        ),
                        qDadosBasicosTarefa.dataConclusao,
                        qDadosBasicosTarefa.rotulo,
                        qDadosBasicosTarefa.memo,
                        qDadosBasicosTarefa.dadosBasicosTarefaPai,
                        qDadosBasicosTarefa.origemConclusaoTarefa,
                        qDadosBasicosTarefa.status,
                        qDadosBasicosTarefa.local,
                        QStatusFinal.create(
                                qStatusFinal.id,
                                qStatusFinal.descricao
                        )
                        )
                )
                .from(qDadosBasicosTarefa)
                .leftJoin(qDadosBasicosTarefa.responsavel, qResponsavel)
                .leftJoin(qDadosBasicosTarefa.realizadoPor, qRealizadoPor)
                .leftJoin(qDadosBasicosTarefa.dadosBasicosTarefaPai, qDadosBasicosTarefaPai)
                .join(qDadosBasicosTarefa.processo, qProcesso)
                .join(qDadosBasicosTarefa.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefa)
                .join(qFluxoTrabalhoTarefa.tarefa, qTarefa)
                .leftJoin(qDadosBasicosTarefa.statusFinal, qStatusFinal);

        if (query.getFilter() != null && query.getFilter() instanceof DadosBasicosTarefaFilter) {
            final DadosBasicosTarefaFilter filter = (DadosBasicosTarefaFilter) query.getFilter();

            if (filter.getProcesso() != null) {
                jpaQuery.where(qDadosBasicosTarefa.processo.eq(filter.getProcesso()));
            }

            if (filter.getListFluxoTrabalhoTarefa() != null) {
                jpaQuery.where(qFluxoTrabalhoTarefa.in(filter.getListFluxoTrabalhoTarefa()));
            }

            if (filter.getNome() != null) {
                jpaQuery.where(filter.getNome()
                        .stream()
                        .map(qTarefa.nome::equalsIgnoreCase)
                        .reduce(BooleanExpression::or)
                        .get());
            }

            if (filter.getDescricaoStatusFinal() != null) {
                jpaQuery.where(filter.getDescricaoStatusFinal()
                        .stream()
                        .map(qDadosBasicosTarefa.statusFinal.descricao::equalsIgnoreCase)
                        .reduce(BooleanExpression::or)
                        .get());
            }

            if (filter.getResponsavel() != null) {
                jpaQuery.where(qDadosBasicosTarefa.responsavel.eq(filter.getResponsavel()));
            }

            if (filter.getAgendadoPara() != null) {
                jpaQuery.where(qDadosBasicosTarefa.agendadoPara.eq(filter.getAgendadoPara()));
            }

            if (filter.getRealizadoPor() != null) {
                jpaQuery.where(qDadosBasicosTarefa.realizadoPor.eq(filter.getRealizadoPor()));
            }

            if (StringUtils.isNotBlank(filter.getPalavraChave())) {
                jpaQuery.where(qDadosBasicosTarefa.memo.containsIgnoreCase(filter.getPalavraChave()));
            }

            if (filter.getDadosBasicosTarefaPai() != null) {
                jpaQuery.where(qDadosBasicosTarefa.dadosBasicosTarefaPai.eq(filter.getDadosBasicosTarefaPai()));
            }

            if (Objects.nonNull(filter.getDataConclusaoInicial())) {
                jpaQuery.where(qDadosBasicosTarefa.dataConclusao.goe(filter.getDataConclusaoInicial()));
            }

            if (Objects.nonNull(filter.getDataConclusaoFinal())) {
                jpaQuery.where(qDadosBasicosTarefa.dataConclusao.loe(filter.getDataConclusaoFinal()));
            }

            if (Objects.nonNull(filter.getStatusTarefa()) && !filter.getStatusTarefa().isEmpty()) {
                jpaQuery.where(
                        filter.getStatusTarefa()
                                .stream()
                                .map(s -> qDadosBasicosTarefa.status.eq(s))
                                .reduce(BooleanExpression::or)
                                .get()
                );
            }

            aplicarFiltroDataAgendamentoEStatus(jpaQuery, filter);

        }

        return jpaQuery;
    }


    @Override
    public Optional<DadosBasicosTarefa> findById(Long id) {
        final QDadosBasicosTarefa qDadosBasicosTarefa = QDadosBasicosTarefa.dadosBasicosTarefa;
        final QDadosBasicosTarefa qDadosBasicosTarefaPai = new QDadosBasicosTarefa("dadosBasicosTarefaPai");
        final QPessoa qResponsavel = new QPessoa("responsavel");
        final QPessoa qRealizadoPor = new QPessoa("realizadoPor");
        final QPessoa parteContraria = new QPessoa("parteContraria");
        final QProcessoDespesas qProcessoDespesas = QProcessoDespesas.processoDespesas;
        final QProcesso qProcesso = QProcesso.processo1;
        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;
        final QTarefa qTarefa = QTarefa.tarefa;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(QDadosBasicosTarefa.create(
                        qDadosBasicosTarefa.id,
                        QFluxoTrabalhoTarefa.create(
                                qFluxoTrabalhoTarefa.id,
                                QTarefa.create(
                                        qTarefa.id,
                                        qTarefa.nome
                                )
                        ),
                        QProcesso.create( // Long id, String numero, String processoUnico, String pasta, EnumTipoProcesso tipoProcesso, Pessoa parteContraria, Carteira carteira
                                qProcesso.id,
                                qProcesso.numero,
                                qProcesso.processoUnico,
                                qProcesso.pasta,
                                qProcesso.tipoProcesso,
                                QPessoa.create(
                                        parteContraria.id,
                                        parteContraria.nomeRazaoSocial
                                ),
                                qProcesso.carteira
                        ),
                        qDadosBasicosTarefa.dataInicio,
                        qDadosBasicosTarefa.dataAgendamento,
                        qDadosBasicosTarefa.dataPrazoFatal,
                        QPessoa.create(
                                qResponsavel.id,
                                qResponsavel.nomeRazaoSocial,
                                qResponsavel.apelidoFantasia,
                                qResponsavel.rgInscricaoEstadual,
                                qResponsavel.cpfCnpj
                        ),
                        QPessoa.create(
                                qRealizadoPor.id,
                                qRealizadoPor.nomeRazaoSocial,
                                qRealizadoPor.apelidoFantasia,
                                qRealizadoPor.rgInscricaoEstadual,
                                qRealizadoPor.cpfCnpj
                        ),
                        qDadosBasicosTarefa.dataConclusao,
                        qDadosBasicosTarefa.rotulo,
                        qDadosBasicosTarefa.memo,
                        qDadosBasicosTarefa.dadosBasicosTarefaPai,
                        qDadosBasicosTarefa.processoDespesas,
                        qDadosBasicosTarefa.status,
                        qDadosBasicosTarefa.local
                        )
                )
                .from(qDadosBasicosTarefa)
                .leftJoin(qDadosBasicosTarefa.responsavel, qResponsavel)
                .leftJoin(qDadosBasicosTarefa.realizadoPor, qRealizadoPor)
                .leftJoin(qDadosBasicosTarefa.dadosBasicosTarefaPai, qDadosBasicosTarefaPai)
                .leftJoin(qDadosBasicosTarefa.processoDespesas, qProcessoDespesas)
                .join(qDadosBasicosTarefa.processo, qProcesso)
                .leftJoin(qProcesso.parteContraria, parteContraria)
                .join(qDadosBasicosTarefa.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefa)
                .join(qFluxoTrabalhoTarefa.tarefa, qTarefa)
                .where(qDadosBasicosTarefa.id.eq(id))
                .fetchOne());
    }

    @Override
    public Long countTarefasPendentesPorFilaDevolucao(Fila fila, boolean isFiltrarTempoVisao) {
        final QDadosBasicosTarefa qDadosBasicosTarefa = QDadosBasicosTarefa.dadosBasicosTarefa;
        final QFila qFila = QFila.fila;
        final QProcesso qProcesso = QProcesso.processo1;
        final QFilaTarefa qFilaTarefa = QFilaTarefa.filaTarefa;
        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;
        final QFilaEspera qFilaEspera = QFilaEspera.filaEspera;

        JPAQuery<Long> query = new JPAQueryFactory(entityManager)
                .select(qDadosBasicosTarefa.count())
                .from(qDadosBasicosTarefa)
                .join(qDadosBasicosTarefa.processo, qProcesso)
                .join(qDadosBasicosTarefa.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefa)
                .join(qFluxoTrabalhoTarefa.filas, qFilaTarefa)
                .join(qFilaTarefa.fila, qFila).on(qFilaTarefa.carteira.eq(qProcesso.carteira))
                .where(qFila.esteira.id.eq(fila.getEsteira().getId()))
                .where(qDadosBasicosTarefa.dataConclusao.isNull())
                .where(qDadosBasicosTarefa.responsavel.isNull())
                .where(qFila.ativo.eq(Boolean.TRUE).and(JPAExpressions
                        .select(qFilaEspera)
                        .from(qFilaEspera)
                        .where(qFilaEspera.filaAtivaEspera.eq(qFila))
                        .notExists()))
                .where(qDadosBasicosTarefa.status.eq(EnumStatusTarefa.DEVOLVIDA)
                        .or(qDadosBasicosTarefa.status.eq(EnumStatusTarefa.HIBERNADA)));


        Optional<Pessoa> operacional = Optional.ofNullable(fila.getOperacional());
        if (operacional.isPresent() && Objects.nonNull(operacional.get().getId())) {
            query.where(qFila.operacional.id.in(
                    JPAExpressions
                            .select(qProcesso.operacional.id)
                            .from(qProcesso)
                            .where(qProcesso.eq(qDadosBasicosTarefa.processo))
            ));
        }

        if (fila.isProcessosSemTag()) {
            final QProcessoTag qProcessoTag = QProcessoTag.processoTag;
            query.where(qProcesso.id.notIn(JPAExpressions
                    .select(qProcessoTag.processo.id)
                    .from(qProcessoTag).fetchAll()));
        }

        if (isFiltrarTempoVisao) {
            whereDueDate(query, fila);
        }

        whereTag(query, fila, null);

        Long count = query.fetchOne();

        return (Objects.isNull(count)) ? 0L : count;
    }

    @Override
    public Long countTarefasPendentesPorFilaAtendimento(Fila fila, boolean isFiltrarTempoVisao, boolean isCountTarefasDevolvidas) {
        final QDadosBasicosTarefa qDadosBasicosTarefa = QDadosBasicosTarefa.dadosBasicosTarefa;
        final QFila qFila = QFila.fila;
        final QProcesso qProcesso = QProcesso.processo1;
        final QFilaTarefa qFilaTarefa = QFilaTarefa.filaTarefa;
        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;

        JPAQuery<Long> query = new JPAQueryFactory(entityManager)
                .select(qDadosBasicosTarefa.count())
                .from(qDadosBasicosTarefa)
                .join(qDadosBasicosTarefa.processo, qProcesso)
                .join(qDadosBasicosTarefa.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefa)
                .join(qFluxoTrabalhoTarefa.filas, qFilaTarefa)
                .join(qFilaTarefa.fila, qFila)
                .where(qFila.id.in(fila.getId()))
                .where(qDadosBasicosTarefa.dataConclusao.isNull())
                .where(qDadosBasicosTarefa.responsavel.isNull());

        if (isCountTarefasDevolvidas) {
            query.where(qDadosBasicosTarefa.status.eq(EnumStatusTarefa.PENDENTE).or(qDadosBasicosTarefa.status.eq(EnumStatusTarefa.DEVOLVIDA)));
        } else {
            query.where(qDadosBasicosTarefa.status.eq(EnumStatusTarefa.PENDENTE));
        }

        Optional<Pessoa> operacional = Optional.ofNullable(fila.getOperacional());
        if (operacional.isPresent() && Objects.nonNull(operacional.get().getId())) {
            query.where(qFila.operacional.id.in(
                    JPAExpressions
                            .select(qProcesso.operacional.id)
                            .from(qProcesso)
                            .where(qProcesso.eq(qDadosBasicosTarefa.processo))
            ));
        }

        if (fila.isProcessosSemTag()) {
            final QProcessoTag qProcessoTag = QProcessoTag.processoTag;
            query.where(qProcesso.id.notIn(JPAExpressions
                    .select(qProcessoTag.processo.id)
                    .from(qProcessoTag).fetchAll()));
        }

        if (isFiltrarTempoVisao) {
            whereDueDate(query, fila);
        }

        whereTag(query, fila, null);

        Long count = query.fetchCount();

        return (Objects.isNull(count)) ? 0L : count;
    }


    @Override
    public List<DadosBasicosTarefa> findTarefasBy(Query<DadosBasicosTarefa> query) {
        return createQueryTarefasBy(query).fetch();
    }

    @Override
    public long countTarefasBy(Query<DadosBasicosTarefa> query) {
        return createQueryTarefasBy(query).fetchCount();
    }

    @Override
    public long countTarefasConcluidas(Query<DadosBasicosTarefa> query) {
        final QDadosBasicosTarefa qDadosBasicosTarefa = QDadosBasicosTarefa.dadosBasicosTarefa;
        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;
        final QTarefa qTarefa = QTarefa.tarefa;
        final QProcesso qProcesso = QProcesso.processo1;
        final QFilaTarefa qFilaTarefa = QFilaTarefa.filaTarefa;
        final QFila qFila = QFila.fila;

        JPAQuery<DadosBasicosTarefa> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qDadosBasicosTarefa)
                .from(qDadosBasicosTarefa)
                .join(qDadosBasicosTarefa.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefa)
                .join(qFluxoTrabalhoTarefa.tarefa, qTarefa)
                .join(qDadosBasicosTarefa.processo, qProcesso)
                .join(qFluxoTrabalhoTarefa.filas, qFilaTarefa)
                .join(qFilaTarefa.fila, qFila);

        if (query.getFilter() != null && query.getFilter() instanceof DadosBasicosTarefaFilter) {
            final DadosBasicosTarefaFilter filter = (DadosBasicosTarefaFilter) query.getFilter();

            if (filter.getFila() != null) {
                jpaQuery.where(QFila.fila.eq(filter.getFila()));
            }

            if (filter.getDataConclusaoInicial() != null && filter.getDataConclusaoFinal() != null) {
                jpaQuery.where(QDadosBasicosTarefa.dadosBasicosTarefa.dataConclusao.goe(filter.getDataConclusaoInicial()));
                jpaQuery.where(QDadosBasicosTarefa.dadosBasicosTarefa.dataConclusao.loe(filter.getDataConclusaoFinal()));
            }
        }

        return jpaQuery.fetchCount();
    }

    @Override
    public List<DadosBasicosTarefa> findByPendentesByFluxoTrabalhoTarefa(FluxoTrabalhoTarefa ftt) {
        final QDadosBasicosTarefa qDadosBasicosTarefa = QDadosBasicosTarefa.dadosBasicosTarefa;

        return new JPAQueryFactory(entityManager)
                .select(qDadosBasicosTarefa)
                .from(qDadosBasicosTarefa)
                .where(qDadosBasicosTarefa.fluxoTrabalhoTarefa.eq(ftt))
                .where(qDadosBasicosTarefa.status.eq(EnumStatusTarefa.PENDENTE))
                .where(qDadosBasicosTarefa.dataConclusao.isNull())
                .fetch();
    }

    private JPAQuery<DadosBasicosTarefa> createQueryTarefasBy(Query<DadosBasicosTarefa> query) {
        final QDadosBasicosTarefa qDadosBasicosTarefa = QDadosBasicosTarefa.dadosBasicosTarefa;
        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;
        final QTarefa qTarefa = QTarefa.tarefa;
        final QProcesso qProcesso = QProcesso.processo1;
        final QFilaTarefa qFilaTarefa = QFilaTarefa.filaTarefa;
        final QFila qFila = QFila.fila;

        JPAQuery<DadosBasicosTarefa> jpaQuery = new JPAQueryFactory(entityManager)
                .selectDistinct(qDadosBasicosTarefa)
                .from(qDadosBasicosTarefa)
                .join(qDadosBasicosTarefa.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefa)
                .join(qFluxoTrabalhoTarefa.tarefa, qTarefa)
                .join(qDadosBasicosTarefa.processo, qProcesso)
                .join(qFluxoTrabalhoTarefa.filas, qFilaTarefa)
                .join(qFilaTarefa.fila, qFila);

        if (query.getFilter() != null && query.getFilter() instanceof DadosBasicosTarefaFilter) {
            final DadosBasicosTarefaFilter filter = (DadosBasicosTarefaFilter) query.getFilter();
            where(jpaQuery, filter);
        }

        return jpaQuery;
    }


    private void where(JPAQuery<DadosBasicosTarefa> jpaQuery, DadosBasicosTarefaFilter filter) {

        //TODO - ACERTAR ESTA CLASSE

        /*if (filter.getFila() != null) {
            if (filter.getFila().isFilaDevolucao()) {
                jpaQuery.where(QFila.fila.esteira.eq(filter.getFila().getEsteira()));
            } else {
                jpaQuery.where(QFila.fila.eq(filter.getFila()));
            }

            jpaQuery.where(QProcesso.processo1.carteira.eq(QFilaTarefa.filaTarefa.carteira));

            if (!filter.getConsiderarFilasInativas()) {
                jpaQuery.where(QFila.fila.ativo.eq(true));
            }

            if (!filter.getRetornarTarefasEmAtendimento()) {
                jpaQuery.where(QDadosBasicosTarefa.dadosBasicosTarefa.responsavel.isNull());
            }

            if (Objects.nonNull(filter.getFila().getExpressao())) {
                jpaQuery.where(QDadosBasicosTarefa.dadosBasicosTarefa.memo.contains(filter.getFila().getExpressao()));
            }

            if (Objects.nonNull(filter.getFila().getDataRecebimentoInicial())) {
                jpaQuery.where(QProcesso.processo1.dataRecebimento.goe(filter.getFila().getDataRecebimentoInicial()));
            }

            if (Objects.nonNull(filter.getFila().getDataRecebimentoFinal())) {
                jpaQuery.where(QProcesso.processo1.dataRecebimento.loe(filter.getFila().getDataRecebimentoFinal()));
            }

            whereTag(jpaQuery, filter.getFila(), filter.getTags());
        }

        if (Objects.nonNull(filter.getListFluxoTrabalhoTarefa())) {
            jpaQuery.where(QDadosBasicosTarefa.dadosBasicosTarefa.fluxoTrabalhoTarefa.in(filter.getListFluxoTrabalhoTarefa()));
        }
        if (filter.getDataAgendamentoInicial() != null) {
            jpaQuery.where(QDadosBasicosTarefa.dadosBasicosTarefa.dataPrazoFatal
                    .coalesce(QDadosBasicosTarefa.dadosBasicosTarefa.dataInicio
                            .coalesce(QDadosBasicosTarefa.dadosBasicosTarefa.dataAgendamento))
                    .asDate().goe(DateUtils.setInicioDeDia(filter.getDataAgendamentoInicial())));
        }

        if (filter.getDataAgendamentoFinal() != null) {
            jpaQuery.where(QDadosBasicosTarefa.dadosBasicosTarefa.dataPrazoFatal
                    .coalesce(QDadosBasicosTarefa.dadosBasicosTarefa.dataInicio
                            .coalesce(QDadosBasicosTarefa.dadosBasicosTarefa.dataAgendamento))
                    .asDate().loe(DateUtils.setFimDeDia(filter.getDataAgendamentoFinal())));
        }

        if (filter.getDataConclusaoInicial() != null) {
            jpaQuery.where(QDadosBasicosTarefa.dadosBasicosTarefa.dataPrazoFatal
                    .coalesce(QDadosBasicosTarefa.dadosBasicosTarefa.dataInicio
                            .coalesce(QDadosBasicosTarefa.dadosBasicosTarefa.dataAgendamento))
                    .asDate().goe(filter.getDataConclusaoInicial()));
        }

        if (filter.getDataConclusaoFinal() != null) {
            jpaQuery.where(QDadosBasicosTarefa.dadosBasicosTarefa.dataPrazoFatal
                    .coalesce(QDadosBasicosTarefa.dadosBasicosTarefa.dataInicio
                            .coalesce(QDadosBasicosTarefa.dadosBasicosTarefa.dataAgendamento))
                    .asDate().loe(filter.getDataConclusaoFinal()));
        }

        if (filter.getResponsavel() != null) {
            jpaQuery.where(QDadosBasicosTarefa.dadosBasicosTarefa.responsavel.eq(filter.getResponsavel()));
        }

        if (filter.getAgendadoPara() != null) {
            jpaQuery.where(QDadosBasicosTarefa.dadosBasicosTarefa.agendadoPara.eq(filter.getAgendadoPara()));
        }

        if (filter.getRealizadoPor() != null) {
            jpaQuery.where(QDadosBasicosTarefa.dadosBasicosTarefa.realizadoPor.eq(filter.getRealizadoPor()));
        }

        if (filter.getStatusTarefa() != null) {

            jpaQuery.where(filter.getStatusTarefa()
                    .stream()
                    .map(QDadosBasicosTarefa.dadosBasicosTarefa.status::eq)
                    .reduce(BooleanExpression::or)
                    .get());

            if (filter.getStatusTarefa().contains(EnumStatusTarefa.CUMPRIDO) || filter.getStatusTarefa().contains(EnumStatusTarefa.NAO_CUMPRIDO)) {
                jpaQuery.where(QDadosBasicosTarefa.dadosBasicosTarefa.dataConclusao.isNotNull());
            } else {
                jpaQuery.where(QDadosBasicosTarefa.dadosBasicosTarefa.dataConclusao.isNull());
            }
        }

        //Se não foram passadas as datas como parâmetro, valida configuração da fila por tempo de visão
        if ((filter.getDesconsiderarConfigDatasFila() == null || !filter.getDesconsiderarConfigDatasFila())
                && filter.getDataAgendamentoInicial() == null && filter.getDataAgendamentoFinal() == null) {
            whereDueDate(jpaQuery, filter.getFila());
        }

        //Filtra as tarefas dos processos de acordo com o operacional parametrizado na fila
        if (!Objects.isNull(filter.getFila().getOperacional()) && !Objects.isNull(filter.getFila().getOperacional().getId())) {
            jpaQuery.where(QFila.fila.operacional.id.in(
                    JPAExpressions
                            .select(QProcesso.processo1.operacional.id)
                            .from(QProcesso.processo1)
                            .where(QProcesso.processo1.eq(QDadosBasicosTarefa.dadosBasicosTarefa.processo))
            ));
        }*/
    }


    private void whereTag(JPAQuery query, Fila fila, List<String> tags) {
        final QProcesso qProcesso = QProcesso.processo1;
        final QProcessoTag qProcessoTag = QProcessoTag.processoTag;
        final QTag qTag = QTag.tag;
        List<Tag> tagList = null; //tagService.findByFila(fila);

        if (fila != null && fila.isProcessosSemTag()) {
            query.where(qProcesso.id.notIn(JPAExpressions
                    .select(qProcessoTag.processo.id)
                    .from(qProcessoTag).fetchAll()));
        } else {
            if (tags != null && !tags.isEmpty()) {
                query.join(qProcesso.tags, qTag);
                query.where(qTag.nome.in(tags));
            } else {
                if (Objects.nonNull(tagList)) {
                    if (tagList.size() > 0) {
                        query.join(qProcesso.tags, qTag);
                        query.where(qTag.in(tagList));
                    }
                }
            }
        }
    }


    @Override
    public Long countTarefasPendentesPorUsuario(Long idUsuario) {
        final QDadosBasicosTarefa qDadosBasicosTarefa = QDadosBasicosTarefa.dadosBasicosTarefa;
        final QPessoa qPessoa = QPessoa.pessoa;

        return new JPAQueryFactory(entityManager)
                .select(qDadosBasicosTarefa.id)
                .from(qDadosBasicosTarefa)
                .join(qDadosBasicosTarefa.responsavel, qPessoa)
                .where(qPessoa.id.eq(idUsuario))
                .where(qDadosBasicosTarefa.dataConclusao.isNull())
                .fetchCount();
    }


    @Override
    public Map<Calendar, Long> mapDatacountTarefasConcluidasDataPessoa(Long idPessoa, Calendar dataInicio, Calendar dataFim) {
        final QDadosBasicosTarefa qDadosBasicosTarefa = QDadosBasicosTarefa.dadosBasicosTarefa;
        final QPessoa qPessoa = QPessoa.pessoa;

        List<Tuple> tuples = new JPAQueryFactory(entityManager)
                .select(qDadosBasicosTarefa.dataConclusao,
                        qDadosBasicosTarefa.count())
                .from(qDadosBasicosTarefa)
                .join(qDadosBasicosTarefa.realizadoPor, qPessoa)
                .where(qDadosBasicosTarefa.dataConclusao.between(dataInicio, dataFim))
                .where(qPessoa.id.eq(idPessoa))
                .groupBy(qDadosBasicosTarefa.dataConclusao)
                .fetch();

        final Function<Tuple, Calendar> tarefasPorDataSemTime =
                (tuple) -> DateUtils.setFimDeDia(tuple.get(0, Calendar.class));

        final Collector<Tuple, ?, Map<Calendar, List<Tuple>>> tarefasPorDataSemTimeAgrupador =
                Collectors.groupingBy(tarefasPorDataSemTime);

        return tuples
                .stream()
                .collect(tarefasPorDataSemTimeAgrupador)
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        t -> t.getKey(),
                        t -> t.getValue().stream().mapToLong(tt -> (Long) tt.get(1, Long.class)).sum()
                ));
    }


    @Override
    public List<Object[]> countTopProdutividadePorPessoa(List<Long> carteiras, Long top, Calendar dataInicio, Calendar dataFim, List<Long> idEscritorio) {
        final QDadosBasicosTarefa qDadosBasicosTarefa = QDadosBasicosTarefa.dadosBasicosTarefa;
        final QProcesso qProcesso = QProcesso.processo1;
        final QEscritorio qEscritorio = QEscritorio.escritorio;
        final QCarteira qCarteira = QCarteira.carteira;

        List<Tuple> tuples = new JPAQueryFactory(entityManager)
                .select(qDadosBasicosTarefa.count(), qDadosBasicosTarefa.realizadoPor.id)
                .from(qDadosBasicosTarefa)
                .join(qDadosBasicosTarefa.processo, qProcesso)
                .join(qProcesso.escritorio, qEscritorio)
                .join(qProcesso.carteira, qCarteira)
                .where(qDadosBasicosTarefa.dataInicio.between(dataInicio, dataFim))
                .where(qDadosBasicosTarefa.dataConclusao.between(dataInicio, dataFim))
                .where(qEscritorio.id.in(idEscritorio))
                .where(qDadosBasicosTarefa.realizadoPor.isNotNull())
                .where(qProcesso.processo.isNull())
                .where(qCarteira.id.in(carteiras))
                .groupBy(qDadosBasicosTarefa.realizadoPor.id)
                .orderBy(qDadosBasicosTarefa.count().desc())
                .limit(top)
                .fetch();

        List<Object[]> result = new ArrayList<>();
        for (Tuple tuple : tuples) {
            result.add(tuple.toArray());
        }
        return result;
    }


    @Override
    public Long countTarefasConcluidasPorCarteiras(Set<Carteira> carteiras) {
        final QDadosBasicosTarefa qDadosBasicosTarefa = QDadosBasicosTarefa.dadosBasicosTarefa;
        final QProcesso qProcesso = QProcesso.processo1;

        return new JPAQueryFactory(entityManager)
                .select(qDadosBasicosTarefa.id)
                .from(qDadosBasicosTarefa)
                .join(qDadosBasicosTarefa.processo, qProcesso)
                .where(qProcesso.carteira.in(carteiras))
                .where(qDadosBasicosTarefa.dataConclusao.isNotNull())
                .fetchCount();
    }


    @Override
    public Long countTarefasPendentesPorCarteiras(Set<Carteira> carteiras) {
        final QDadosBasicosTarefa qDadosBasicosTarefa = QDadosBasicosTarefa.dadosBasicosTarefa;
        final QProcesso qProcesso = QProcesso.processo1;

        return new JPAQueryFactory(entityManager)
                .select(qDadosBasicosTarefa.id)
                .from(qDadosBasicosTarefa)
                .join(qDadosBasicosTarefa.processo, qProcesso)
                .where(qProcesso.carteira.in(carteiras))
                .where(qDadosBasicosTarefa.dataConclusao.isNull())
                .fetchCount();
    }


    @Override
    public Map<Escritorio, Long> countTarefasConcluidasPorEscritorio(Set<Carteira> carteiras, Calendar dataInicial, Calendar dataFinal) {
        final QDadosBasicosTarefa qDadosBasicosTarefa = QDadosBasicosTarefa.dadosBasicosTarefa;
        final QProcesso qProcesso = QProcesso.processo1;
        final QEscritorio qEscritorio = QEscritorio.escritorio;
        final QPessoa qEscritorioPessoa = QPessoa.pessoa;

        List<Tuple> tuples = new JPAQueryFactory(entityManager)
                .select(
                        qEscritorio.id.coalesce(0L),
                        qEscritorioPessoa.nomeRazaoSocial.coalesce("A distribuir"),
                        qDadosBasicosTarefa.count())
                .from(qDadosBasicosTarefa)
                .join(qDadosBasicosTarefa.processo, qProcesso)
                .leftJoin(qProcesso.escritorio, qEscritorio)
                .leftJoin(qEscritorio.pessoa, qEscritorioPessoa)
                .where(qProcesso.carteira.in(carteiras))
                .where(qDadosBasicosTarefa.dataConclusao.between(dataInicial, dataFinal))
                .groupBy(
                        qEscritorio.id,
                        qEscritorioPessoa.nomeRazaoSocial
                )
                .orderBy(qDadosBasicosTarefa.count().desc())
                .orderBy(qEscritorioPessoa.nomeRazaoSocial.asc())
                .fetch();

        Map<Escritorio, Long> processosPorEscritorio = new LinkedHashMap<>();
        for (Tuple tuple : tuples) {
            Long id = tuple.get(0, Long.class);
            String nomeRazaoSocial = tuple.get(1, String.class);
            Long total = tuple.get(2, Long.class);

            processosPorEscritorio.put(new Escritorio(id, nomeRazaoSocial), total);
        }

        return processosPorEscritorio;
    }


    @Override
    public List<DadosBasicosTarefa> findByEscritorioAndCarteiras(Usuario usuario, List<Long> idsEscritorios) {
        final QDadosBasicosTarefa qDadosBasicosTarefa = QDadosBasicosTarefa.dadosBasicosTarefa;
        final QProcesso qProcesso = QProcesso.processo1;
        final QEscritorio qEscritorio = QEscritorio.escritorio;
        final QCarteira qCarteira = QCarteira.carteira;

        return new JPAQueryFactory(entityManager)
                .select(qDadosBasicosTarefa)
                .from(qDadosBasicosTarefa)
                .join(qDadosBasicosTarefa.processo, qProcesso)
                .join(qProcesso.escritorio, qEscritorio)
                .join(qProcesso.carteira, qCarteira)
                .where(qEscritorio.id.in(idsEscritorios))
                .where(qCarteira.in(usuario.getPessoa().getCarteiras()))
                .fetch();
    }


    @Override
    public List<?> retornaTotalSolicitacoesPorDia(Fila fila, Calendar dataInicio, Calendar dataFim) {
        final QDadosBasicosTarefa qDadosBasicosTarefa = QDadosBasicosTarefa.dadosBasicosTarefa;
        final QFila qFila = QFila.fila;
        final QTarefa qTarefa = QTarefa.tarefa;
        final QProcesso qProcesso = QProcesso.processo1;
        final QCarteira qCarteira = QCarteira.carteira;
        final QFluxoTrabalho qFluxoTrabalho = QFluxoTrabalho.fluxoTrabalho;
        final QFilaTarefa qFilaTarefa = QFilaTarefa.filaTarefa;

        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;

        List<Tuple> result = new JPAQueryFactory(entityManager)
                .select(qDadosBasicosTarefa.dataInicio,
                        qDadosBasicosTarefa.dataInicio.count())
                .from(qDadosBasicosTarefa)
                .join(qDadosBasicosTarefa.processo, qProcesso)
                .join(qProcesso.carteira, qCarteira)
                .join(qCarteira.fluxoTrabalho, qFluxoTrabalho)
                .join(qFluxoTrabalho.fluxoTrabalhoTarefas, qFluxoTrabalhoTarefa)
                .join(qFluxoTrabalhoTarefa.filas, qFilaTarefa)
                .join(qFilaTarefa.fila, qFila)
                .where(qFluxoTrabalhoTarefa.eq(qDadosBasicosTarefa.fluxoTrabalhoTarefa))
                .where(qFila.id.eq(fila.getId()))
                .where(qDadosBasicosTarefa.dataInicio.between(dataInicio, dataFim))
                .groupBy(qDadosBasicosTarefa.dataInicio)
                .where(qDadosBasicosTarefa.dataAgendamento.loe(getDataTempoVisao(Optional.ofNullable(fila.getTempoVisao()).orElse(365), true)))
                .orderBy(qDadosBasicosTarefa.dataInicio.asc())
                .fetch();

        List<Object[]> lista = new ArrayList<>();
        for (Tuple tuple : result) {
            lista.add(tuple.toArray());
        }

        return lista;
    }


    @Override
    public List<?> retornaTotalSolicitacoesConcluidasPorDia(Long idFila, Calendar dataInicio, Calendar dataFim) {
        final QFila qFila = QFila.fila;
        final QTarefa qTarefa = QTarefa.tarefa;
        final QDadosBasicosTarefa qDadosBasicosTarefa = QDadosBasicosTarefa.dadosBasicosTarefa;
        final QProcesso qProcesso = QProcesso.processo1;
        final QCarteira qCarteira = QCarteira.carteira;
        final QFluxoTrabalho qFluxoTrabalho = QFluxoTrabalho.fluxoTrabalho;
        final QFilaTarefa qFilaTarefa = QFilaTarefa.filaTarefa;
        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;

        List<Tuple> result = new JPAQueryFactory(entityManager)
                .select(qDadosBasicosTarefa.dataConclusao,
                        qDadosBasicosTarefa.dataConclusao.count())
                .from(qDadosBasicosTarefa)
                .from(qDadosBasicosTarefa)
                .join(qDadosBasicosTarefa.processo, qProcesso)
                .join(qProcesso.carteira, qCarteira)
                .join(qCarteira.fluxoTrabalho, qFluxoTrabalho)
                .join(qFluxoTrabalho.fluxoTrabalhoTarefas, qFluxoTrabalhoTarefa)
                .join(qFluxoTrabalhoTarefa.filas, qFilaTarefa)
                .join(qFilaTarefa.fila, qFila)
                .where(qFluxoTrabalhoTarefa.eq(qDadosBasicosTarefa.fluxoTrabalhoTarefa))
                .where(qFila.id.eq(idFila))
                .where(qDadosBasicosTarefa.dataConclusao.between(dataInicio, dataFim))
                .groupBy(qDadosBasicosTarefa.dataConclusao)
                .orderBy(qDadosBasicosTarefa.dataConclusao.asc())
                .fetch();

        List<Object[]> lista = new ArrayList<>();
        for (Tuple tuple : result) {
            lista.add(tuple.toArray());
        }

        return lista;
    }


    @Override
    public long count(Query<DadosBasicosTarefa> query) {
        return createQuery(query).fetchCount();
    }


    @Override
    public List<DadosBasicosTarefa> findByProcesso(Processo processo) {
        final QDadosBasicosTarefa qDadosBasicosTarefa = QDadosBasicosTarefa.dadosBasicosTarefa;
        final QTarefa qTarefa = QTarefa.tarefa;
        final QProcesso qProcesso = QProcesso.processo1;
        final QPessoa qPessoaResponsavel = new QPessoa("pessoaResponsavel");
        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;

        return new JPAQueryFactory(entityManager)
                .select(QDadosBasicosTarefa.create(
                        qDadosBasicosTarefa.id,
                        qDadosBasicosTarefa.dataInicio,
                        qDadosBasicosTarefa.dataAgendamento,
                        qDadosBasicosTarefa.dataConclusao,
                        qDadosBasicosTarefa.dataPrazoFatal,
                        qDadosBasicosTarefa.rotulo,
                        QProcesso.create(
                                qProcesso.id
                        ),
                        QFluxoTrabalhoTarefa.create(
                                qFluxoTrabalhoTarefa.id,
                                QTarefa.create(
                                        qTarefa.id,
                                        qTarefa.nome
                                )
                        ),
                        QPessoa.create(
                                qPessoaResponsavel.id,
                                qPessoaResponsavel.nomeRazaoSocial
                        ),
                        qDadosBasicosTarefa.memo

                ))
                .from(qDadosBasicosTarefa)
                .join(qDadosBasicosTarefa.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefa)
                .join(qFluxoTrabalhoTarefa.tarefa, qTarefa)
                .join(qDadosBasicosTarefa.processo, qProcesso)
                .leftJoin(qDadosBasicosTarefa.responsavel, qPessoaResponsavel)
                .where(qDadosBasicosTarefa.processo.eq(processo))
                .where(qDadosBasicosTarefa.dataConclusao.isNull())
                .fetch();
    }

    @Override
    public Long countTarefasPendentesPorProcesso(Long idProcesso) {
        final QDadosBasicosTarefa qDadosBasicosTarefa = QDadosBasicosTarefa.dadosBasicosTarefa;
        final QProcesso qProcesso = QProcesso.processo1;
        return new JPAQueryFactory(entityManager)
                .select(qDadosBasicosTarefa)
                .from(qDadosBasicosTarefa)
                .join(qDadosBasicosTarefa.processo, qProcesso)
                .where(qProcesso.id.eq(idProcesso))
                .where(qDadosBasicosTarefa.dataConclusao.isNull())
                .fetchCount();
    }

    @Override
    public List<DadosBasicosTarefa> findTarefasPendentesPorUsuarioEProcesso(Long idUsuario, Long idProcesso) {
        QDadosBasicosTarefa qDadosBasicosTarefa = QDadosBasicosTarefa.dadosBasicosTarefa;

        return new JPAQueryFactory(entityManager)
                .select(qDadosBasicosTarefa)
                .from(qDadosBasicosTarefa)
                .where(qDadosBasicosTarefa.dataConclusao.isNull())
                .where(qDadosBasicosTarefa.responsavel.id.eq(idUsuario))
                .where(qDadosBasicosTarefa.processo.id.eq(idProcesso))
                .fetch();
    }

    @Override
    public void deleteById(Long id) {
        final QDadosBasicosTarefa qDadosBasicosTarefa = QDadosBasicosTarefa.dadosBasicosTarefa;

        new JPADeleteClause(entityManager, qDadosBasicosTarefa)
                .where(qDadosBasicosTarefa.id.eq(id))
                .execute();
    }

    public List<DadosBasicosTarefa> findHibernadas() {

        final QDadosBasicosTarefa qDadosBasicosTarefa = QDadosBasicosTarefa.dadosBasicosTarefa;
        final QDadosBasicosTarefa qDadosBasicosTarefaPai = new QDadosBasicosTarefa("dadosBasicosTarefaPai");
        final QPessoa qResponsavel = new QPessoa("responsavel");
        final QPessoa qRealizadoPor = new QPessoa("realizadoPor");
        final QProcessoDespesas qProcessoDespesas = QProcessoDespesas.processoDespesas;
        final QProcesso qProcesso = QProcesso.processo1;
        final QTarefa qTarefa = QTarefa.tarefa;

        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;

        return new JPAQueryFactory(entityManager)
                .select(QDadosBasicosTarefa.create(
                        qDadosBasicosTarefa.id,
                        QFluxoTrabalhoTarefa.create(
                                qFluxoTrabalhoTarefa.id,
                                QTarefa.create(
                                        qTarefa.id,
                                        qTarefa.nome
                                )
                        ),
                        QProcesso.create(
                                qProcesso.id
                        ),
                        qDadosBasicosTarefa.dataInicio,
                        qDadosBasicosTarefa.dataAgendamento,
                        qDadosBasicosTarefa.dataPrazoFatal,
                        QPessoa.create(
                                qResponsavel.id,
                                qResponsavel.nomeRazaoSocial,
                                qResponsavel.apelidoFantasia,
                                qResponsavel.rgInscricaoEstadual,
                                qResponsavel.cpfCnpj
                        ),
                        QPessoa.create(
                                qRealizadoPor.id,
                                qRealizadoPor.nomeRazaoSocial,
                                qRealizadoPor.apelidoFantasia,
                                qRealizadoPor.rgInscricaoEstadual,
                                qRealizadoPor.cpfCnpj
                        ),
                        qDadosBasicosTarefa.dataConclusao,
                        qDadosBasicosTarefa.rotulo,
                        qDadosBasicosTarefa.memo,
                        qDadosBasicosTarefa.dadosBasicosTarefaPai,
                        qDadosBasicosTarefa.processoDespesas,
                        qDadosBasicosTarefa.status,
                        qDadosBasicosTarefa.local
                        )
                )
                .from(qDadosBasicosTarefa)
                .leftJoin(qDadosBasicosTarefa.responsavel, qResponsavel)
                .leftJoin(qDadosBasicosTarefa.realizadoPor, qRealizadoPor)
                .leftJoin(qDadosBasicosTarefa.dadosBasicosTarefaPai, qDadosBasicosTarefaPai)
                .leftJoin(qDadosBasicosTarefa.processoDespesas, qProcessoDespesas)
                .join(qDadosBasicosTarefa.processo, qProcesso)
                .join(qDadosBasicosTarefa.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefa)
                .join(qFluxoTrabalhoTarefa.tarefa, qTarefa)
                .where(qDadosBasicosTarefa.status.eq(EnumStatusTarefa.HIBERNADA))
                .where(qDadosBasicosTarefa.dataAgendamento.loe(DateUtils.setFimDeDia(Calendar.getInstance())))
                .fetch();
    }

    private void whereDueDate(JPAQuery query, Fila fila) {
        if (fila == null) {
            return;
        }

        QDadosBasicosTarefa qDadosBasicosTarefa = QDadosBasicosTarefa.dadosBasicosTarefa;

        /*if (fila.getVisualizarTarefasVencidas() == null || !fila.getVisualizarTarefasVencidas()) {
            query.where(qDadosBasicosTarefa.dataAgendamento.coalesce(qDadosBasicosTarefa.dataInicio).asDate().isNull()
                    .or(qDadosBasicosTarefa.dataAgendamento.between(Calendar.getInstance(), getDataTempoVisao(fila.getTempoVisao(), false))));
        } else {
            if (Objects.equals(fila.getTempoVisaoVencidas(), 0L) || fila.getTempoVisaoVencidas() == null) {
                query.where(qDadosBasicosTarefa.dataAgendamento.coalesce(qDadosBasicosTarefa.dataInicio).asDate().isNull()
                        .or(qDadosBasicosTarefa.dataAgendamento.coalesce(qDadosBasicosTarefa.dataInicio).asDate().loe(getDataTempoVisao(fila.getTempoVisao(), false))));
            } else {
                query.where(qDadosBasicosTarefa.dataAgendamento.coalesce(qDadosBasicosTarefa.dataInicio).asDate().isNull()
                        .or(qDadosBasicosTarefa.dataAgendamento.coalesce(qDadosBasicosTarefa.dataInicio).asDate().between(getDataTempoVisao(fila.getTempoVisaoVencidas() * -1, true),
                                getDataTempoVisao(fila.getTempoVisao(), false))));
            }
        }*/
    }

    private Calendar getDataTempoVisao(Integer tempoVisao, Boolean inicioDia) {
        if (Objects.isNull(tempoVisao)) {
            tempoVisao = 0;
        }

        DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern(Util.PATTERN_DATA_HORA).toFormatter();
        LocalDateTime now = LocalDateTime.now().plusDays(tempoVisao);
        Calendar retorno = Util.getStringToDate(now.format(formatter), Util.PATTERN_DATA_HORA);

        if (inicioDia) {
            Util.correcaoInicioDia(retorno);
        } else {
            Util.correcaoFimDia(retorno);
        }

        return retorno;
    }

    @Override
    public List<DadosBasicosTarefa> findPorContratoClausulaEProcesso(ContratoClausula clausula, Processo processo, Calendar dataInicio, Calendar dataFim) {
        QDadosBasicosTarefa qDadosBasicosTarefa = QDadosBasicosTarefa.dadosBasicosTarefa;
        QContratoClausula qContratoClausula = QContratoClausula.contratoClausula;
        QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;

        JPAQuery<DadosBasicosTarefa> query = new JPAQueryFactory(entityManager)
                .select(qDadosBasicosTarefa)
                .from(qDadosBasicosTarefa)
                .join(qDadosBasicosTarefa.processo)
                .where(qDadosBasicosTarefa.dataConclusao.between(dataInicio, dataFim))
                .where(qDadosBasicosTarefa.processo.eq(processo))
                .where(qDadosBasicosTarefa.fluxoTrabalhoTarefa.in(JPAExpressions
                        .select(qFluxoTrabalhoTarefa)
                        .from(qContratoClausula)
                        .join(qContratoClausula.fluxoTrabalhoTarefas, qFluxoTrabalhoTarefa)
                        .where(qContratoClausula.eq(clausula))));

        if (Objects.nonNull(clausula.getStatusTarefa())) {
            query.where(qDadosBasicosTarefa.status.eq(clausula.getStatusTarefa()));
        }

        return query.fetch();
    }

    @Override
    public Long countPendentesByProcessoAndFluxoTrabalhoTarefa(Processo processo, FluxoTrabalhoTarefa fluxoTrabalhoTarefa) {
        final QDadosBasicosTarefa qDadosBasicosTarefa = QDadosBasicosTarefa.dadosBasicosTarefa;
        return new JPAQueryFactory(entityManager)
                .select(qDadosBasicosTarefa)
                .from(qDadosBasicosTarefa)
                .where(qDadosBasicosTarefa.processo.id.eq(processo.getId())
                        .and(qDadosBasicosTarefa.fluxoTrabalhoTarefa.id.eq(fluxoTrabalhoTarefa.getId()))
                        .and(qDadosBasicosTarefa.status.in(EnumStatusTarefa.PENDENTE, EnumStatusTarefa.HIBERNADA, EnumStatusTarefa.DEVOLVIDA))
                        .and(qDadosBasicosTarefa.dataConclusao.isNull()))
                .fetchCount();
    }

    @Override
    public List<DadosBasicosTarefa> findPendentesByProcessoAndFluxoTrabalhoTarefa(Processo processo, FluxoTrabalhoTarefa fluxoTrabalhoTarefa) {
        final QDadosBasicosTarefa qDadosBasicosTarefa = QDadosBasicosTarefa.dadosBasicosTarefa;
        return new JPAQueryFactory(entityManager)
                .select(qDadosBasicosTarefa)
                .from(qDadosBasicosTarefa)
                .where(qDadosBasicosTarefa.processo.id.eq(processo.getId())
                        .and(qDadosBasicosTarefa.fluxoTrabalhoTarefa.id.eq(fluxoTrabalhoTarefa.getId()))
                        .and(qDadosBasicosTarefa.status.in(EnumStatusTarefa.PENDENTE, EnumStatusTarefa.HIBERNADA, EnumStatusTarefa.DEVOLVIDA))
                        .and(qDadosBasicosTarefa.dataConclusao.isNull()))
                .fetchAll()
                .fetch();
    }

    @Override
    public List<DadosBasicosTarefa> findVencendoPorNotificacaoFluxo(final Query<DadosBasicosTarefa> query) {
        return this.createQueryVencendoPorNotificacaoFluxo(query).fetch();
    }

    @Override
    public Long countVencendoPorNotificacaoFluxo(final Query<DadosBasicosTarefa> query) {
        return this.createQueryVencendoPorNotificacaoFluxo(query).fetchCount();
    }

    private JPAQuery<DadosBasicosTarefa> createQueryVencendoPorNotificacaoFluxo(final Query<DadosBasicosTarefa> query) {
        final QDadosBasicosTarefa qDadosBasicosTarefa = QDadosBasicosTarefa.dadosBasicosTarefa;
        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;
        final QProcesso qProcesso = QProcesso.processo1;
        final QTarefa qTarefa = QTarefa.tarefa;
        final QPessoa qResponsavelTarefa = QPessoa.pessoa;
        final QUsuario qUsuarioNotificacao = new QUsuario("usuarioNotificacao");
        final QCarteira qCarteira = QCarteira.carteira;

        final JPAQuery<DadosBasicosTarefa> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qDadosBasicosTarefa)
                .from(qDadosBasicosTarefa)
                .join(qDadosBasicosTarefa.processo, qProcesso).fetchJoin()
                .join(qProcesso.carteira, qCarteira).fetchJoin()
                .join(qDadosBasicosTarefa.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefa).fetchJoin()
                .join(qFluxoTrabalhoTarefa.tarefa, qTarefa).fetchJoin()
                .leftJoin(qFluxoTrabalhoTarefa.usuario, qUsuarioNotificacao).fetchJoin()
                .leftJoin(qDadosBasicosTarefa.responsavel, qResponsavelTarefa).fetchJoin();

        this.restringirVisualizacaoUsuarioLogado(jpaQuery);

       /* jpaQuery.where(Expressions.dateTimeOperation(Date.class,
                        Ops.DateTimeOps.ADD_DAYS,
                        qDadosBasicosTarefa.dataAgendamento,
                        Expressions.asNumber(
                                qFluxoTrabalhoTarefa.notificacaoIntervalo.coalesce(0).asNumber().multiply(-1)
                        )).lt(Date.valueOf(LocalDate.now().plusDays(1)))
                        .and(qFluxoTrabalhoTarefa.notificacaoVencimento.eq(true)))
                .where(qDadosBasicosTarefa.dataConclusao.isNull());*/

        if (Objects.nonNull(query.getFilter())) {
            final DadosBasicosTarefaFilter filter = (DadosBasicosTarefaFilter) query.getFilter();

            if (StringUtils.isNotEmpty(filter.getProcessoUnico())) {
                jpaQuery.where(qProcesso.processoUnico.eq(filter.getProcessoUnico()));
            }

            if (!CollectionUtils.isEmpty(filter.getNome())) {
                jpaQuery.where(filter.getNome()
                        .stream()
                        .map(nome -> "%" + nome + "%")
                        .map(qTarefa.nome::likeIgnoreCase)
                        .reduce(BooleanExpression::or)
                        .get());
            }

            if (Objects.nonNull(filter.getIdCarteira())) {
                jpaQuery.where(qCarteira.id.eq(filter.getIdCarteira()));
            }

            aplicarFiltroDataAgendamentoEStatus(jpaQuery, filter);
        }

        applyPagination(jpaQuery, query.getPage(), query.getLimit());
        applySorter(jpaQuery, query.getSorter());

        return jpaQuery;
    }

    private void restringirVisualizacaoUsuarioLogado(final JPAQuery<DadosBasicosTarefa> jpaQuery) {
        final Pessoa usuarioLogado = null; //Util.getUsuarioLogado();

        if (Objects.isNull(usuarioLogado) ||
                usuarioLogado.getUsuarioSistema().hasPermissao(Permissao.PROCESSOS_PESQUISA_TODOS)) {
            return;
        }

        final QProcesso qProcesso = QProcesso.processo1;
        final QUsuarioEscritorio qUsuarioEscritorio = QUsuarioEscritorio.usuarioEscritorio;
        final QEscritorio qEscritorio = QEscritorio.escritorio;

        jpaQuery.join(qProcesso.escritorio, qEscritorio)
                .where(qProcesso.carteira.in(usuarioLogado.getCarteiras()));

        if (usuarioLogado.getUsuarioSistema().hasFuncao(EnumFuncao.COORDENADOR_OPERACIONAL).equals(Boolean.TRUE)) {
            jpaQuery.where(JPAExpressions
                    .select(qUsuarioEscritorio)
                    .from(qUsuarioEscritorio)
                    .where(qUsuarioEscritorio.escritorio.eq(qEscritorio))
                    .where(qUsuarioEscritorio.usuario.id.eq(usuarioLogado.getId()))
                    .exists());
        } else if (usuarioLogado.getUsuarioSistema().hasFuncao(EnumFuncao.OPERACIONAL).equals(Boolean.TRUE)) {
            jpaQuery.where(QProcesso.processo1.operacional.id.eq(usuarioLogado.getId()));
        }
    }

    private void aplicarFiltroDataAgendamentoEStatus(final JPAQuery<DadosBasicosTarefa> jpaQuery, final DadosBasicosTarefaFilter filter) {
        final QDadosBasicosTarefa qDadosBasicosTarefa = QDadosBasicosTarefa.dadosBasicosTarefa;

        if (Objects.nonNull(filter.getStatus()) && !filter.getStatus().isEmpty()) {

            List<BooleanExpression> predicates = new ArrayList<>();

            BooleanExpression expressionDataAgendamento = null;
            BooleanExpression whereDataAgendamentoInicial = null;
            BooleanExpression whereDataAgendamentoFinal = null;

            for (EnumTarefaStatus status : filter.getStatus()) {

                switch (status) {

                    case ATRASO:

                        expressionDataAgendamento = qDadosBasicosTarefa.dataAgendamento.lt(Calendar.getInstance());

                        if (filter.getDataAgendamentoInicial() != null && filter.getDataAgendamentoInicial().before(Calendar.getInstance())) {
                            whereDataAgendamentoInicial = qDadosBasicosTarefa.dataAgendamento.goe(DateUtils.setInicioDeDia(filter.getDataAgendamentoInicial()));
                            expressionDataAgendamento = expressionDataAgendamento.and(whereDataAgendamentoInicial);
                        }

                        if (filter.getDataAgendamentoFinal() != null && filter.getDataAgendamentoFinal().before(Calendar.getInstance())) {
                            whereDataAgendamentoFinal = qDadosBasicosTarefa.dataAgendamento.loe(DateUtils.setFimDeDia(filter.getDataAgendamentoFinal()));
                            expressionDataAgendamento = expressionDataAgendamento.and(whereDataAgendamentoFinal);
                        }

                        predicates.add(expressionDataAgendamento.and(qDadosBasicosTarefa.dataConclusao.isNull()));

                        break;

                    case VENCENDO_NO_DIA:

                        final Calendar dataFim = DateUtils.setFimDeDia(Calendar.getInstance());

                        predicates
                                .add(qDadosBasicosTarefa.dataAgendamento.goe(Calendar.getInstance())
                                        .and(qDadosBasicosTarefa.dataAgendamento.loe(dataFim))
                                        .and(qDadosBasicosTarefa.dataConclusao.isNull())
                                );

                        break;

                    case EM_ANDAMENTO:

                        expressionDataAgendamento = qDadosBasicosTarefa.dataAgendamento.gt(DateUtils.setFimDeDia(Calendar.getInstance()));

                        if (filter.getDataAgendamentoInicial() != null && filter.getDataAgendamentoInicial().after(Calendar.getInstance())) {
                            whereDataAgendamentoInicial = qDadosBasicosTarefa.dataAgendamento.goe(DateUtils.setInicioDeDia(filter.getDataAgendamentoInicial()));
                            expressionDataAgendamento = expressionDataAgendamento.and(whereDataAgendamentoInicial);
                        }

                        if (filter.getDataAgendamentoFinal() != null && filter.getDataAgendamentoFinal().after(Calendar.getInstance())) {
                            whereDataAgendamentoFinal = qDadosBasicosTarefa.dataAgendamento.loe(DateUtils.setFimDeDia(filter.getDataAgendamentoFinal()));
                            expressionDataAgendamento = expressionDataAgendamento.and(whereDataAgendamentoFinal);
                        }

                        predicates.add(expressionDataAgendamento.and(qDadosBasicosTarefa.dataConclusao.isNull()));

                        break;

                    case CONCLUIDO:

                        expressionDataAgendamento = qDadosBasicosTarefa.dataConclusao.isNotNull();

                        if (filter.getDataAgendamentoInicial() != null) {
                            whereDataAgendamentoInicial = qDadosBasicosTarefa.dataAgendamento.goe(DateUtils.setInicioDeDia(filter.getDataAgendamentoInicial()));
                            expressionDataAgendamento = expressionDataAgendamento.and(whereDataAgendamentoInicial);
                        }

                        if (filter.getDataAgendamentoFinal() != null) {
                            whereDataAgendamentoFinal = qDadosBasicosTarefa.dataAgendamento.loe(DateUtils.setFimDeDia(filter.getDataAgendamentoFinal()));
                            expressionDataAgendamento = expressionDataAgendamento.and(whereDataAgendamentoFinal);
                        }

                        predicates.add(expressionDataAgendamento);

                        break;

                }

            }

            jpaQuery.where(predicates.stream().reduce(BooleanExpression::or).get());

        } else {

            if (filter.getDataAgendamentoInicial() != null) {
                jpaQuery.where(qDadosBasicosTarefa.dataAgendamento.goe(DateUtils.setInicioDeDia(filter.getDataAgendamentoInicial())));
            }

            if (filter.getDataAgendamentoFinal() != null) {
                jpaQuery.where(qDadosBasicosTarefa.dataAgendamento.loe(DateUtils.setFimDeDia(filter.getDataAgendamentoFinal())));
            }
        }
    }
}
