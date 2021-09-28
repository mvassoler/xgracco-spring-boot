package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.enums.EnumStatusTarefa;
import br.com.finchsolucoes.xgracco.domain.repository.TarefaStatusFinalJpaRepository;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.math3.analysis.function.Abs;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade TarefaStatusFinal.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
@Repository
public class TarefaStatusFinalRepositoryImpl extends AbstractJpaRepository<TarefaStatusFinal,Long> implements TarefaStatusFinalJpaRepository {



    @Override
    public Optional<TarefaStatusFinal> findByIdFetchAgendamentos(Long id) {
        final QTarefaStatusFinal qTarefaStatusFinal = QTarefaStatusFinal.tarefaStatusFinal;
        final QTarefa qTarefa = QTarefa.tarefa;
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

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .selectDistinct(qTarefaStatusFinal)
                .from(qTarefaStatusFinal)
                .join(qTarefaStatusFinal.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefa).fetchJoin()
//                .join(qFluxoTrabalhoTarefa.tarefa, qTarefa).fetchJoin()
                .join(qTarefaStatusFinal.statusFinal, qStatusFinal).fetchJoin()
                .leftJoin(qStatusFinal.formulario, qFormularioPadrao).fetchJoin()
                .leftJoin(qTarefaStatusFinal.formulario, qFormulario).fetchJoin()
                .leftJoin(qTarefaStatusFinal.agendamentos, qTarefaStatusFinalAgendamento).fetchJoin()
                .leftJoin(qTarefaStatusFinalAgendamento.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefaAgendamento)
//                .leftJoin(qFluxoTrabalhoTarefaAgendamento.tarefa, qTarefaAgendamento).fetchJoin()
                .leftJoin(qTarefaStatusFinalAgendamento.campoData, qCampoData).fetchJoin()
                .leftJoin(qTarefaStatusFinalAgendamento.campoResponsavel, qCampoResponsavel).fetchJoin()
                .leftJoin(qTarefaStatusFinalAgendamento.responsavel, qUsuarioResponsavel).fetchJoin()
                .leftJoin(qUsuarioResponsavel.pessoa, qPessoaResponsavel).fetchJoin()
                .where(qTarefaStatusFinal.id.eq(id))
                .fetchOne());
    }

    @Override
    public List<TarefaStatusFinal> findByIdStatusFinal(StatusFinal statusFinal) {
        final QTarefaStatusFinal qTarefaStatusFinal = QTarefaStatusFinal.tarefaStatusFinal;

        return new JPAQueryFactory(entityManager)
                .select(qTarefaStatusFinal)
                .from(qTarefaStatusFinal)
                .where(qTarefaStatusFinal.statusFinal.id.eq(statusFinal.getId()))
                .fetch();
    }

    @Override
    public void removeByTarefa(FluxoTrabalhoTarefa fluxoTrabalhoTarefa) {
        final QTarefaStatusFinal qTarefaStatusFinal = QTarefaStatusFinal.tarefaStatusFinal;
        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;

        new JPADeleteClause(entityManager, qTarefaStatusFinal)
                .where(qTarefaStatusFinal.fluxoTrabalhoTarefa.eq(fluxoTrabalhoTarefa))
                .execute();

        entityManager.flush();
    }

    @Override
    public List<TarefaStatusFinal> findByTarefa(FluxoTrabalhoTarefa fluxoTrabalhoTarefa) {

        final QTarefaStatusFinal qTarefaStatusFinal = QTarefaStatusFinal.tarefaStatusFinal;
        final QTarefa qTarefa = QTarefa.tarefa;
        final QStatusFinal qStatusFinal = QStatusFinal.statusFinal;
        final QFormulario qFormulario = QFormulario.formulario;
        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;

        return new JPAQueryFactory(entityManager)
                .select(QTarefaStatusFinal.create(
                        qTarefaStatusFinal.id,
                        QFluxoTrabalhoTarefa.create(
                                qFluxoTrabalhoTarefa.id,
                                QTarefa.create(qTarefa.id, qTarefa.nome)
                        ),
                        QStatusFinal.create(
                                qStatusFinal.id,
                                qStatusFinal.descricao
                        ),
                        qTarefaStatusFinal.status,
                        QFormulario.create(
                                qFormulario.id
                        )
                ))
                .from(qTarefaStatusFinal)
                .join(qTarefaStatusFinal.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefa)
                .join(qFluxoTrabalhoTarefa.tarefa, qTarefa)
                .join(qTarefaStatusFinal.statusFinal, qStatusFinal)
                .leftJoin(qTarefaStatusFinal.formulario, qFormulario)
                .where(qFluxoTrabalhoTarefa.eq(fluxoTrabalhoTarefa))
                .fetch();
    }

    @Override
    public List<TarefaStatusFinal> findByFluxoTrabalho(FluxoTrabalho fluxoTrabalho) {
        final QTarefaStatusFinal qTarefaStatusFinal = QTarefaStatusFinal.tarefaStatusFinal;
        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;

        return new JPAQueryFactory(entityManager)
                .select(qTarefaStatusFinal)
                .from(qTarefaStatusFinal)
                .join(qTarefaStatusFinal.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefa)
                .where(qFluxoTrabalhoTarefa.fluxoTrabalho.eq(fluxoTrabalho))
                .fetch();
    }

    @Override
    public Long countByFluxoTrabalhoTarefaAndStatusFinal(FluxoTrabalhoTarefa fluxoTrabalhoTarefa, StatusFinal statusFinal) {
        final QTarefaStatusFinal qTarefaStatusFinal = QTarefaStatusFinal.tarefaStatusFinal;
        return new JPAQueryFactory(entityManager)
                .select(qTarefaStatusFinal)
                .from(qTarefaStatusFinal)
                .where(qTarefaStatusFinal.fluxoTrabalhoTarefa.id.eq(fluxoTrabalhoTarefa.getId())
                        .and(qTarefaStatusFinal.statusFinal.id.eq(statusFinal.getId())))
                .fetchCount();
    }

    @Override
    public List<TarefaStatusFinal> findByFluxoTrabalhoTarefaAndStatusFinalAndStatusFetchAgendamentos(FluxoTrabalhoTarefa fluxoTrabalhoTarefa,
                                                                                                     StatusFinal statusFinal,
                                                                                                     EnumStatusTarefa statusTarefa) {
        final QTarefaStatusFinal qTarefaStatusFinal = QTarefaStatusFinal.tarefaStatusFinal;
        return new JPAQueryFactory(entityManager)
                .select(qTarefaStatusFinal)
                .from(qTarefaStatusFinal)
                .leftJoin(qTarefaStatusFinal.agendamentos).fetchJoin()
                .where(qTarefaStatusFinal.fluxoTrabalhoTarefa.id.eq(fluxoTrabalhoTarefa.getId())
                        .and(qTarefaStatusFinal.statusFinal.id.eq(statusFinal.getId()))
                        .and(qTarefaStatusFinal.status.eq(statusTarefa)))
                .fetch();
    }
}
