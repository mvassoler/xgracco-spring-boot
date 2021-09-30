package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.query.impl.FluxoTrabalhoTarefaFilter;
import br.com.finchsolucoes.xgracco.domain.repository.FluxoTrabalhoTarefaJpaRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class FluxoTrabalhoTarefaRepositoryImpl implements FluxoTrabalhoTarefaJpaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private JPAQuery<FluxoTrabalhoTarefa> createQuery(FluxoTrabalho fluxoTrabalho, Query<FluxoTrabalhoTarefa> query) {
        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;
        final QTarefa qTarefa = QTarefa.tarefa;

        JPAQuery<FluxoTrabalhoTarefa> jpaQuery = new JPAQueryFactory(entityManager)
                .select(QFluxoTrabalhoTarefa.create(
                        qFluxoTrabalhoTarefa.id,
                        QTarefa.create(
                                qTarefa.id,
                                qTarefa.nome)
                        )
                )
                .from(qFluxoTrabalhoTarefa)
                .join(qFluxoTrabalhoTarefa.tarefa, qTarefa)
                .where(qFluxoTrabalhoTarefa.fluxoTrabalho.eq(fluxoTrabalho));

        if (query.getFilter() != null) {
            final FluxoTrabalhoTarefaFilter filter = (FluxoTrabalhoTarefaFilter) query.getFilter();

            if (filter.getNome() != null) {
                jpaQuery.where(qTarefa.nome.likeIgnoreCase("%" + filter.getNome() + "%"));
            }

            if (filter.getAtivo() != null) {
                jpaQuery.where(qFluxoTrabalhoTarefa.ativo.eq(filter.getAtivo()));
            }
        }

        return jpaQuery;
    }

    @Override
    public long count(FluxoTrabalho fluxoTrabalho, Query<FluxoTrabalhoTarefa> query) {
        return createQuery(fluxoTrabalho, query).fetchCount();
    }

    @Override
    public Optional<FluxoTrabalhoTarefa> findTarefaPublicacaoByFluxo(FluxoTrabalho fluxoTrabalho) {

        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;
        final QTarefa qTarefa = QTarefa.tarefa;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(qFluxoTrabalhoTarefa)
                .from(qFluxoTrabalhoTarefa)
                .join(qFluxoTrabalhoTarefa.tarefa, qTarefa)
                .where(qFluxoTrabalhoTarefa.ativo.eq(Boolean.TRUE))
                .where(qFluxoTrabalhoTarefa.fluxoTrabalho.eq(fluxoTrabalho))
                .where(qTarefa.nome.likeIgnoreCase("publicação"))
                .fetchOne());
    }

    @Override
    public Optional<FluxoTrabalhoTarefa> findById(final Long id) {

        final QFluxoTrabalho qFluxoTrabalho = QFluxoTrabalho.fluxoTrabalho;
        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;
        final QTarefa qTarefa = QTarefa.tarefa;
        final QUsuario qUsuario = QUsuario.usuario;
        final QPessoa qPessoa = QPessoa.pessoa;

        return Optional.ofNullable(
                new JPAQueryFactory(entityManager)
                        .select(qFluxoTrabalhoTarefa)
                        .from(qFluxoTrabalhoTarefa)
                        .join(qFluxoTrabalhoTarefa.tarefa, qTarefa).fetchJoin()
                        .join(qFluxoTrabalhoTarefa.fluxoTrabalho, qFluxoTrabalho).fetchJoin()
                        .leftJoin(qFluxoTrabalhoTarefa.usuario, qUsuario).fetchJoin()
                        .leftJoin(qUsuario.pessoa, qPessoa)
                        .where(qFluxoTrabalhoTarefa.id.eq(id))
                        .fetchOne()
        );
    }

    @Override
    public List<FluxoTrabalhoTarefa> find(FluxoTrabalho fluxoTrabalho, Query<FluxoTrabalhoTarefa> query) {

        final PathBuilder<FluxoTrabalhoTarefa> entityPath = new PathBuilder<>(FluxoTrabalhoTarefa.class, "fluxoTrabalhoTarefa");
        final JPAQuery<FluxoTrabalhoTarefa> jpaQuery = createQuery(fluxoTrabalho, query);

        if (Optional.ofNullable(query.getPage()).orElse(0L) > 0L) {
            jpaQuery.offset((query.getPage() - 1L) * query.getLimit());
        }

        if (query.getLimit() != Query.NO_LIMIT) {
            jpaQuery.limit(query.getLimit());
        }

        jpaQuery.orderBy(entityPath.getString("tarefa.nome").asc());

        return jpaQuery.fetch();
    }

    @Override
    public void create(FluxoTrabalhoTarefa fluxoTrabalhoTarefa) {
        entityManager.persist(fluxoTrabalhoTarefa);
        entityManager.flush();
    }

    @Override
    public void remove(FluxoTrabalhoTarefa fluxoTrabalhoTarefa) {
        this.updateAtivo(fluxoTrabalhoTarefa, false);
    }

    @Override
    public Optional<FluxoTrabalhoTarefa> findByFluxoTrabalhoAndTarefaFetchStatusFinais(FluxoTrabalho fluxoTrabalho, Long idFluxoTrabalhoTarefa) {
        final QTarefa qTarefa = QTarefa.tarefa;
        final QFluxoTrabalho qFluxoTrabalho = QFluxoTrabalho.fluxoTrabalho;
        final QTarefaStatusFinal qTarefaStatusFinal = QTarefaStatusFinal.tarefaStatusFinal;
        final QStatusFinal qStatusFinal = QStatusFinal.statusFinal;
        final QFormulario qFormularioPadrao = new QFormulario("formularioPadrao");
        final QFormulario qFormulario = QFormulario.formulario;
        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;

        final List<TarefaStatusFinal> tarefaStatusFinals = new JPAQueryFactory(entityManager)
                .select(QTarefaStatusFinal.create(
                        qTarefaStatusFinal.id,
                        QFluxoTrabalhoTarefa.create(
                                qFluxoTrabalhoTarefa.id,
                                QTarefa.create(
                                        qTarefa.id,
                                        qTarefa.idTarefa,
                                        qTarefa.nome,
                                        qTarefa.descricao
                                )
                        ),
                        QStatusFinal.create(
                                qStatusFinal.id,
                                qStatusFinal.descricao,
                                QFormulario.create(
                                        qFormularioPadrao.id,
                                        qFormularioPadrao.nome
                                )
                        ),
                        qTarefaStatusFinal.status,
                        QFormulario.create(
                                qFormulario.id,
                                qFormulario.nome
                        )
                ))
                .from(qFluxoTrabalhoTarefa)
                .join(qFluxoTrabalhoTarefa.fluxoTrabalho, qFluxoTrabalho)
                .join(qFluxoTrabalhoTarefa.tarefa, qTarefa)
                .leftJoin(qFluxoTrabalhoTarefa.statusFinais, qTarefaStatusFinal)
                .leftJoin(qTarefaStatusFinal.statusFinal, qStatusFinal)
                .leftJoin(qStatusFinal.formulario, qFormularioPadrao)
                .leftJoin(qTarefaStatusFinal.formulario, qFormulario)
                .where(qFluxoTrabalho.id.eq(fluxoTrabalho.getId()))
                .where(qFluxoTrabalhoTarefa.id.eq(idFluxoTrabalhoTarefa))
                .fetch();

        if (tarefaStatusFinals.isEmpty()) {
            return Optional.empty();
        }

        /**
         * Agrupando resultado
         */
        final Map<Optional<FluxoTrabalhoTarefa>, List<TarefaStatusFinal>> mapTarefa = tarefaStatusFinals
                .stream()
                .collect(Collectors.groupingBy(tarefaStatusFinal -> Optional.<TarefaStatusFinal>ofNullable(tarefaStatusFinal)
                        .map(TarefaStatusFinal::getFluxoTrabalhoTarefa)
                        .map(FluxoTrabalhoTarefa::getId)
                        .isPresent() ? Optional.<FluxoTrabalhoTarefa>ofNullable(tarefaStatusFinal.getFluxoTrabalhoTarefa()) : Optional.empty()));

        mapTarefa.forEach((fluxoTrabalhoTarefa, statusFinais) -> {
            statusFinais.forEach(tarefaStatusFinal -> {
                tarefaStatusFinal.setFluxoTrabalhoTarefa(null);

                if (!Optional.ofNullable(tarefaStatusFinal).map(TarefaStatusFinal::getStatusFinal).map(StatusFinal::getId).isPresent()) {
                    tarefaStatusFinal.setStatusFinal(null);
                }
                if (!Optional.ofNullable(tarefaStatusFinal).map(TarefaStatusFinal::getFormulario).map(Formulario::getId).isPresent()) {
                    tarefaStatusFinal.setFormulario(null);
                }
            });

            fluxoTrabalhoTarefa.ifPresent(t -> t.setStatusFinais(statusFinais.stream().filter(m -> m.getId() != null).collect(Collectors.toList())));
        });

        /**
         * Retornando a primeira (e única) tarefa do map
         */
        return mapTarefa.keySet().iterator().next();
    }

    @Override
    public Optional<FluxoTrabalhoTarefa> findByFluxoTrabalhoAndIdFetchStatusFinaisAgendamentos(final FluxoTrabalho fluxoTrabalho, final Long id) {
        final QTarefa qTarefa = QTarefa.tarefa;
        final QFluxoTrabalho qFluxoTrabalho = QFluxoTrabalho.fluxoTrabalho;
        final QTarefaStatusFinal qTarefaStatusFinal = QTarefaStatusFinal.tarefaStatusFinal;
        final QStatusFinal qStatusFinal = QStatusFinal.statusFinal;
        final QFormulario qFormularioPadrao = new QFormulario("formularioPadrao");
        final QFormulario qFormulario = QFormulario.formulario;
        final QTarefaStatusFinalAgendamento qTarefaStatusFinalAgendamento = QTarefaStatusFinalAgendamento.tarefaStatusFinalAgendamento;
        final QTarefa qTarefaAgendamento = new QTarefa("tarefaAgendamento");
        final QFluxoTrabalho qFluxoTrabalhoAgendamento = new QFluxoTrabalho("fluxoTrabalhoAgendamento");
        final QCampo qCampoData = new QCampo("campoData");
        final QCampo qCampoResponsavel = new QCampo("campoResponsavel");
        final QUsuario qUsuarioResponsavel = new QUsuario("usuarioResponsavel");
        final QPessoa qPessoaResponsavel = new QPessoa("pessoaResponsavel");
        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;
        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefaAgendamento = new QFluxoTrabalhoTarefa("fttAgendamento");

        final List<TarefaStatusFinalAgendamento> agendamentos = new JPAQueryFactory(entityManager)
                .select(QTarefaStatusFinalAgendamento.create(
                        qTarefaStatusFinalAgendamento.id,
                        QTarefaStatusFinal.create(
                                qTarefaStatusFinal.id,
                                QFluxoTrabalhoTarefa.create(
                                        qFluxoTrabalhoTarefa.id,
                                        QFluxoTrabalho.create(
                                                qFluxoTrabalho.id,
                                                qFluxoTrabalho.descricao
                                        ),
                                        QTarefa.create(
                                                qTarefa.id,
                                                qTarefa.idTarefa,
                                                qTarefa.nome,
                                                qTarefa.descricao
                                        )
                                ),
                                QStatusFinal.create(
                                        qStatusFinal.id,
                                        qStatusFinal.descricao,
                                        QFormulario.create(
                                                qFormularioPadrao.id,
                                                qFormularioPadrao.nome
                                        )
                                ),
                                qTarefaStatusFinal.status,
                                QFormulario.create(
                                        qFormulario.id,
                                        qFormulario.nome
                                )
                        ),
                        QFluxoTrabalhoTarefa.create(
                                qFluxoTrabalhoTarefaAgendamento.id,
                                QTarefa.create(
                                        qTarefaAgendamento.id,
                                        qTarefaAgendamento.idTarefa,
                                        qTarefaAgendamento.nome,
                                        qTarefaAgendamento.descricao
                                )
                        ),
                        qTarefaStatusFinalAgendamento.tipoDataAgendamento,
                        qTarefaStatusFinalAgendamento.tipoResponsavelAgendamento,
                        qTarefaStatusFinalAgendamento.tipoIntervaloAgendamento,
                        qTarefaStatusFinalAgendamento.intervalo,
                        qTarefaStatusFinalAgendamento.diasUteis,
                        QCampo.create(
                                qCampoData.id,
                                qCampoData.descricao
                        ),
                        QCampo.create(
                                qCampoResponsavel.id,
                                qCampoResponsavel.descricao
                        ),
                        QUsuario.create(
                                qUsuarioResponsavel.id,
                                qUsuarioResponsavel.login,
                                QPessoa.create(
                                        qPessoaResponsavel.id,
                                        qPessoaResponsavel.nomeRazaoSocial
                                )
                        )
                ))
                .from(qFluxoTrabalhoTarefa)
                .join(qFluxoTrabalhoTarefa.tarefa, qTarefa)
                .join(qFluxoTrabalhoTarefa.fluxoTrabalho, qFluxoTrabalho)
                .leftJoin(qFluxoTrabalhoTarefa.statusFinais, qTarefaStatusFinal)
                .leftJoin(qTarefaStatusFinal.statusFinal, qStatusFinal)
                .leftJoin(qStatusFinal.formulario, qFormularioPadrao)
                .leftJoin(qTarefaStatusFinal.formulario, qFormulario)
                .leftJoin(qTarefaStatusFinal.agendamentos, qTarefaStatusFinalAgendamento)
                .leftJoin(qTarefaStatusFinalAgendamento.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefaAgendamento)
                .leftJoin(qFluxoTrabalhoTarefaAgendamento.tarefa, qTarefaAgendamento)
                .leftJoin(qFluxoTrabalhoTarefaAgendamento.fluxoTrabalho, qFluxoTrabalhoAgendamento)
                .leftJoin(qTarefaStatusFinalAgendamento.campoData, qCampoData)
                .leftJoin(qTarefaStatusFinalAgendamento.campoResponsavel, qCampoResponsavel)
                .leftJoin(qTarefaStatusFinalAgendamento.responsavel, qUsuarioResponsavel)
                .leftJoin(qUsuarioResponsavel.pessoa, qPessoaResponsavel)
                .where(qFluxoTrabalhoTarefa.id.eq(id))
                .fetch();

        if (agendamentos.isEmpty()) {
            return Optional.empty();
        }

        /**
         * Agrupando resultado
         */
        final Map<Optional<FluxoTrabalhoTarefa>, Map<Optional<TarefaStatusFinal>, List<TarefaStatusFinalAgendamento>>> mapTarefa = agendamentos
                .stream()
                .collect(Collectors.groupingBy(agendamento -> Optional.<TarefaStatusFinalAgendamento>ofNullable(agendamento)
                                .map(TarefaStatusFinalAgendamento::getTarefaStatusFinal)
                                .map(TarefaStatusFinal::getFluxoTrabalhoTarefa)
                                .map(FluxoTrabalhoTarefa::getId)
                                .isPresent() ? Optional.<FluxoTrabalhoTarefa>ofNullable(agendamento.getTarefaStatusFinal().getFluxoTrabalhoTarefa()) : Optional.empty(),
                        Collectors.groupingBy(agendamento -> Optional.<TarefaStatusFinalAgendamento>ofNullable(agendamento)
                                .map(TarefaStatusFinalAgendamento::getTarefaStatusFinal)
                                .map(TarefaStatusFinal::getId)
                                .isPresent() ? Optional.<TarefaStatusFinal>ofNullable(agendamento.getTarefaStatusFinal()) : Optional.empty())));

        mapTarefa.forEach((tarefa, mapTarefaStatusFinal) -> {
            mapTarefaStatusFinal.forEach((tarefaStatusFinal, tarefaStatusFinalAgendamentos) -> {
                tarefaStatusFinal.ifPresent(t -> t.setFluxoTrabalhoTarefa(null));

                if (!tarefaStatusFinal.map(TarefaStatusFinal::getStatusFinal).map(StatusFinal::getId).isPresent()) {
                    tarefaStatusFinal.ifPresent(t -> t.setStatusFinal(null));
                }
                if (!tarefaStatusFinal.map(TarefaStatusFinal::getFormulario).map(Formulario::getId).isPresent()) {
                    tarefaStatusFinal.ifPresent(t -> t.setFormulario(null));
                }

                tarefaStatusFinalAgendamentos.forEach(tarefaStatusFinalAgendamento -> {
                    tarefaStatusFinalAgendamento.setTarefaStatusFinal(null);

                    if (!Optional.ofNullable(tarefaStatusFinalAgendamento).map(TarefaStatusFinalAgendamento::getFluxoTrabalhoTarefa).map(FluxoTrabalhoTarefa::getId).isPresent()) {
                        tarefaStatusFinalAgendamento.setFluxoTrabalhoTarefa(null);
                    }
                    if (!Optional.ofNullable(tarefaStatusFinalAgendamento).map(TarefaStatusFinalAgendamento::getCampoData).map(Campo::getId).isPresent()) {
                        tarefaStatusFinalAgendamento.setCampoData(null);
                    }
                    if (!Optional.ofNullable(tarefaStatusFinalAgendamento).map(TarefaStatusFinalAgendamento::getCampoResponsavel).map(Campo::getId).isPresent()) {
                        tarefaStatusFinalAgendamento.setCampoResponsavel(null);
                    }
                    if (!Optional.ofNullable(tarefaStatusFinalAgendamento).map(TarefaStatusFinalAgendamento::getResponsavel).map(Usuario::getId).isPresent()) {
                        tarefaStatusFinalAgendamento.setResponsavel(null);
                    }
                });

                tarefaStatusFinal.ifPresent(t -> t.setAgendamentos(tarefaStatusFinalAgendamentos.stream().filter(a -> a.getId() != null).collect(Collectors.toList())));
            });

            tarefa.ifPresent(t -> t.setStatusFinais(mapTarefaStatusFinal.keySet().stream().filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList())));
        });

        /**
         * Retornando a primeira (e única) tarefa do map
         */
        return mapTarefa.keySet().iterator().next();
    }

    @Override
    public List<FluxoTrabalhoTarefa> findByEsteiraAndCarteira(final Esteira esteira, final Carteira carteira, final String tarefa) {
        final QTarefa qTarefa = QTarefa.tarefa;
        final QCarteira qCarteira = QCarteira.carteira;
        final QEsteira qEsteira = QEsteira.esteira;
        final QEsteiraTarefa qEsteiraTarefa = QEsteiraTarefa.esteiraTarefa;
        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;

        final JPAQuery<FluxoTrabalhoTarefa> jpaQuery = new JPAQueryFactory(entityManager)
                .selectDistinct(qFluxoTrabalhoTarefa)
                .from(qFluxoTrabalhoTarefa)
                .join(qFluxoTrabalhoTarefa.esteiras, qEsteiraTarefa)
                .join(qEsteiraTarefa.esteira, qEsteira)
                .join(qEsteiraTarefa.carteira, qCarteira)
                .join(qFluxoTrabalhoTarefa.tarefa, qTarefa)
                .where(qEsteira.eq(esteira))
                .where(qCarteira.eq(carteira))
                .where(qFluxoTrabalhoTarefa.ativo.isTrue());

        if (StringUtils.isNotBlank(tarefa)) {
            jpaQuery.where(qTarefa.nome.containsIgnoreCase(tarefa));
        }

        return jpaQuery.fetch();
    }

    @Override
    public void removeByFluxoTrabalho(FluxoTrabalho fluxoTrabalho) {
        final QFluxoTrabalho qFluxoTrabalho = QFluxoTrabalho.fluxoTrabalho;
        final QTarefaStatusFinal qTarefaStatusFinal = QTarefaStatusFinal.tarefaStatusFinal;
        final QTarefaStatusFinalAgendamento qTarefaStatusFinalAgendamento = QTarefaStatusFinalAgendamento.tarefaStatusFinalAgendamento;
        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;

        new JPADeleteClause(entityManager, qTarefaStatusFinalAgendamento)
                .where(qTarefaStatusFinalAgendamento.in(JPAExpressions
                        .select(qTarefaStatusFinalAgendamento)
                        .from(qTarefaStatusFinalAgendamento)
                        .join(qTarefaStatusFinalAgendamento.tarefaStatusFinal, qTarefaStatusFinal)
                        .join(qTarefaStatusFinal.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefa)
                        .join(qFluxoTrabalhoTarefa.fluxoTrabalho, qFluxoTrabalho)
                        .where(qFluxoTrabalho.eq(fluxoTrabalho))))
                .execute();

        new JPADeleteClause(entityManager, qTarefaStatusFinal)
                .where(qTarefaStatusFinal.in(JPAExpressions
                        .select(qTarefaStatusFinal)
                        .from(qTarefaStatusFinal)
                        .join(qTarefaStatusFinal.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefa)
                        .join(qFluxoTrabalhoTarefa.fluxoTrabalho, qFluxoTrabalho)
                        .where(qFluxoTrabalho.eq(fluxoTrabalho))))
                .execute();

        new JPADeleteClause(entityManager, qFluxoTrabalhoTarefa)
                .where(qFluxoTrabalhoTarefa.fluxoTrabalho.eq(fluxoTrabalho))
                .execute();

        entityManager.flush();
        entityManager.clear();
    }

    @Override
    public void update(FluxoTrabalhoTarefa fluxoTrabalhoTarefa) {
        entityManager.merge(fluxoTrabalhoTarefa);
        entityManager.flush();
    }

    @Override
    public Optional<FluxoTrabalhoTarefa> findByFluxoAndTarefa(FluxoTrabalho fluxoTrabalho, Tarefa tarefa) {
        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;

        // Está retornando o primeiro da lista porque no momento os dados não estão 100% corretos.

        return Optional.ofNullable(
                new JPAQueryFactory(entityManager)
                        .select(qFluxoTrabalhoTarefa)
                        .from(qFluxoTrabalhoTarefa)
                        .where(qFluxoTrabalhoTarefa.fluxoTrabalho.eq(fluxoTrabalho))
                        .where(qFluxoTrabalhoTarefa.tarefa.eq(tarefa))
                        .fetchFirst()
        );
    }

    @Override
    public Optional<FluxoTrabalhoTarefa> findByFluxoAndTarefa(FluxoTrabalho fluxoTrabalho, Tarefa tarefa, Boolean ativo) {
        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;

        // Está retornando o primeiro da lista porque no momento os dados não estão 100% corretos.

        return Optional.ofNullable(
                new JPAQueryFactory(entityManager)
                        .select(qFluxoTrabalhoTarefa)
                        .from(qFluxoTrabalhoTarefa)
                        .where(qFluxoTrabalhoTarefa.fluxoTrabalho.eq(fluxoTrabalho))
                        .where(qFluxoTrabalhoTarefa.tarefa.eq(tarefa))
                        .where(qFluxoTrabalhoTarefa.ativo.eq(ativo))
                        .fetchFirst()
        );
    }

    @Override
    public void updateAtivo(FluxoTrabalhoTarefa fluxoTrabalhoTarefa, boolean b) {
        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;

        new JPAUpdateClause(entityManager, qFluxoTrabalhoTarefa)
                .set(qFluxoTrabalhoTarefa.ativo, b)
                .where(qFluxoTrabalhoTarefa.eq(fluxoTrabalhoTarefa))
                .execute();
    }

    @Override
    public List<FluxoTrabalhoTarefa> findByFluxoTrabalho(FluxoTrabalho fluxoTrabalho) {

        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;
        final QTarefaStatusFinal qTarefaStatusFinal = QTarefaStatusFinal.tarefaStatusFinal;
        final QTarefaStatusFinalAgendamento qTarefaStatusFinalAgendamento = QTarefaStatusFinalAgendamento.tarefaStatusFinalAgendamento;

        return new JPAQueryFactory(entityManager)
                .selectDistinct(qFluxoTrabalhoTarefa)
                .from(qFluxoTrabalhoTarefa)
                .leftJoin(qFluxoTrabalhoTarefa.statusFinais, qTarefaStatusFinal).fetchJoin()
                .leftJoin(qTarefaStatusFinal.agendamentos, qTarefaStatusFinalAgendamento)
                .where(qFluxoTrabalhoTarefa.fluxoTrabalho.eq(fluxoTrabalho))
                .where(qFluxoTrabalhoTarefa.ativo.eq(Boolean.TRUE))
                .fetch();
    }

    @Override
    public Optional<FluxoTrabalhoTarefa> findByDadosBasicosTarefa(DadosBasicosTarefa dadosBasicosTarefa) {
        QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;
        QDadosBasicosTarefa qDadosBasicosTarefa = QDadosBasicosTarefa.dadosBasicosTarefa;
        return Optional.of(new JPAQueryFactory(entityManager)
                .select(qFluxoTrabalhoTarefa)
                .from(qFluxoTrabalhoTarefa)
                .innerJoin(qFluxoTrabalhoTarefa.dadosBasicosTarefas, qDadosBasicosTarefa)
                .where(qDadosBasicosTarefa.id.eq(dadosBasicosTarefa.getId()))
                .fetchOne());
    }

    @Override
    public List<FluxoTrabalhoTarefa> findByUsuarioNotificacao(Usuario usuario) {

        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;

        return new JPAQueryFactory(entityManager)
                .selectDistinct(qFluxoTrabalhoTarefa)
                .from(qFluxoTrabalhoTarefa)
                .where(qFluxoTrabalhoTarefa.usuario.eq(usuario))
                .where(qFluxoTrabalhoTarefa.ativo.eq(Boolean.TRUE))
                .fetch();
    }
}
