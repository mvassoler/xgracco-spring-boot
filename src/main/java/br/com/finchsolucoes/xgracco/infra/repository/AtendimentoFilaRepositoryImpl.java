//package br.com.finchsolucoes.xgracco.infra.repository;
//
//import br.com.finchsolucoes.xgracco.domain.entity.*;
//import br.com.finchsolucoes.xgracco.domain.enums.EnumMotivoDevolucaoTarefa;
//import br.com.finchsolucoes.xgracco.domain.enums.EnumStatusTarefa;
//import br.com.finchsolucoes.xgracco.domain.enums.EnumTarefaStatus;
//import br.com.finchsolucoes.xgracco.domain.query.Query;
//import br.com.finchsolucoes.xgracco.domain.query.impl.AtendimentoFilaFilter;
//import br.com.finchsolucoes.xgracco.domain.repository.AtendimentoFilaJpaRepository;
//import br.com.finchsolucoes.xgracco.domain.utils.DateUtils;
//import br.com.finchsolucoes.xgracco.legacy.bussines.util.Util;
//import com.querydsl.core.types.Predicate;
//import com.querydsl.core.types.dsl.PathBuilder;
//import com.querydsl.jpa.JPAExpressions;
//import com.querydsl.jpa.impl.JPAQuery;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.time.format.DateTimeFormatterBuilder;
//import java.util.Calendar;
//import java.util.List;
//import java.util.Objects;
//import java.util.Optional;
//
///**
// * Implementação JPA do repositório da projection AtendimentoFila
// *
// * @author thiago.fogar
// */
//@Repository
//public class AtendimentoFilaRepositoryImpl implements AtendimentoFilaJpaRepository {
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    @Autowired
//    private DadosBasicosTarefaRepositoryImpl dadosBasicosTarefaJpaRepository;
//
//    @Override
//    public List<AtendimentoFila> find(final Query<AtendimentoFila> query, List<EnumStatusTarefa> enumStatusTarefaList, EnumTarefaStatus enumTarefaStatus) {
//
//        final JPAQuery jpaQuery = createQuery(query, enumStatusTarefaList, enumTarefaStatus);
//
//        // page
//        if (Optional.ofNullable(query.getPage()).orElse(0L) > 0L) {
//            jpaQuery.offset(((query.getPage() - 1L) * query.getLimit()));
//        }
//
//        // limit
//        if (query.getLimit() != Query.NO_LIMIT) {
//            jpaQuery.limit(query.getLimit());
//        }
//
//        return jpaQuery.fetch();
//    }
//
//
//    @Override
//    public Optional<AtendimentoFila> findProximaTarefaDisponivel(final Query<AtendimentoFila> query) {
//        return Optional.ofNullable(
//                createQuery(query, null, null).fetchFirst()
//        );
//    }
//
//    @Override
//    public Long count(Query<AtendimentoFila> query, List<EnumStatusTarefa> enumStatusTarefaList, EnumTarefaStatus enumTarefaStatus) {
//        return createQuery(query, enumStatusTarefaList, enumTarefaStatus).fetchCount();
//    }
//
//    @Override
//    public Optional<AtendimentoFila> findDevolucaoByDadosBasicosTarefa(DadosBasicosTarefa dadosBasicosTarefa) {
//
//        final QDadosBasicosTarefa qDadosBasicosTarefa = QDadosBasicosTarefa.dadosBasicosTarefa;
//        final QProcesso qProcesso = QProcesso.processo1;
//        final QTarefa qTarefa = QTarefa.tarefa;
//        final QFilaTarefa qFilaTarefa = QFilaTarefa.filaTarefa;
//        final QFila qFila = QFila.fila;
//        final QPessoa qPessoa = QPessoa.pessoa;
//        final QCarteira qCarteira = QCarteira.carteira;
//        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;
//
//        return Optional.ofNullable(new JPAQueryFactory(entityManager)
//                .selectDistinct(new QAtendimentoFila(
//                        qDadosBasicosTarefa.id,
//                        QDadosBasicosTarefa.create(
//                                qDadosBasicosTarefa.id,
//                                qDadosBasicosTarefa.dataInicio,
//                                qDadosBasicosTarefa.dataAgendamento,
//                                qDadosBasicosTarefa.dataPrazoFatal,
//                                qDadosBasicosTarefa.dataConclusao,
//                                QPessoa.create(
//                                        qPessoa.id,
//                                        qPessoa.nomeRazaoSocial
//                                ),
//                                qDadosBasicosTarefa.rotulo
//                        ),
//                        QProcesso.create(
//                                qProcesso.id,
//                                qProcesso.processoUnico
//                        ),
//                        QFluxoTrabalhoTarefa.create(
//                                qFluxoTrabalhoTarefa.id,
//                                QTarefa.create(
//                                        qTarefa.id,
//                                        qTarefa.nome
//                                )
//                        )
//                ))
//                .from(qDadosBasicosTarefa)
//                .join(qDadosBasicosTarefa.processo, qProcesso)
//                .join(qProcesso.carteira, qCarteira)
//                .join(qDadosBasicosTarefa.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefa)
//                .join(qFluxoTrabalhoTarefa.filas, qFilaTarefa)
//                .join(qFluxoTrabalhoTarefa.tarefa, qTarefa)
//                .join(qFilaTarefa.fila, qFila)
//                .leftJoin(qDadosBasicosTarefa.responsavel, qPessoa)
//                .where(qDadosBasicosTarefa.dataConclusao.isNull())
//                .where(qDadosBasicosTarefa.eq(dadosBasicosTarefa))
//                .where(qDadosBasicosTarefa.status.eq(EnumStatusTarefa.DEVOLVIDA))
//                .fetchOne());
//    }
//
//
//    @Override
//    public Optional<AtendimentoFila> findByDadosBasicosTarefa(DadosBasicosTarefa dadosBasicosTarefa) {
//        final QDadosBasicosTarefa qDadosBasicosTarefa = QDadosBasicosTarefa.dadosBasicosTarefa;
//        final QProcesso qProcesso = QProcesso.processo1;
//        final QTarefa qTarefa = QTarefa.tarefa;
//        final QFilaTarefa qFilaTarefa = QFilaTarefa.filaTarefa;
//        final QFila qFila = QFila.fila;
//        final QPessoa qPessoa = QPessoa.pessoa;
//        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;
//
//
//        return Optional.ofNullable(new JPAQueryFactory(entityManager)
//                .selectDistinct(new QAtendimentoFila(
//                        qDadosBasicosTarefa.id,
//                        QDadosBasicosTarefa.create(
//                                qDadosBasicosTarefa.id,
//                                qDadosBasicosTarefa.dataInicio,
//                                qDadosBasicosTarefa.dataAgendamento,
//                                qDadosBasicosTarefa.dataPrazoFatal,
//                                qDadosBasicosTarefa.dataConclusao,
//                                QPessoa.create(
//                                        qPessoa.id,
//                                        qPessoa.nomeRazaoSocial
//                                ),
//                                qDadosBasicosTarefa.rotulo
//                        ),
//                        QProcesso.create(
//                                qProcesso.id,
//                                qProcesso.processoUnico
//                        ),
//                        QFluxoTrabalhoTarefa.create(
//                                qFluxoTrabalhoTarefa.id,
//                                QTarefa.create(
//                                        qTarefa.id,
//                                        qTarefa.nome
//                                )
//                        )
//                ))
//                .from(qDadosBasicosTarefa)
//                .join(qDadosBasicosTarefa.processo, qProcesso)
//                .join(qDadosBasicosTarefa.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefa)
//                .join(qFluxoTrabalhoTarefa.tarefa, qTarefa)
//                .join(qFluxoTrabalhoTarefa.filas, qFilaTarefa)
//                .join(qFilaTarefa.fila, qFila)
//                .leftJoin(qDadosBasicosTarefa.responsavel, qPessoa)
//                .where(qDadosBasicosTarefa.dataConclusao.isNull())
//                .where(qDadosBasicosTarefa.eq(dadosBasicosTarefa))
//                .fetchFirst());
//    }
//
//
//    @Override
//    public Optional<DadosBasicosTarefa> findTarefaNaoConcluidaByUsuarioAndFila(final Pessoa pessoa, final Fila fila) {
//        final QDadosBasicosTarefa qDadosBasicosTarefa = QDadosBasicosTarefa.dadosBasicosTarefa;
//        final QFilaPessoa qFilaPessoa = QFilaPessoa.filaPessoa;
//        final QFilaTarefa qFilaTarefa = QFilaTarefa.filaTarefa;
//        final QProcesso qProcesso = QProcesso.processo1;
//        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;
//
//
//        return Optional.ofNullable(new JPAQueryFactory(entityManager)
//                .select(qDadosBasicosTarefa)
//                .from(qDadosBasicosTarefa)
//                .join(qDadosBasicosTarefa.processo, qProcesso)
//                .join(qDadosBasicosTarefa.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefa)
//                .join(qFilaPessoa).on(qFilaPessoa.pessoa.eq(pessoa).and(qFilaPessoa.fila.eq(fila)))
//                .join(qFilaTarefa).on(qFilaTarefa.fila.eq(qFilaPessoa.fila)
//                        .and(qFilaTarefa.fluxoTrabalhoTarefa.eq(qFluxoTrabalhoTarefa))
//                        .and(qFilaTarefa.carteira.eq(qProcesso.carteira)))
//                .where(qDadosBasicosTarefa.dataConclusao.isNull())
//                .where(qDadosBasicosTarefa.responsavel.eq(pessoa))
//                .fetchFirst());
//    }
//
//    private JPAQuery<AtendimentoFila> createQuery(Query<AtendimentoFila> query, List<EnumStatusTarefa> enumStatusTarefaList, EnumTarefaStatus enumTarefaStatus) {
//
//        final QDadosBasicosTarefa qDadosBasicosTarefa = new QDadosBasicosTarefa("dadosBasicosTarefa");
//        final QProcesso qProcesso = QProcesso.processo1;
//        final QTarefa qTarefa = QTarefa.tarefa;
//        final QFilaTarefa qFilaTarefa = QFilaTarefa.filaTarefa;
//        final QFila qFila = QFila.fila;
//        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;
//        final QPessoa qResponsavelTarefa = QPessoa.pessoa;
//        final QHistoricoFilaDevolucao qHistoricoFilaDevolucao = QHistoricoFilaDevolucao.historicoFilaDevolucao;
//
//        JPAQuery<AtendimentoFila> jpaQuery = new JPAQueryFactory(entityManager)
//                .selectDistinct(new QAtendimentoFila(
//                        qDadosBasicosTarefa.id,
//                        QDadosBasicosTarefa.create(
//                                qDadosBasicosTarefa.id,
//                                QProcesso.create(
//                                        qProcesso.id,
//                                        qProcesso.processoUnico
//                                ),
//                                qDadosBasicosTarefa.dataInicio,
//                                qDadosBasicosTarefa.dataAgendamento,
//                                qDadosBasicosTarefa.dataPrazoFatal,
//                                qDadosBasicosTarefa.status,
//                                QPessoa.create(
//                                        qResponsavelTarefa.id,
//                                        qResponsavelTarefa.nomeRazaoSocial
//                                ),
//                                qDadosBasicosTarefa.memo,
//                                QFluxoTrabalhoTarefa.create(
//                                        qFluxoTrabalhoTarefa.id,
//                                        QTarefa.create(
//                                                qTarefa.id,
//                                                qTarefa.nome
//                                        )
//                                )
//                        ),
//                        QProcesso.create(
//                                qProcesso.id,
//                                qProcesso.processoUnico
//                        ),
//                        QFluxoTrabalhoTarefa.create(
//                                qFluxoTrabalhoTarefa.id,
//                                QTarefa.create(
//                                        qTarefa.id,
//                                        qTarefa.nome
//                                )
//                        )
//                ))
//                .from(qDadosBasicosTarefa)
//                .join(qDadosBasicosTarefa.processo, qProcesso)
//                .join(qDadosBasicosTarefa.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefa)
//                .join(qFluxoTrabalhoTarefa.tarefa, qTarefa)
//                .join(qFluxoTrabalhoTarefa.filas, qFilaTarefa)
//                .join(qFilaTarefa.fila, qFila).on(qFilaTarefa.carteira.eq(qProcesso.carteira))
//                .leftJoin(qHistoricoFilaDevolucao).on(qDadosBasicosTarefa.eq(qHistoricoFilaDevolucao.dadosBasicosTarefa))
//                .leftJoin(qDadosBasicosTarefa.responsavel, qResponsavelTarefa)
//                .where(qDadosBasicosTarefa.dataConclusao.isNull());
//
//        // filter
//        if (query.getFilter() != null && query.getFilter() instanceof AtendimentoFilaFilter) {
//            final AtendimentoFilaFilter atendimentoFilaFilter = (AtendimentoFilaFilter) query.getFilter();
//
//            whereFila(jpaQuery, atendimentoFilaFilter);
//
//            if (StringUtils.isNotEmpty(atendimentoFilaFilter.getProcessoUnico())) {
//                jpaQuery.where(qProcesso.processoUnico.likeIgnoreCase("%" + atendimentoFilaFilter.getProcessoUnico() + "%"));
//            }
//
//            if (StringUtils.isNotEmpty(atendimentoFilaFilter.getTarefa())) {
//                jpaQuery.where(qFluxoTrabalhoTarefa.tarefa.nome.likeIgnoreCase("%" + atendimentoFilaFilter.getTarefa() + "%"));
//            }
//
//            if (StringUtils.isNotEmpty(atendimentoFilaFilter.getMemo())){
//                jpaQuery.where(qDadosBasicosTarefa.memo.likeIgnoreCase("%"+atendimentoFilaFilter.getMemo()+"%"));
//            }
//
//            if (Objects.nonNull(atendimentoFilaFilter.getDataPrazoInicio())) {
//                jpaQuery.where(qDadosBasicosTarefa.dataAgendamento.goe(atendimentoFilaFilter.getDataPrazoInicio()));
//            }
//
//            if (Objects.nonNull(atendimentoFilaFilter.getDataPrazoFim())) {
//                jpaQuery.where(qDadosBasicosTarefa.dataAgendamento.loe(atendimentoFilaFilter.getDataPrazoFim()));
//            }
//
//            if (Objects.nonNull(atendimentoFilaFilter.isRetornarTarefasEmAtendimento()) && !atendimentoFilaFilter.isRetornarTarefasEmAtendimento()) {
//                jpaQuery.where(qDadosBasicosTarefa.responsavel.isNull());
//            }
//
//            if (Objects.nonNull(atendimentoFilaFilter.getDescricaoMotivo()) && StringUtils.isNotEmpty(atendimentoFilaFilter.getDescricaoMotivo())) {
//                jpaQuery.where(qHistoricoFilaDevolucao.descricao.likeIgnoreCase("%"+atendimentoFilaFilter.getDescricaoMotivo()+"%"));
//            }
//
//            if (Objects.nonNull(atendimentoFilaFilter.getMotivoDevolucao())){
//                jpaQuery.where(qHistoricoFilaDevolucao.motivo.eq(EnumMotivoDevolucaoTarefa.getById(atendimentoFilaFilter.getMotivoDevolucao())));
//            }
//
//        }
//
//        if (query.getSorter() != null) {
//
//            if (query.getSorter().getProperty().contains(".")) {
//                String[] entidadeCampo = query.getSorter().getProperty().split("\\.");
//                PathBuilder entityPath = null;
//                String column = entidadeCampo[1];
//                if (entidadeCampo[0].equalsIgnoreCase("PROCESSO")) {
//                    entityPath = new PathBuilder<>(Processo.class, "processo1");
//                    if (column.equalsIgnoreCase("NUMERO")) {
//                        column = "id";
//                    }
//                } else if (entidadeCampo[0].equalsIgnoreCase("DADOSBASICOSTAREFA")) {
//                    entityPath = new PathBuilder<>(DadosBasicosTarefa.class, "dadosBasicosTarefa");
//                } else if (entidadeCampo[0].equalsIgnoreCase("TAREFA")) {
//                    entityPath = new PathBuilder<>(Tarefa.class, "tarefa");
//                }
//
//                if (query.getSorter().isDesc()) {
//                    jpaQuery.orderBy(entityPath.getString(column).desc());
//                } else {
//                    jpaQuery.orderBy(entityPath.getString(column).asc());
//                }
//            }
//        } else {
//            jpaQuery.orderBy(qDadosBasicosTarefa.dataAgendamento.asc());
//        }
//
//
//        return jpaQuery;
//    }
//
//    private JPAQuery<AtendimentoFila> createQueryDevolucao(Query<AtendimentoFila> query) {
//        final QDadosBasicosTarefa qDadosBasicosTarefa = QDadosBasicosTarefa.dadosBasicosTarefa;
//        final QProcesso qProcesso = QProcesso.processo1;
//        final QTarefa qTarefa = QTarefa.tarefa;
//        final QFilaTarefa qFilaTarefa = QFilaTarefa.filaTarefa;
//        final QFila qFila = QFila.fila;
//        final QCarteira qCarteira = QCarteira.carteira;
//        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;
//        final QFilaEspera qFilaEspera = QFilaEspera.filaEspera;
//
//        JPAQuery<AtendimentoFila> jpaQuery = new JPAQueryFactory(entityManager)
//                .selectDistinct(new QAtendimentoFila(
//                        qDadosBasicosTarefa.id,
//                        QDadosBasicosTarefa.create(
//                                qDadosBasicosTarefa.id,
//                                qDadosBasicosTarefa.dataInicio,
//                                qDadosBasicosTarefa.dataAgendamento,
//                                qDadosBasicosTarefa.dataPrazoFatal,
//                                qDadosBasicosTarefa.rotulo,
//                                qDadosBasicosTarefa.status
//                        ),
//                        QProcesso.create(
//                                qProcesso.id,
//                                qProcesso.processoUnico
//                        ),
//                        QFluxoTrabalhoTarefa.create(
//                                qFluxoTrabalhoTarefa.id,
//                                QTarefa.create(
//                                        qTarefa.id,
//                                        qTarefa.nome
//                                )
//                        )
//                ))
//                .from(qDadosBasicosTarefa)
//                .join(qDadosBasicosTarefa.processo, qProcesso)
//                .join(qDadosBasicosTarefa.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefa)
//                .join(qFluxoTrabalhoTarefa.tarefa, qTarefa)
//                .join(qFluxoTrabalhoTarefa.filas, qFilaTarefa)
//                .join(qFilaTarefa.fila, qFila).on(qFilaTarefa.carteira.eq(qProcesso.carteira))
//                .where(qDadosBasicosTarefa.dataConclusao.isNull())
//                .where(qDadosBasicosTarefa.responsavel.isNull())
//                .where( JPAExpressions
//                        .select(qFilaEspera)
//                        .from(qFilaEspera)
//                        .where(qFilaEspera.filaAtivaEspera.eq(qFila))
//                        .notExists().and(qFila.ativo.eq(Boolean.TRUE)));
//
//        // filter
//        if (query.getFilter() != null && query.getFilter() instanceof AtendimentoFilaFilter) {
//            final AtendimentoFilaFilter atendimentoFilaFilter = (AtendimentoFilaFilter) query.getFilter();
//
//            whereFila(jpaQuery, atendimentoFilaFilter);
//        }
//
//        if (query.getSorter() != null) {
//
//            if (query.getSorter().getProperty().contains(".")) {
//                String[] entidadeCampo = query.getSorter().getProperty().split("\\.");
//                PathBuilder entityPath = null;
//                String column = entidadeCampo[1];
//                if (entidadeCampo[0].equalsIgnoreCase("PROCESSO")) {
//                    entityPath = new PathBuilder<>(Processo.class, "processo1");
//                    if (column.equalsIgnoreCase("NUMERO")) {
//                        column = "id";
//                    }
//                } else if (entidadeCampo[0].equalsIgnoreCase("DADOSBASICOSTAREFA")) {
//                    entityPath = new PathBuilder<>(DadosBasicosTarefa.class, "dadosBasicosTarefa");
//                } else if (entidadeCampo[0].equalsIgnoreCase("TAREFA")) {
//                    entityPath = new PathBuilder<>(Tarefa.class, "tarefa");
//                }
//
//                if (query.getSorter().isDesc()) {
//                    jpaQuery.orderBy(entityPath.getString(column).desc());
//                } else {
//                    jpaQuery.orderBy(entityPath.getString(column).asc());
//                }
//            }
//        } else {
//            if (Objects.nonNull(qDadosBasicosTarefa.dataPrazoFatal)) {
//                jpaQuery.orderBy(qDadosBasicosTarefa.dataPrazoFatal.asc());
//            } else if (Objects.nonNull(qDadosBasicosTarefa.dataAgendamento)) {
//                jpaQuery.orderBy(qDadosBasicosTarefa.dataAgendamento.asc());
//            } else if (Objects.nonNull(qDadosBasicosTarefa.dataInicio)) {
//                jpaQuery.orderBy(qDadosBasicosTarefa.dataInicio.asc());
//            }
//        }
//
//        return jpaQuery;
//    }
//
//    @Override
//    public Long countTarefasPrioritariasTratadasHoje(Fila fila) {
//        final QDadosBasicosTarefa qDadosBasicosTarefa = QDadosBasicosTarefa.dadosBasicosTarefa;
//
//        final QFila qFila = QFila.fila;
//        final QTarefa qTarefa = QTarefa.tarefa;
//        final QProcesso qProcesso = QProcesso.processo1;
//        final QCarteira qCarteira = QCarteira.carteira;
//        final QHistoricoFilaDevolucao qHistoricoFilaDevolucao = QHistoricoFilaDevolucao.historicoFilaDevolucao;
//        final QFilaTarefa qFilaTarefa = QFilaTarefa.filaTarefa;
//        final QTag qTag = QTag.tag;
//        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;
//
//        return new JPAQueryFactory(entityManager)
//                .select(qTarefa.id.countDistinct())
//                .from(qDadosBasicosTarefa)
//                .join(qDadosBasicosTarefa.processo, qProcesso)
//                .join(qProcesso.carteira, qCarteira)
//                .join(qDadosBasicosTarefa.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefa)
//                .join(qFluxoTrabalhoTarefa.filas, qFilaTarefa)
//                .join(qFilaTarefa.fila, qFila).on(qFilaTarefa.carteira.eq(qCarteira))
//                .join(qFila.historicoFilaDevolucoes, qHistoricoFilaDevolucao)
//                .join(qFila.tags, qTag)
//                .where(qFluxoTrabalhoTarefa.eq(qDadosBasicosTarefa.fluxoTrabalhoTarefa))
//                .where(qHistoricoFilaDevolucao.dadosBasicosTarefa.eq(qDadosBasicosTarefa))
//                .where(qHistoricoFilaDevolucao.fluxoTrabalhoTarefa.eq(qFluxoTrabalhoTarefa))
//                .where(qFila.id.eq(fila.getId()))
//                .where(qHistoricoFilaDevolucao.dataTratamento.goe(Util.getInicioDoDia()))
//                .fetchOne();
//    }
//
//    @Override
//    public Long countTarefasPrioridadeConcluidasHoje(Long idFila) {
//
//        final QHistoricoFilaPessoa qHistoricoFilaPessoa = QHistoricoFilaPessoa.historicoFilaPessoa;
//        final QFila qFila = QFila.fila;
//        final QFilaTag qFilaTag = QFilaTag.filaTag;
//        final QTag qTag = QTag.tag;
//        final QProcessoTag qProcessoTag = QProcessoTag.processoTag;
//        final QProcesso qProcesso = QProcesso.processo1;
//
//        Calendar inicioDia = Calendar.getInstance();
//        Util.correcaoInicioDia(inicioDia);
//
//        return new JPAQueryFactory(entityManager)
//                .selectDistinct(qHistoricoFilaPessoa.id)
//                .from(qHistoricoFilaPessoa)
//                .innerJoin(qFila).on(qHistoricoFilaPessoa.fila.id.eq(qFila.id))
//                .innerJoin(qFilaTag).on(qFila.id.eq(qFilaTag.fila.id))
//                .innerJoin(qTag).on(qTag.id.eq(qFilaTag.tag.id))
//                .innerJoin(qProcessoTag).on(qProcessoTag.tag.id.eq(qFilaTag.tag.id))
//                .innerJoin(qProcesso).on(qProcesso.id.eq(qProcessoTag.processo.id))
//                .where(qHistoricoFilaPessoa.fila.id.eq(idFila))
//                .where(qHistoricoFilaPessoa.dataConclusao.gt(inicioDia))
//                .where(qHistoricoFilaPessoa.dataInicio.gt(inicioDia))
//                .where(qHistoricoFilaPessoa.dataDevolucao.isNull()).fetchCount();
//    }
//
//    @Override
//    public List<HistoricoFilaPessoa> consultaSolicitacoesConcluidasDataPessoaFila(Long idFila, Long idPessoa, Calendar dataInicio, Calendar dataFim) {
//        final QHistoricoFilaPessoa qHistoricoFilaPessoa = QHistoricoFilaPessoa.historicoFilaPessoa;
//        final QFila qFila = QFila.fila;
//
//        JPAQuery<HistoricoFilaPessoa> jpaQuery = new JPAQueryFactory(entityManager)
//                .select(qHistoricoFilaPessoa)
//                .from(qHistoricoFilaPessoa)
//                .join(qHistoricoFilaPessoa.fila, qFila)
//                .where(qFila.id.eq(idFila))
//                .where(qHistoricoFilaPessoa.dataInicio.goe(dataInicio))
//                .where(qHistoricoFilaPessoa.dataConclusao.loe(dataFim));
//
//        if (idPessoa != null) {
//            jpaQuery.where(qHistoricoFilaPessoa.pessoa.id.eq(idPessoa));
//        }
//
//        jpaQuery.orderBy(qHistoricoFilaPessoa.dataConclusao.asc());
//
//        return jpaQuery.fetch();
//    }
//
//    private void whereTag(JPAQuery query, Fila fila, List<String> tags) {
//        final QProcesso qProcesso = QProcesso.processo1;
//        final QProcessoTag qProcessoTag = QProcessoTag.processoTag;
//        final QTag qTag = QTag.tag;
//
//        if (fila != null && fila.isProcessosSemTag()) {
//            query.where(qProcesso.id.notIn(JPAExpressions
//                    .select(qProcessoTag.processo.id)
//                    .from(qProcessoTag).fetchAll()));
//        } else {
//            if (tags != null && !tags.isEmpty()) {
//                query.join(qProcesso.tags, qTag);
//                query.where(qTag.nome.in(tags));
//            }
//        }
//    }
//
//    /**
//     * Insere os parâmetros de configuração de tempo de visão da fila
//     *
//     * @param query
//     * @param fila
//     */
//    private void whereDueDate(JPAQuery query, Fila fila) {
//        if (fila == null) {
//            return;
//        }
//
//        if (fila.isFilaDevolucao()) {
//            return;
//        }
//
//        QDadosBasicosTarefa qDadosBasicosTarefa = QDadosBasicosTarefa.dadosBasicosTarefa;
//
//        if (fila.getVisualizarTarefasVencidas() == null || !fila.getVisualizarTarefasVencidas()) {
//            query.where(qDadosBasicosTarefa.dataAgendamento.isNull()
//                    .or(qDadosBasicosTarefa.dataAgendamento.between(Util.getInicioDoDia(), getDataTempoVisao(fila.getTempoVisao(), false))));
//        } else {
//            if (Objects.equals(fila.getTempoVisaoVencidas(), 0L) || fila.getTempoVisaoVencidas() == null) {
//                query.where(qDadosBasicosTarefa.dataAgendamento.isNull()
//                        .or(qDadosBasicosTarefa.dataAgendamento.loe(getDataTempoVisao(fila.getTempoVisao(), false))));
//            } else {
//                query.where(qDadosBasicosTarefa.dataAgendamento.isNull()
//                        .or(qDadosBasicosTarefa.dataAgendamento.between(getDataTempoVisao(fila.getTempoVisaoVencidas() * -1, true),
//                                getDataTempoVisao(fila.getTempoVisao(), false))));
//            }
//        }
//    }
//
//    private Calendar getDataTempoVisao(Integer tempoVisao, Boolean inicioDia) {
//        LocalDateTime now = LocalDateTime
//                .now()
//                .plusDays(tempoVisao);
//
//        DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern(Util.PATTERN_DATA_HORA).toFormatter();
//        Calendar retorno = Util.getStringToDate(now.format(formatter), Util.PATTERN_DATA_HORA);
//
//        if (inicioDia) {
//            Util.correcaoInicioDia(retorno);
//        } else {
//            Util.correcaoFimDia(retorno);
//        }
//
//        return retorno;
//    }
//
//    private void whereFila(JPAQuery<AtendimentoFila> jpaQuery, AtendimentoFilaFilter filter) {
//
//        if (filter.getFila().isFilaDevolucao()) {
//            jpaQuery.where(QDadosBasicosTarefa.dadosBasicosTarefa.status.eq(EnumStatusTarefa.DEVOLVIDA));
//            jpaQuery.where(QFila.fila.esteira.eq(filter.getFila().getEsteira()));
//        } else {
//            jpaQuery.where(QDadosBasicosTarefa.dadosBasicosTarefa.status.eq(EnumStatusTarefa.PENDENTE));
//            jpaQuery.where(QFila.fila.eq(filter.getFila()));
//        }
//
//        jpaQuery.where(QFila.fila.ativo.eq(true));
//
//        if (Objects.nonNull(filter.getStatusTarefa())) {
//            whereTarefaStatus(jpaQuery, EnumTarefaStatus.getById(filter.getStatusTarefa()));
//        }
//
//        //Filtra as tarefas dos processos de acordo com o operacional parametrizado na fila
//        if (filter.getFila().getOperacional() != null && filter.getFila().getOperacional().getId() != null) {
//            jpaQuery.where(QFila.fila.operacional.id.in(
//                    JPAExpressions
//                            .select(QProcesso.processo1.operacional.id)
//                            .from(QProcesso.processo1)
//                            .where(QProcesso.processo1.eq(QDadosBasicosTarefa.dadosBasicosTarefa.processo))
//            ));
//        }
//
//        jpaQuery.where(QFila.fila.expressao.isNull().or(QDadosBasicosTarefa.dadosBasicosTarefa.memo.contains(QFila.fila.expressao)));
//
//        jpaQuery.where(QFila.fila.dataRecebimentoInicial.isNull().or(QProcesso.processo1.dataRecebimento.goe(QFila.fila.dataRecebimentoInicial)));
//        jpaQuery.where(QFila.fila.dataRecebimentoFinal.isNull().or(QProcesso.processo1.dataRecebimento.loe(QFila.fila.dataRecebimentoFinal)));
//
//
//        whereDueDate(jpaQuery, filter.getFila());
//
//        whereTag(jpaQuery, filter.getFila(), filter.getTags());
//    }
//
//    private void whereTarefaStatus(JPAQuery<?> jpaQuery, EnumTarefaStatus enumTarefaStatus) {
//
//        final QDadosBasicosTarefa qDadosBasicosTarefa = QDadosBasicosTarefa.dadosBasicosTarefa;
//
//        Calendar dataAtual = Calendar.getInstance();
//        Calendar dataAtualFim = DateUtils.setFimDeDia(Calendar.getInstance());
//
//        Predicate predicate = null;
//
//        switch (enumTarefaStatus) {
//
//            case CONCLUIDO:
//
//                predicate = qDadosBasicosTarefa.dataConclusao.isNotNull();
//
//                break;
//
//            case VENCENDO_NO_DIA:
//
//                predicate = qDadosBasicosTarefa.dataConclusao.isNull().and(
//                        qDadosBasicosTarefa.dataAgendamento.between(dataAtual, dataAtualFim));
//
//                break;
//
//            case ATRASO:
//
//                predicate = qDadosBasicosTarefa.dataConclusao.isNull().and(
//                        qDadosBasicosTarefa.dataAgendamento.lt(dataAtual));
//
//                break;
//
//            case EM_ANDAMENTO:
//
//                predicate = qDadosBasicosTarefa.dataConclusao.isNull().and(
//                        qDadosBasicosTarefa.dataAgendamento.gt(dataAtualFim));
//
//                break;
//
//        }
//
//        jpaQuery.where(predicate);
//
//    }
//
//}
//
