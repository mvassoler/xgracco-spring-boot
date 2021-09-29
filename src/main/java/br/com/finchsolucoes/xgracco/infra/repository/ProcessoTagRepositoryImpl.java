package br.com.finchsolucoes.xgracco.infra.repository;


import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.ProcessoTagFilter;
import br.com.finchsolucoes.xgracco.domain.repository.ProcessoTagJpaRepository;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ProcessoTagRepositoryImpl extends AbstractJpaRepository<ProcessoTag, Long> implements ProcessoTagJpaRepository {

    //TODO Descomentar quando criar os services
//    @Autowired
//    private UfService ufService;


    public List<ProcessoTag> find(Query<ProcessoTag> query) {
        JPAQuery<ProcessoTag> jpaQuery = this.createQuery(query);

        this.applyPagination(jpaQuery, query.getPage(), query.getLimit());
        this.applySorter(jpaQuery, query.getSorter());

        return jpaQuery.fetch();
    }


    public long count(Query<ProcessoTag> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<ProcessoTag> createQuery(Query<ProcessoTag> query) {
        QProcessoTag qProcessoTag = QProcessoTag.processoTag;
        QProcesso qProcesso = QProcesso.processo1;
        QTag qTag = QTag.tag;
        QComarca qComarca = QComarca.comarca;
        QCarteira qCarteira = QCarteira.carteira;

        final JPAQuery<ProcessoTag> jpaQuery = new JPAQueryFactory(entityManager)
                .select(
                        QProcessoTag.create(
                                QProcesso.create(
                                        qProcesso.id,
                                        qProcesso.numero
                                ),
                                QTag.create(
                                        qTag.id,
                                        qTag.nome
                                )
                        )
                )
                .from(qProcessoTag)
                .join(qProcessoTag.processo, qProcesso)
                .join(qProcessoTag.tag, qTag)
                .leftJoin(qProcesso.carteira, qCarteira)
                .leftJoin(qProcesso.comarca, qComarca);

        if (Objects.nonNull(query.getFilter()) && query.getFilter() instanceof ProcessoTagFilter) {

            final ProcessoTagFilter filter = (ProcessoTagFilter) query.getFilter();

            if (Objects.nonNull(filter.getIdProcesso())) {
                jpaQuery.where(qProcessoTag.processo.id.eq(filter.getIdProcesso()));
            }

            if (!StringUtils.isBlank(filter.getProcessoUnico())) {
                jpaQuery.where(qProcessoTag.processo.processoUnico.eq(filter.getProcessoUnico()));
            }

            if (Objects.nonNull(filter.getIdTag())) {
                jpaQuery.where(qTag.id.eq(filter.getIdTag()));
            }

            if (!StringUtils.isBlank(filter.getNomeTag())) {
                jpaQuery.where(qTag.nome.eq(filter.getNomeTag()));
            }

        }

        return jpaQuery;
    }


    /**
     * Consulta e retorna as tags(+counts) relacionadas com processo, de acordo com os filtros passados
     * obs: utilizado na tela processopesquisa(editar em lote)
     */
    //TODO Descomentar quando criar os DTOS
//    public Map<Tag, Long> findByProcessoPesquisa(ProcessoConsultaDTO processoConsultaDTO) {
//
//        final QProcessoTag qProcessoTag = QProcessoTag.processoTag;
//        final QTag qTag = QTag.tag;
//        final QProcesso qProcesso = QProcesso.processo1;
//        final QProcesso qDesdobramento = new QProcesso("desdobramento");
//        final QCarteira qCarteira = QCarteira.carteira;
//        final QComarca qComarca = QComarca.comarca;
//        final QPessoaDivisao qPessoaDivisao = QPessoaDivisao.pessoaDivisao;
//        final QVara qVara = QVara.vara;
//        final QMateria qMateria = QMateria.materia;
//        final QEscritorio qEscritorio = QEscritorio.escritorio;
//        final QUsuario qOperacional = QUsuario.usuario;
//        final QPessoa qEscritorioPessoa = new QPessoa("escritorioPessoa");
//        final QPessoa qOperacionalPessoa = new QPessoa("operacionalPessoa");
//        final QPessoa qCliente = new QPessoa("cliente");
//        final QPessoa qParteInteressada = new QPessoa("parteInteressada");
//        final QPessoa qParteContraria = new QPessoa("parteContraria");
//        final QCasoProcesso qCasoProcesso = new QCasoProcesso("casoProcesso");
//        final QCaso qCaso = QCaso.caso;
//        final QProcessoUsuario qProcessoUsuario = QProcessoUsuario.processoUsuario;
//        final QSistemaVirtual qSistemaVirtual = QSistemaVirtual.sistemaVirtual;
//        final QUf qUf = QUf.uf;
//
//
//        JPAQuery<Tuple> query = new JPAQueryFactory(entityManager)
//                .selectDistinct(
//                        qTag.id,
//                        qTag.nome,
//                        qProcessoTag.processo.id.count()
//                )
//                .from(qProcessoTag)
//                .join(qProcessoTag.tag, qTag)
//                .join(qProcessoTag.processo, qProcesso)
//                .leftJoin(qProcesso.cliente, qCliente)
//                .leftJoin(qProcesso.parteInteressada, qParteInteressada)
//                .leftJoin(qProcesso.parteContraria, qParteContraria)
//                .leftJoin(qProcesso.carteira, qCarteira)
//                .leftJoin(qProcesso.comarca, qComarca)
//                .leftJoin(qProcesso.divisao, qPessoaDivisao)
//                .leftJoin(qProcesso.vara, qVara)
//                .leftJoin(qProcesso.materia, qMateria)
//                .leftJoin(qProcesso.escritorio, qEscritorio)
//                .leftJoin(qEscritorio.pessoa, qEscritorioPessoa)
//                .leftJoin(qProcesso.operacional, qOperacional)
//                .leftJoin(qOperacional.pessoa, qOperacionalPessoa)
//                .leftJoin(qProcesso.casoProcessos, qCasoProcesso)
//                .leftJoin(qCasoProcesso.caso, qCaso)
//                .leftJoin(qProcesso.usuariosCompartilhados, qProcessoUsuario)
//                .leftJoin(qProcesso.sistemaVirtual, qSistemaVirtual)
//                .leftJoin(qProcesso.uf, qUf);
//
//
//        if (processoConsultaDTO.getExibeDesdobramento() != null && !processoConsultaDTO.getExibeDesdobramento()) {
//            query.where(qProcesso.processo.isNull());
//        }
//
//        // carteiras
//        if (CollectionUtils.isNotEmpty(processoConsultaDTO.getCarteiras())) {
//            if (processoConsultaDTO.getUsuario() != null) {
//                query.where((qCaso.receberNotificacoes.eq(Boolean.TRUE)).and(qCaso.responsavel.eq(Util.getUsuarioLogado().getUsuarioSistema()))
//                        .or(qCarteira.in(processoConsultaDTO.getCarteiras()))
//                        .or(qEscritorio.in(processoConsultaDTO.getEscritoriosRelacionadosList()))
//                        .or(qProcessoUsuario.usuario.eq(processoConsultaDTO.getUsuario()))
//                );
//            } else {
//                query.where((qCaso.receberNotificacoes.eq(Boolean.TRUE)).and(qCaso.responsavel.eq(Util.getUsuarioLogado().getUsuarioSistema()))
//                        .or(qCarteira.in(processoConsultaDTO.getCarteiras()))
//                        .or(qEscritorio.in(processoConsultaDTO.getEscritoriosRelacionadosList()))
//                );
//            }
//        }
//
//        // carteira
//        if (processoConsultaDTO.getCarteira() != null) {
//            query.where(qCarteira.eq(processoConsultaDTO.getCarteira()));
//        }
//
//        // cliente
//        if (processoConsultaDTO.getCliente() != null) {
//            query.where(qCliente.eq(processoConsultaDTO.getCliente()));
//        }
//
//        // parteInteressada
//        if (processoConsultaDTO.getParteInteressada() != null) {
//            query.where(qParteInteressada.eq(processoConsultaDTO.getParteInteressada()));
//        }
//
//        // parteContraria
//        if (processoConsultaDTO.getParteContraria() != null) {
//            query.where(qParteContraria.eq(processoConsultaDTO.getParteContraria()));
//        }
//
//        // caso
//        if (processoConsultaDTO.getCaso() != null) {
//            query.where(qCaso.eq(processoConsultaDTO.getCaso()));
//        }
//
//        // dataInicial
//        if (processoConsultaDTO.getDataInicial() != null) {
//            query.where(qProcesso.dataCadastro.goe(processoConsultaDTO.getDataInicial()));
//        }
//
//        // dataFinal
//        if (processoConsultaDTO.getDataFinal() != null) {
//            query.where(qProcesso.dataCadastro.loe(processoConsultaDTO.getDataFinal()));
//        }
//
//        /*// recemDistribuido
//        if (processoConsultaDTO.getRecemDistribuido() != null) {
//            if (processoConsultaDTO.getRecemDistribuido()) {
//                query.where(qProcesso.dataDistribuicaoVisualizacao.isNotNull()
//                        .or(qEscritorio.in(processoConsultaDTO.getEscritoriosRelacionadosList()))
//                        .or(qProcessoUsuario.usuario.eq(processoConsultaDTO.getUsuario()))
//
//                );
//            } else {
//                query.where(qProcesso.dataDistribuicaoVisualizacao.isNull()
//                        .or(qEscritorio.in(processoConsultaDTO.getEscritoriosRelacionadosList()))
//                        .or(qProcessoUsuario.usuario.eq(processoConsultaDTO.getUsuario()))
//                );
//            }
//
//        }*/
//
//        // valorCausaInicial
//        if (processoConsultaDTO.getValorCausaInicial() != null) {
//            query.where(qProcesso.valorCausa.goe(processoConsultaDTO.getValorCausaInicial()));
//        }
//
//        // valorCausaFinal
//        if (processoConsultaDTO.getValorCausaFinal() != null) {
//            query.where(qProcesso.valorCausa.loe(processoConsultaDTO.getValorCausaFinal()));
//        }
//
//        // semEscritorio
//        if (processoConsultaDTO.getSemEscritorio() != null) {
//            if (processoConsultaDTO.getSemEscritorio()) {
//                query.where(qEscritorio.isNull());
//            } else {
//                query.where(qEscritorio.isNotNull());
//            }
//        }
//
//        // escritorio
//        if (processoConsultaDTO.getEscritorios() != null) {
//            query.where(
//                    ((qCaso.receberNotificacoes.eq(Boolean.TRUE)).and(qCaso.responsavel.eq(Util.getUsuarioLogado().getUsuarioSistema())))
//                            .or(qEscritorio.in(processoConsultaDTO.getEscritorios()).or(qEscritorio.in(processoConsultaDTO.getEscritoriosRelacionadosList())))
//                            .or(qProcessoUsuario.usuario.eq(processoConsultaDTO.getUsuario()))
//            );
//        }
//
//        // operacional
//        if (processoConsultaDTO.getOperacional() != null) {
//            query.where(
//                    ((qCaso.receberNotificacoes.eq(Boolean.TRUE)).and(qCaso.responsavel.eq(Util.getUsuarioLogado().getUsuarioSistema())))
//                            .or(qProcesso.operacional.eq(processoConsultaDTO.getOperacional()).or(qEscritorio.in(processoConsultaDTO.getEscritoriosRelacionadosList())))
//                            .or(qProcessoUsuario.usuario.eq(processoConsultaDTO.getUsuario()))
//            );
//        }
//
//        // status
//        if (CollectionUtils.isNotEmpty(processoConsultaDTO.getStatus())) {
//            query.where(qProcesso.status.in(processoConsultaDTO.getStatus()));
//        }
//
//        // uf
//        if (CollectionUtils.isNotEmpty(processoConsultaDTO.getUfs())) {
//            query.where(qUf.in(processoConsultaDTO.getUfs()));
//        }
//
//        if (processoConsultaDTO.getTipoProcesso() != null) {
//            query.where(qProcesso.tipoProcesso.eq(processoConsultaDTO.getTipoProcesso()));
//        }
//
//        if (processoConsultaDTO.getSistemaVirtual() != null) {
//            query.where(qProcesso.sistemaVirtual.eq(processoConsultaDTO.getSistemaVirtual()));
//        }
//
//        // filterColumn / filterValue
//        if (processoConsultaDTO.getFilterColumn() != null && StringUtils.isNotBlank(processoConsultaDTO.getFilterValue())) {
//            final String filter = "%" + processoConsultaDTO.getFilterValue() + "%";
//            switch (processoConsultaDTO.getFilterColumn()) {
//                case OPERACIONAL:
//                    query.where(qOperacionalPessoa.nomeRazaoSocial.likeIgnoreCase(filter));
//                    break;
//
//                case COMARCA:
//                    query.where(qComarca.descricao.likeIgnoreCase(filter));
//                    break;
//
//                case DESDOBRAMENTO:
//                    query.where(qProcesso.numero.likeIgnoreCase(filter));
//                    break;
//
//                case DIVISAO_CLIENTE:
//                    query.where(qPessoaDivisao.descricao.likeIgnoreCase(filter));
//                    break;
//
//                case ESTADO:
//                    Optional<Uf> ufFilter = ufService.findBySigla(processoConsultaDTO.getFilterValue());
//                    if (ufFilter.isPresent()) {
//                        query.where(qProcesso.uf.eq(ufFilter.get()));
//                    }
//                    break;
//
//                case PROCESSO_UNICO:
//                    query.where(qProcesso.processoUnico.likeIgnoreCase(filter));
//                    break;
//
//                case CONTROLE_CLIENTE:
//                    query.where(qProcesso.controleCliente.likeIgnoreCase(filter));
//                    break;
//
//                case ORDEM:
//                    query.where(qProcesso.numeroOrdem.likeIgnoreCase(filter));
//                    break;
//
//                case PARTE:
//                    final QProcessoParte qProcessoParte = QProcessoParte.processoParte;
//                    final QPessoa qPessoaParte = new QPessoa("pessoaParte");
//
//                    query.where(qParteContraria.nomeRazaoSocial.likeIgnoreCase(filter)
//                            .or(qParteInteressada.nomeRazaoSocial.likeIgnoreCase(filter)
//                                    .or(JPAExpressions
//                                            .select(qProcessoParte.count())
//                                            .from(qProcessoParte)
//                                            .join(qProcessoParte.pessoa, qPessoaParte)
//                                            .where(qProcessoParte.processo.eq(qProcesso))
//                                            .where(qPessoaParte.nomeRazaoSocial.likeIgnoreCase(filter))
//                                            .gt(0L))));
//                    break;
//
//                case PARTE_CONTRARIA:
//                    query.where(qParteContraria.nomeRazaoSocial.likeIgnoreCase(filter));
//                    break;
//
//                case PARTE_INTERESSADA:
//                    query.where(qParteInteressada.nomeRazaoSocial.likeIgnoreCase(filter));
//                    break;
//
//                case PASTA:
//                    query.where(qProcesso.pasta.likeIgnoreCase(filter));
//                    break;
//
//                case PASTA_DESDOBRAMENTO:
//                    query.where(qProcesso.pasta.likeIgnoreCase(filter));
//                    break;
//
//                case PROCESSO_NUMERO:
//                    query.where(Expressions.stringTemplate("REPLACE(REPLACE(REPLACE({0}, {1}, ''), {2}, ''), {3}, '')", qProcesso.numero, ".", "-", "/")
//                            .likeIgnoreCase(Expressions.stringTemplate("REPLACE(REPLACE(REPLACE({0}, {1}, ''), {2}, ''), {3}, '')", filter, ".", "-", "/"))
//
//                            .or(Expressions.stringTemplate("REPLACE(REPLACE(REPLACE({0}, {1}, ''), {2}, ''), {3}, '')", qProcesso.numeroAntigo, ".", "-", "/")
//                                    .likeIgnoreCase(Expressions.stringTemplate("REPLACE(REPLACE(REPLACE({0}, {1}, ''), {2}, ''), {3}, '')", filter, ".", "-", "/")))
//
//                            .or(Expressions.stringTemplate("REPLACE(REPLACE(REPLACE({0}, {1}, ''), {2}, ''), {3}, '')", qProcesso.pasta, ".", "-", "/")
//                                    .likeIgnoreCase(Expressions.stringTemplate("REPLACE(REPLACE(REPLACE({0}, {1}, ''), {2}, ''), {3}, '')", filter, ".", "-", "/")))
//
//                            .or(JPAExpressions.select(qDesdobramento.count())
//                                    .from(qDesdobramento)
//                                    .where(qDesdobramento.processo.eq(qProcesso))
//                                    .where(Expressions.stringTemplate("REPLACE(REPLACE(REPLACE({0}, {1}, ''), {2}, ''), {3}, '')", qDesdobramento.numero, ".", "-", "/")
//                                            .likeIgnoreCase(Expressions.stringTemplate("REPLACE(REPLACE(REPLACE({0}, {1}, ''), {2}, ''), {3}, '')", filter, ".", "-", "/")))
//                                    .gt(0L))
//                    );
//                    break;
//
//                case PROCESSO_NUMERO_SECUNDARIO:
//                    query.where(qProcesso.numeroAntigo.likeIgnoreCase(filter));
//                    break;
//
//                case VARA:
//                    query.where(qVara.descricao.likeIgnoreCase(filter));
//                    break;
//
//                case TAG:
//                    QTag qTag2 = QTag.tag;
//                    QProcessoTag qProcessoTag1 = QProcessoTag.processoTag;
//                    query.where(JPAExpressions
//                            .select(qTag2.count())
//                            .from(qProcessoTag1)
//                            .join(qProcessoTag1.tag, qTag)
//                            .where(qProcessoTag1.processo.eq(qProcesso))
//                            .where(qTag2.nome.likeIgnoreCase(filter)).gt(0L));
//                    break;
//
//                case ESCRITORIO:
//                    query.where(qEscritorioPessoa.nomeRazaoSocial.likeIgnoreCase(filter));
//                    break;
//
//                case MATERIA:
//                    query.where(qMateria.descricao.likeIgnoreCase(filter));
//                    break;
//                case CASO:
//                    query.where(qCaso.descricao.likeIgnoreCase(filter).or(qCaso.identificador.likeIgnoreCase(filter)));
//                    break;
//            }
//        }
//
//        if (processoConsultaDTO.getProcessoSemNumero()) {
//            query.where(qProcesso.processoSemNumero.eq(true));
//        }
//
//        return query.groupBy(qTag.id, qTag.nome)
//                .fetch()
//                .stream()
//                .collect(Collectors.toMap(
//                        tuple -> new Tag(tuple.get(0, Long.class), tuple.get(1, String.class)), tuple -> tuple.get(2, Long.class)));
//
//    }

    @Override
    public Optional<ProcessoTag> findOnlyProcessoTagByProcessoAndTag(Processo processo, Tag tag) {
        final QProcessoTag qProcessoTag = QProcessoTag.processoTag;
        final QProcesso qProcesso = QProcesso.processo1;
        final QTag qTag = QTag.tag;

        JPAQuery<ProcessoTag> jpaQuery = new JPAQueryFactory(this.entityManager)
                .select(QProcessoTag.create(
                        QProcesso.create(
                                qProcesso.id,
                                qProcesso.numero
                        ),
                        QTag.create(
                                qTag.id,
                                qTag.nome
                        )
                ))
                .from(qProcessoTag)
                .join(qProcessoTag.processo, qProcesso)
                .join(qProcessoTag.tag, qTag)
                .where(qProcessoTag.processo.eq(processo))
                .where(qProcessoTag.tag.eq(tag));

        return Optional.ofNullable(jpaQuery.fetchOne());
    }

    @Override
    public void insertProcessoTag(Long idProcesso, Long idTag) {
        javax.persistence.Query query = entityManager.createNativeQuery("insert into PROCESSO_TAG (FK_PROCESSO, FK_TAG) values (:idProcesso, :idTag)");
        query.setParameter("idProcesso", idProcesso);
        query.setParameter("idTag", idTag);
        query.executeUpdate();
    }


    @Override
    public List<ProcessoTag> findByProcesso(Processo processo) {
        final QProcessoTag qProcessoTag = QProcessoTag.processoTag;
        final QProcesso qProcesso = QProcesso.processo1;
        final QTag qTag = QTag.tag;

        JPAQuery<ProcessoTag> jpaQuery = new JPAQueryFactory(this.entityManager)
                .select(QProcessoTag.create(
                        QProcesso.create(
                                qProcesso.id,
                                qProcesso.numero
                        ),
                        QTag.create(
                                qTag.id,
                                qTag.nome
                        )
                ))
                .from(qProcessoTag)
                .join(qProcessoTag.processo, qProcesso)
                .join(qProcessoTag.tag, qTag)
                .where(qProcessoTag.processo.in(processo));


        return jpaQuery.fetch();
    }

}