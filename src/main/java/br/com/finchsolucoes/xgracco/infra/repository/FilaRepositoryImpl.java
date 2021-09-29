package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.Sorter;
import br.com.finchsolucoes.xgracco.domain.query.impl.FilaFilter;
import br.com.finchsolucoes.xgracco.domain.repository.FilaJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade Fila
 *
 * @author Felipi Berdun
 * @since 2.1
 */
@Repository
public class FilaRepositoryImpl extends AbstractJpaRepository<Fila, Long> implements FilaJpaRepository {

    @Override
    public List<Fila> find(Query<Fila> query) {
        final JPAQuery<Fila> jpaQuery = createQuery(query);
        final PathBuilder<Fila> builder = new PathBuilder<>(Fila.class, "fila");

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

    @Override
    public long count(Query<Fila> query) {
        return createQuery(query).fetchCount();
    }

    private JPAQuery<Fila> createQuery(Query<Fila> query) {
        final QEsteira qEsteira = QEsteira.esteira;
        final QFila qFila = QFila.fila;
        final QTag qTag = QTag.tag;
        final QFilaEspera qFilaEspera = QFilaEspera.filaEspera;

        final JPAQuery<Fila> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qFila)
                .from(qFila);

        if (query.getFilter() != null && query.getFilter() instanceof FilaFilter) {
            FilaFilter filter = (FilaFilter) query.getFilter();

            if (filter.getEsteira() != null) {
                jpaQuery.join(qFila.esteira, qEsteira);
                jpaQuery.where(qEsteira.eq(filter.getEsteira()));
            }

            if (StringUtils.isNotBlank(filter.getDescricao())) {
                jpaQuery.where(qFila.descricao.likeIgnoreCase("%" + filter.getDescricao() + "%"));
            }

            if (filter.getAtivo() != null) {

                if(filter.getAtivo()){
                    jpaQuery.where(
                            JPAExpressions
                                    .select(qFilaEspera)
                                    .from(qFilaEspera)
                                    .where(qFilaEspera.filaAtivaEspera.eq(qFila))
                                    .notExists().and(qFila.ativo.eq(filter.getAtivo())));
                }else{
                    jpaQuery.where(
                            JPAExpressions
                                    .select(qFilaEspera)
                                    .from(qFilaEspera)
                                    .where(qFilaEspera.filaAtivaEspera.eq(qFila))
                                    .exists().or(qFila.ativo.eq(filter.getAtivo())));
                }
            }

            if (StringUtils.isNotBlank(filter.getTag())) {
                jpaQuery.join(qFila.tags, qTag);
                jpaQuery.where(qTag.nome.likeIgnoreCase("%" + filter.getTag() + "%"));
            }
        }

        return jpaQuery;
    }

    @Override
    public Fila findDevolucaoByEsteira(Esteira esteira) {
        final QFila qFila = QFila.fila;
        final QEsteira qEsteira = QEsteira.esteira;

        return new JPAQueryFactory(entityManager)
                .select(qFila)
                .from(qFila)
                .join(qFila.esteira, qEsteira)
                .where(qEsteira.eq(esteira))
                .where(qFila.filaDevolucao.eq(true))
                .fetchOne();
    }

    @Override
    public List<Fila> findFilaByEsteira(Esteira esteira) {
        final QFila qFila = QFila.fila;
        final QEsteira qEsteira = QEsteira.esteira;

        return new JPAQueryFactory(entityManager)
                .select(qFila)
                .from(qFila)
                .join(qFila.esteira, qEsteira).fetchJoin()
                .where(qEsteira.eq(esteira))
                .fetch();
    }

