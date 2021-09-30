package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.repository.TarefaStatusFinalAgendamentoJpaRepository;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade TarefaStatusFinalAgendamento.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
@Repository
public class TarefaStatusFinalAgendamentoRepositoryImpl extends AbstractJpaRepository<TarefaStatusFinal,Long> implements TarefaStatusFinalAgendamentoJpaRepository {


    @Override
    public List<TarefaStatusFinalAgendamento> findByTarefas(Collection<Tarefa> tarefas) {
        final QTarefa qTarefa = QTarefa.tarefa;
        final QTarefaStatusFinal qTarefaStatusFinal = QTarefaStatusFinal.tarefaStatusFinal;
        final QStatusFinal qStatusFinal = QStatusFinal.statusFinal;
        final QFormulario qFormularioPadrao = new QFormulario("formularioPadrao");
        final QFormulario qFormulario = QFormulario.formulario;
        final QTarefaStatusFinalAgendamento qTarefaStatusFinalAgendamento = QTarefaStatusFinalAgendamento.tarefaStatusFinalAgendamento;
        final QTarefa qTarefaAgendamento = new QTarefa("tarefaAgendamento");
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
                .from(qTarefaStatusFinalAgendamento)
                .join(qTarefaStatusFinalAgendamento.tarefaStatusFinal, qTarefaStatusFinal)
                .join(qTarefaStatusFinal.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefa)
                .join(qFluxoTrabalhoTarefa.tarefa, qTarefa)
                .leftJoin(qTarefaStatusFinal.statusFinal, qStatusFinal)
                .leftJoin(qStatusFinal.formulario, qFormularioPadrao)
                .leftJoin(qTarefaStatusFinal.formulario, qFormulario)
                .leftJoin(qTarefaStatusFinalAgendamento.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefaAgendamento)
                .leftJoin(qFluxoTrabalhoTarefaAgendamento.tarefa, qTarefaAgendamento)
                .leftJoin(qTarefaStatusFinalAgendamento.campoData, qCampoData)
                .leftJoin(qTarefaStatusFinalAgendamento.campoResponsavel, qCampoResponsavel)
                .leftJoin(qTarefaStatusFinalAgendamento.responsavel, qUsuarioResponsavel)
                .leftJoin(qUsuarioResponsavel.pessoa, qPessoaResponsavel)
                .where(qTarefaAgendamento.in(tarefas))
                .fetch();

        agendamentos.forEach(tarefaStatusFinalAgendamento -> {
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
            if (!Optional.ofNullable(tarefaStatusFinalAgendamento).map(TarefaStatusFinalAgendamento::getTarefaStatusFinal).map(TarefaStatusFinal::getStatusFinal).map(StatusFinal::getId).isPresent()) {
                Optional.ofNullable(tarefaStatusFinalAgendamento).map(TarefaStatusFinalAgendamento::getTarefaStatusFinal).ifPresent(t -> t.setStatusFinal(null));
            }
            if (!Optional.ofNullable(tarefaStatusFinalAgendamento).map(TarefaStatusFinalAgendamento::getTarefaStatusFinal).map(TarefaStatusFinal::getFormulario).map(Formulario::getId).isPresent()) {
                Optional.ofNullable(tarefaStatusFinalAgendamento).map(TarefaStatusFinalAgendamento::getTarefaStatusFinal).ifPresent(t -> t.setFormulario(null));
            }
        });

        return agendamentos;
    }

    @Override
    public void removeByTarefa(FluxoTrabalhoTarefa fluxoTrabalhoTarefa) {
        final QTarefaStatusFinalAgendamento qTarefaStatusFinalAgendamento = QTarefaStatusFinalAgendamento.tarefaStatusFinalAgendamento;
        final QTarefaStatusFinal qTarefaStatusFinal = QTarefaStatusFinal.tarefaStatusFinal;

        new JPADeleteClause(entityManager, qTarefaStatusFinalAgendamento)
                .where(JPAExpressions
                        .select(qTarefaStatusFinal.count())
                        .from(qTarefaStatusFinal)
                        .where(qTarefaStatusFinal.eq(qTarefaStatusFinalAgendamento.tarefaStatusFinal))
                        .where(qTarefaStatusFinal.fluxoTrabalhoTarefa.eq(fluxoTrabalhoTarefa))
                        .gt(0L))
                .execute();

        entityManager.flush();
    }

    @Override
    public List<TarefaStatusFinalAgendamento> findByFluxo(FluxoTrabalho fluxoTrabalho) {
        final QTarefa qTarefa = QTarefa.tarefa;
        final QTarefaStatusFinalAgendamento qTarefaStatusFinalAgendamento = QTarefaStatusFinalAgendamento.tarefaStatusFinalAgendamento;
        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;

        final JPAQuery<TarefaStatusFinalAgendamento> jpaQuery = new JPAQueryFactory(entityManager)
                .select(qTarefaStatusFinalAgendamento)
                .from(qTarefaStatusFinalAgendamento)
                .innerJoin(qTarefaStatusFinalAgendamento.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefa).fetchJoin()
                .innerJoin(qFluxoTrabalhoTarefa.tarefa, qTarefa).fetchJoin()
                .where(qFluxoTrabalhoTarefa.fluxoTrabalho.eq(fluxoTrabalho));

        return jpaQuery.fetch();
    }

    @Override
    public void create(TarefaStatusFinalAgendamento tarefaStatusFinalAgendamento) {
        entityManager.persist(tarefaStatusFinalAgendamento);
        entityManager.flush();
    }

    @Override
    public List<TarefaStatusFinalAgendamento> findByTarefa(FluxoTrabalhoTarefa fluxoTrabalhoTarefa) {
        QTarefaStatusFinalAgendamento qTarefaStatusFinalAgendamento = QTarefaStatusFinalAgendamento.tarefaStatusFinalAgendamento;
        QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;

        return new JPAQueryFactory(entityManager)
                .select(qTarefaStatusFinalAgendamento)
                .from(qTarefaStatusFinalAgendamento)
                .join(qTarefaStatusFinalAgendamento.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefa)
                .where(qFluxoTrabalhoTarefa.eq(fluxoTrabalhoTarefa))
                .fetch();
    }

    @Override
    public List<TarefaStatusFinalAgendamento> findByTarefaStatusFinal(TarefaStatusFinal tarefaStatusFinal) {
        QTarefaStatusFinalAgendamento qTarefaStatusFinalAgendamento = QTarefaStatusFinalAgendamento.tarefaStatusFinalAgendamento;
        return new JPAQueryFactory(entityManager)
                .select(qTarefaStatusFinalAgendamento)
                .from(qTarefaStatusFinalAgendamento)
                .where(qTarefaStatusFinalAgendamento.tarefaStatusFinal.eq(tarefaStatusFinal))
                .fetch();
    }
}
