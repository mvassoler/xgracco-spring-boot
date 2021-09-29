//package br.com.finchsolucoes.xgracco.infra.repository;
//
//import br.com.finchsolucoes.xgracco.domain.entity.*;
//import br.com.finchsolucoes.xgracco.domain.query.Query;
//import br.com.finchsolucoes.xgracco.domain.query.impl.ProcessoPedidoFilter;
//import com.querydsl.core.types.dsl.PathBuilder;
//import com.querydsl.jpa.impl.JPAQuery;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import com.querydsl.jpa.impl.JPAUpdateClause;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.stereotype.Repository;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Repository
//public class ProcessoPedidoRepositoryImpl extends AbstractJpaRepository<ProcessoPedido, Long> implements ProcessoPedidoRepository {
//
//
//    public List<ProcessoPedido> find(Processo processo, Query<ProcessoPedido> query) {
//        final PathBuilder<ProcessoPedido> path = new PathBuilder<>(ProcessoPedido.class, "pedido");
//        final JPAQuery jpaQuery = createQuery(processo, query);
//
//        // order
//        if (query.getSorter() != null && StringUtils.isNotBlank(query.getSorter().getProperty())) {
//            if (query.getSorter().isDesc()) {
//                jpaQuery.orderBy(path.getString(query.getSorter().getProperty()).desc());
//            } else {
//                jpaQuery.orderBy(path.getString(query.getSorter().getProperty()).asc());
//            }
//        } else {
//            jpaQuery.orderBy(path.getString("id").asc());
//        }
//
//        // page
//        if (Optional.ofNullable(query.getPage()).orElse(0L) > 0L) {
//            jpaQuery.offset(((query.getPage() - 1L) * query.getLimit()));
//        }
//
//        // limit
//        jpaQuery.limit(query.getLimit());
//
//        return jpaQuery.fetch();
//    }
//
//    @Override
//    public Long count(Processo processo, Query<ProcessoPedido> query) {
//        return createQuery(processo, query).fetchCount();
//    }
//
//    private JPAQuery<ProcessoPedido> createQuery(Processo processo, Query<ProcessoPedido> query) {
//        final QProcessoPedido qProcessoPedido = QProcessoPedido.processoPedido;
//        final QTipoPedido qTipoPedido = QTipoPedido.tipoPedido;
//        final QRiscoCausa qRiscoCausa = QRiscoCausa.riscoCausa;
//        final QProcesso qProcesso = QProcesso.processo1;
//
//        final JPAQuery<ProcessoPedido> jpaQuery = new JPAQueryFactory(entityManager)
//                .select(QProcessoPedido.create(
//                        qProcessoPedido.id,
//                        qProcessoPedido.descricao,
//                        qProcessoPedido.valor,
//                        QTipoPedido.create(
//                                qTipoPedido.id,
//                                qTipoPedido.descricao
//                        ),
//                        QRiscoCausa.create(
//                                qRiscoCausa.id,
//                                qRiscoCausa.descricao,
//                                qRiscoCausa.probabilidade
//                        ),
//                        QProcesso.create(
//                                qProcesso.id
//                        ),
//                        qProcessoPedido.multaValorFixo,
//                        qProcessoPedido.percentualMulta,
//                        qProcessoPedido.multaMemo,
//                        qProcessoPedido.possibilidadePerdaPercent,
//                        qProcessoPedido.valorProvisao,
//                        qProcessoPedido.embasamentoProvisao,
//                        qProcessoPedido.dataCadastro,
//                        qProcessoPedido.motivoAlteracao,
//                        qProcessoPedido.sucumbencia,
//                        qProcessoPedido.encargos))
//                .from(qProcessoPedido)
//                .leftJoin(qProcessoPedido.tipoPedido, qTipoPedido)
//                .leftJoin(qProcessoPedido.riscoCausa, qRiscoCausa)
//                .leftJoin(qProcessoPedido.processo, qProcesso)
//                .where(qProcessoPedido.processo.eq(processo));
//
//        // filter
//        if (query.getFilter() != null && query.getFilter() instanceof ProcessoPedidoFilter) {
//            final ProcessoPedidoFilter filter = (ProcessoPedidoFilter) query.getFilter();
//
//            if (org.apache.commons.lang3.StringUtils.isNotBlank(filter.getDescricao())) {
//                jpaQuery.where(qProcessoPedido.descricao.likeIgnoreCase("%" + filter.getDescricao() + "%"));
//            }
//            if (filter.getTipoPedido() != null) {
//                jpaQuery.where(qProcessoPedido.tipoPedido.eq(filter.getTipoPedido()));
//            }
//            if (filter.getRiscoCausa() != null) {
//                jpaQuery.where(qProcessoPedido.riscoCausa.eq(filter.getRiscoCausa()));
//            }
//
//        }
//
//        return jpaQuery;
//    }
//
//
//    public Optional<ProcessoPedido> findById(Processo processo, Long id) {
//        final QProcessoPedido qProcessoPedido = QProcessoPedido.processoPedido;
//        final QTipoPedido qTipoPedido = QTipoPedido.tipoPedido;
//        final QRiscoCausa qRiscoCausa = QRiscoCausa.riscoCausa;
//        final QProcesso qProcesso = QProcesso.processo1;
//
//        return Optional.ofNullable(new JPAQueryFactory(entityManager)
//                .select(QProcessoPedido.create(
//                        qProcessoPedido.id,
//                        qProcessoPedido.descricao,
//                        qProcessoPedido.valor,
//                        QTipoPedido.create(
//                                qTipoPedido.id,
//                                qTipoPedido.descricao
//                        ),
//                        QRiscoCausa.create(
//                                qRiscoCausa.id,
//                                qRiscoCausa.descricao,
//                                qRiscoCausa.probabilidade
//                        ),
//                        QProcesso.create(
//                                qProcesso.id
//                        ),
//                        qProcessoPedido.multaValorFixo,
//                        qProcessoPedido.percentualMulta,
//                        qProcessoPedido.multaMemo,
//                        qProcessoPedido.possibilidadePerdaPercent,
//                        qProcessoPedido.valorProvisao,
//                        qProcessoPedido.embasamentoProvisao,
//                        qProcessoPedido.dataCadastro,
//                        qProcessoPedido.motivoAlteracao,
//                        qProcessoPedido.sucumbencia,
//                        qProcessoPedido.encargos))
//                .from(qProcessoPedido)
//                .leftJoin(qProcessoPedido.tipoPedido, qTipoPedido)
//                .leftJoin(qProcessoPedido.riscoCausa, qRiscoCausa)
//                .leftJoin(qProcessoPedido.processo, qProcesso)
//                .where(qProcessoPedido.processo.eq(processo))
//                .where(qProcessoPedido.id.eq(id))
//                .fetchOne());
//    }
//
//
//    public List<PedidoIndice> findIndices(ProcessoPedido processoPedido) {
//        final QPedidoIndice qPedidoIndice = QPedidoIndice.pedidoIndice;
//        final QIndiceEconomico qIndiceEconomico = QIndiceEconomico.indiceEconomico;
//        final QProcessoPedido qProcessoPedido = QProcessoPedido.processoPedido;
//
//        return new JPAQueryFactory(entityManager)
//                .select(QPedidoIndice.create(
//                        qPedidoIndice.id,
//                        qPedidoIndice.dataInicio,
//                        qPedidoIndice.dataFinal,
//                        qPedidoIndice.valorFixo,
//                        QIndiceEconomico.create(
//                                qIndiceEconomico.id,
//                                qIndiceEconomico.descricao,
//                                qIndiceEconomico.periodoJurosCorrecao,
//                                qIndiceEconomico.tipoIndiceEconomico
//                        ),
//                        QProcessoPedido.create(
//                                qProcessoPedido.id
//                        )
//                ))
//                .from(qPedidoIndice)
//                .leftJoin(qPedidoIndice.indiceEconomico, qIndiceEconomico)
//                .join(qPedidoIndice.processoPedido, qProcessoPedido)
//                .where(qProcessoPedido.eq(processoPedido))
//                .fetch();
//    }
//
//
//    public List<PedidoJuros> findJuros(ProcessoPedido processoPedido) {
//        final QPedidoJuros qPedidoJuros = QPedidoJuros.pedidoJuros;
//        final QJuros qJuros = QJuros.juros;
//        final QProcessoPedido qProcessoPedido = QProcessoPedido.processoPedido;
//
//        return new JPAQueryFactory(entityManager)
//                .select(QPedidoJuros.create(
//                        qPedidoJuros.id,
//                        qPedidoJuros.proRata,
//                        qPedidoJuros.dataInicio,
//                        qPedidoJuros.dataFinal,
//                        qPedidoJuros.valorFixo,
//                        qPedidoJuros.percentualManual,
//                        qPedidoJuros.periodicidadeJuros,
//                        qPedidoJuros.tipoJuros,
//                        QJuros.create(
//                                qJuros.id,
//                                qJuros.descricao,
//                                qJuros.percentual,
//                                qJuros.tipoJuros,
//                                qJuros.periodoJurosCorrecao
//                        ),
//                        QProcessoPedido.create(
//                                qProcessoPedido.id
//                        )
//                ))
//                .from(qPedidoJuros)
//                .leftJoin(qPedidoJuros.juros, qJuros)
//                .join(qPedidoJuros.processoPedido, qProcessoPedido)
//                .where(qPedidoJuros.processoPedido.eq(processoPedido))
//                .fetch();
//    }
//
//
//    public List<ProcessoPedido> getPedidosProcesso(Long idProcesso) {
//        final QProcessoPedido qProcessoPedido = QProcessoPedido.processoPedido;
//        final QTipoPedido qTipoPedido = QTipoPedido.tipoPedido;
//        final QPedidoIndice qPedidoIndice = QPedidoIndice.pedidoIndice;
//        final QPedidoJuros qPedidoJuros = QPedidoJuros.pedidoJuros;
//        final QJuros qJuros = QJuros.juros;
//        final QProcessoPedidoCenarios qProcessoPedidoCenarios = QProcessoPedidoCenarios.processoPedidoCenarios;
//        final QProcesso qProcesso = QProcesso.processo1;
//
//        final List<ProcessoPedido> pedidos = new JPAQueryFactory(entityManager)
//                .select(QProcessoPedido.create(
//                        qProcessoPedido.id,
//                        qProcessoPedido.descricao,
//                        qProcessoPedido.valor,
//                        QTipoPedido.create(
//                                qTipoPedido.id,
//                                qTipoPedido.descricao
//                        ),
//                        qProcessoPedido.multaValorFixo,
//                        qProcessoPedido.percentualMulta,
//                        qProcessoPedido.multaMemo,
//                        qProcessoPedido.possibilidadePerdaPercent,
//                        qProcessoPedido.valorProvisao,
//                        qProcessoPedido.embasamentoProvisao,
//                        qProcessoPedido.dataCadastro,
//                        qProcessoPedido.motivoAlteracao,
//                        qProcessoPedido.sucumbencia,
//                        qProcessoPedido.encargos,
//                        qProcessoPedido.cenariosHabilitados,
//                        QProcessoPedidoCenarios.create(
//                                qProcessoPedidoCenarios.id,
//                                qProcessoPedidoCenarios.valorCorrigidoCenA,
//                                qProcessoPedidoCenarios.valorCorrigidoCenB,
//                                qProcessoPedidoCenarios.valorCorrigidoCenC
//                        ),
//                        QProcesso.create(
//                                qProcesso.id
//                        )
//                ))
//                .from(qProcessoPedido)
//                .join(qProcessoPedido.processo, qProcesso)
//                .join(qProcessoPedido.tipoPedido, qTipoPedido)
//                .leftJoin(qProcessoPedido.cenario, qProcessoPedidoCenarios)
//                .where(qProcessoPedido.processo.id.eq(idProcesso))
//                .fetch();
//
//        final List<PedidoIndice> indices = new JPAQueryFactory(entityManager)
//                .select(QPedidoIndice.create(
//                        qPedidoIndice.id,
//                        qPedidoIndice.dataInicio,
//                        qPedidoIndice.dataFinal,
//                        qPedidoIndice.valorFixo,
//                        qPedidoIndice.indiceEconomico,
//                        QProcessoPedido.create(qProcessoPedido.id)
//                ))
//                .from(qPedidoIndice)
//                .join(qPedidoIndice.processoPedido, qProcessoPedido)
//                .where(qProcessoPedido.processo.id.eq(idProcesso))
//                .fetch();
//
//        final List<PedidoJuros> juros = new JPAQueryFactory(entityManager)
//                .select(QPedidoJuros.create(
//                        qPedidoJuros.id,
//                        qPedidoJuros.proRata,
//                        qPedidoJuros.dataInicio,
//                        qPedidoJuros.dataFinal,
//                        qPedidoJuros.valorFixo,
//                        qPedidoJuros.percentualManual,
//                        qPedidoJuros.periodicidadeJuros,
//                        qPedidoJuros.tipoJuros,
//                        qPedidoJuros.juros,
//                        QProcessoPedido.create(qProcessoPedido.id)
//                ))
//                .from(qPedidoJuros)
//                .join(qPedidoJuros.processoPedido, qProcessoPedido)
//                .leftJoin(qPedidoJuros.juros, qJuros)
//                .where(qProcessoPedido.processo.id.eq(idProcesso))
//                .fetch();
//
//        pedidos.forEach(pedido -> {
//            pedido.setIndices(indices.stream().filter(indice -> pedido.equals(indice.getProcessoPedido())).collect(Collectors.toList()));
//            pedido.setJuros(juros.stream().filter(juro -> pedido.equals(juro.getProcessoPedido())).collect(Collectors.toList()));
//        });
//
//        return pedidos;
//
//    }
//
//
//    public List<Processo> findProcessoByProcessoPedido() {
//        final QProcessoPedido qProcessoPedido = QProcessoPedido.processoPedido;
//        final QProcesso qProcesso = QProcesso.processo1;
//
//        return new JPAQueryFactory(entityManager)
//                .selectDistinct(QProcesso.create(
//                                        qProcesso.id,
//                                        qProcesso.numero))
//                .from(qProcessoPedido)
//                .join(qProcessoPedido.processo, qProcesso).fetch();
//    }
//
//
//    public List<ProcessoPedido> findPedidosFetchCenario() {
//        final QProcessoPedido qProcessoPedido = QProcessoPedido.processoPedido;
//        final QProcesso qProcesso = QProcesso.processo1;
//        final QProcessoPedidoCenarios qProcessoPedidoCenarios = QProcessoPedidoCenarios.processoPedidoCenarios;
//        final QIndiceEconomico qIndiceEconomico = QIndiceEconomico.indiceEconomico;
//        final QTipoPedido qTipoPedido = QTipoPedido.tipoPedido;
//
//
//        return new JPAQueryFactory(entityManager)
//                .select(qProcessoPedido)
//                .from(qProcessoPedido)
//                .join(qProcessoPedido.tipoPedido, qTipoPedido).fetchJoin()
//                .join(qProcessoPedido.processo, qProcesso).fetchJoin()
//                .join(qProcessoPedido.cenario, qProcessoPedidoCenarios).fetchJoin()
//                .join(qProcessoPedidoCenarios.indiceEconomico, qIndiceEconomico).fetchJoin()
//                .where(qProcessoPedido.valor.ne(BigDecimal.ZERO))
//                .fetch( );
//    }
//
//
//    public void updateValorProvisao(Long idPedido, BigDecimal novoValorProvisao) {
//        final QProcessoPedido qProcessoPedido = QProcessoPedido.processoPedido;
//
//        new JPAUpdateClause(entityManager, qProcessoPedido)
//                .set(qProcessoPedido.valorProvisao, novoValorProvisao)
//                .where(qProcessoPedido.id.eq(idPedido))
//                .execute();
//    }
//
//
//    public BigDecimal sumProvisionamentoPedidosByIdProcesso(Long idProcesso) {
//        final QProcessoPedido qPedido = QProcessoPedido.processoPedido;
//        final QProcesso qProcesso = QProcesso.processo1;
//
//        return Optional.ofNullable(new JPAQueryFactory(entityManager)
//                .select(qPedido.valorProvisao.sum())
//                .from(qPedido)
//                .join(qPedido.processo, qProcesso)
//                .where(qProcesso.id.eq(idProcesso))
//                .fetchOne()).orElse(BigDecimal.ZERO);
//    }
//
//
//    public BigDecimal sumValorPedidosByIdProcesso(Long idProcesso) {
//        final QProcessoPedido qPedido = QProcessoPedido.processoPedido;
//        final QProcesso qProcesso = QProcesso.processo1;
//
//        return Optional.ofNullable(new JPAQueryFactory(entityManager)
//                .select(qPedido.valor.sum())
//                .from(qPedido)
//                .join(qPedido.processo, qProcesso)
//                .where(qProcesso.id.eq(idProcesso))
//                .fetchOne()).orElse(BigDecimal.ZERO);
//    }
//}