    @Override
    public List<Fila> findFilaByTarefaAndProcesso(FluxoTrabalhoTarefa fluxoTrabalhoTarefa, Processo processo) {
        final QFila qFila = QFila.fila;
        final QFilaTarefa qFilaTarefa = QFilaTarefa.filaTarefa;
        final QCarteira qCarteira = QCarteira.carteira;
        final QFilaTag qFilaTag = QFilaTag.filaTag;
        final QProcessoTag qProcessoTag = QProcessoTag.processoTag;
        final QProcesso qProcesso = QProcesso.processo1;
        final QUsuario qUsuarioOperacional = QUsuario.usuario;
        final QFilaEspera qFilaEspera = QFilaEspera.filaEspera;

        return new JPAQueryFactory(entityManager)
                .selectDistinct(qFila)
                .from(qFila)
                .join(qFila.tarefas, qFilaTarefa)
                .join(qFilaTarefa.carteira, qCarteira)
                .innerJoin(qProcesso).on(qCarteira.eq(qProcesso.carteira))
                .leftJoin(qFilaTag).on(qFila.tags.any().eq(qFilaTag.tag))
                .leftJoin(qProcesso.operacional, qUsuarioOperacional)
                .where(qProcesso.eq(processo))
                .where(qFilaTarefa.fluxoTrabalhoTarefa.eq(fluxoTrabalhoTarefa))

                //FILTRO DO OPERACIONAL
                .where(
                        (qFila.operacional.eq(qUsuarioOperacional.pessoa))
                                .or(qFila.operacional.isNull())
                )

                //FILTRO DAS TAGS
                .where((qFila.processosSemTag.eq(Boolean.FALSE)
                                .and(
                                        qFilaTag.tag.id.in(
                                                JPAExpressions
                                                        .select(qProcessoTag.tag.id)
                                                        .from(qProcessoTag)
                                                        .where(qProcessoTag.processo.eq(qProcesso))
                                                        .fetchAll()
                                        )
                                                .or(
                                                        qFilaTag.tag.isNull()
                                                )
                                )
                        )
                                .or(
                                        qFila.processosSemTag.eq(Boolean.TRUE)
                                                .and(
                                                        JPAExpressions
                                                                .select(qProcessoTag.tag)
                                                                .from(qProcessoTag)
                                                                .where(qProcessoTag.processo.eq(qProcesso))
                                                                .notExists()
                                                )
                                )
                )
                .fetch();
    }

    @Override
    public List<Fila> findByTarefa(FluxoTrabalhoTarefa fluxoTrabalhoTarefa) {
        final QFila qFila = QFila.fila;
        final QFilaTarefa qFilaTarefa = QFilaTarefa.filaTarefa;
        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;

        return new JPAQueryFactory(entityManager)
                .select(qFila)
                .from(qFila)
                .join(qFila.tarefas, qFilaTarefa)
                .join(qFilaTarefa.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefa)
                .where(qFluxoTrabalhoTarefa.eq(fluxoTrabalhoTarefa))
                .fetch();
    }

    @Override
    public List<Fila> findFilaAndEsteira(Long idFila, String descricaoEsteira, String descricaoFila, Long idCarteira, Boolean ativo, String descricaoTag, Boolean todasAsFilas, Long idUsuario, boolean somenteFilaAtendimentos) {
        final QFila qFila = QFila.fila;
        final QEsteira qEsteira = QEsteira.esteira;
        final QPessoa qPessoa = QPessoa.pessoa;
        final QTag qTag = QTag.tag;
        final QCarteira qCarteira = QCarteira.carteira;
        final QCarteira qCarteira1 = new QCarteira("carteiraFila");
        final QFilaTarefa qFilaTarefa = QFilaTarefa.filaTarefa;
        final QPessoa qPessoa1 = new QPessoa("pessoaCarteiras");
        final QEsteiraTarefa qEsteiraTarefa = QEsteiraTarefa.esteiraTarefa;
        final QFilaEspera qFilaEspera = QFilaEspera.filaEspera;

        JPAQuery<Fila> jpaQuery = new JPAQueryFactory(entityManager)
                .selectDistinct(QFila.create(qFila.id,
                        qFila.descricao,
                        QEsteira.create(
                                qEsteira.id,
                                qEsteira.descricao
                        ),
                        qFila.filaDevolucao,
                        qFila.ativo,
                        QPessoa.create(qPessoa.id),
                        qFila.processosSemTag,
                        qFila.visualizarTarefasVencidas,
                        qFila.tempoVisao,
                        qFila.tempoVisaoVencidas,
                        qFila.dataRecebimentoInicial,
                        qFila.dataRecebimentoFinal,
                        qFila.expressao))
                .from(qFila)
                .join(qFila.esteira, qEsteira)
                .leftJoin(qFila.operacional, qPessoa)
                .join(qEsteira.tarefas, qEsteiraTarefa)
                .join(qEsteiraTarefa.carteira, qCarteira);


        if (Objects.nonNull(idFila)) {
            jpaQuery.where(qFila.id.eq(idFila));
        }

        if (StringUtils.isNotEmpty(descricaoEsteira)) {
            jpaQuery.where(qEsteira.descricao.likeIgnoreCase("%" + descricaoEsteira + "%"));
        }

        if (StringUtils.isNotBlank(descricaoFila)) {
            jpaQuery.where(qFila.descricao.likeIgnoreCase("%" + descricaoFila + "%"));
        }

        if (StringUtils.isNotBlank(descricaoTag)) {
            jpaQuery.join(qFila.tags, qTag);
            jpaQuery.where(qTag.nome.likeIgnoreCase("%" + descricaoTag + "%"));
        }

        if (Objects.nonNull(idCarteira)) {
            jpaQuery.join(qFila.tarefas, qFilaTarefa);
            jpaQuery.join(qFilaTarefa.carteira, qCarteira1);
            jpaQuery.where(qCarteira1.id.eq(idCarteira));
        }

        if (Objects.nonNull(todasAsFilas) && !todasAsFilas) {
            jpaQuery.where(qFila.criadorFila.id.eq(idUsuario));
        }

        if (ativo != null) {

            if(ativo){
                jpaQuery.where(
                        JPAExpressions
                                .select(qFilaEspera)
                                .from(qFilaEspera)
                                .where(qFilaEspera.filaAtivaEspera.eq(qFila))
                                .notExists().and(qFila.ativo.eq(ativo)));
            }else{
                jpaQuery.where(
                        JPAExpressions
                                .select(qFilaEspera)
                                .from(qFilaEspera)
                                .where(qFilaEspera.filaAtivaEspera.eq(qFila))
                                .exists().or(qFila.ativo.eq(ativo)));
            }
        }

        if (somenteFilaAtendimentos) {
            jpaQuery.where(qFila.filaDevolucao.eq(false));
        }

        // CONSULTA APENAS AS FILAS AS QUAIS ESTAO RELACIONADAS COM A CARTEIRA QUE O USUARIO LOGADO
        // POSSUI RELACIONAMENTO
        jpaQuery.join(qCarteira.pessoas, qPessoa1)
                .where(qPessoa1.id.eq(idUsuario));

        //CONSULTA APENAS AS FILAS QUE O USUÁRIO É O CRIADOR
        jpaQuery.orderBy(qEsteira.descricao.asc())
                .orderBy(qFila.id.asc());

        return jpaQuery.fetch();
    }

    @Override
    public List<Fila> findByFluxoTrabalhoTarefaCarteira(FluxoTrabalhoTarefa fluxoTrabalhoTarefa, Carteira carteira) {
        final QFila qFila = QFila.fila;
        final QEsteira qEsteira = QEsteira.esteira;
        final QFilaTarefa qFilaTarefa = QFilaTarefa.filaTarefa;
        final QCarteira qCarteira = QCarteira.carteira;

        return new JPAQueryFactory(entityManager)
                .select(QFila.create(qFila.id,
                        qFila.descricao,
                        QEsteira.create(
                                qEsteira.id,
                                qEsteira.descricao
                        ))
                )
                .from(qFila)
                .join(qFila.esteira, qEsteira)
                .join(qFila.tarefas, qFilaTarefa)
                .join(qFilaTarefa.carteira, qCarteira)
                .where(qCarteira.eq(carteira))
                .where(qFilaTarefa.fluxoTrabalhoTarefa.eq(fluxoTrabalhoTarefa))
                .fetch();
    }
}
