package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.repository.ModeloAgendamentoJpaRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;

/**
 * Implementação JPA do repositório da entidade ModeloAgendamento.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
@Repository
public class ModeloAgendamentoRepositoryImpl implements  ModeloAgendamentoJpaRepository {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public List<ModeloAgendamento> findByTarefas(Collection<Tarefa> tarefas) {
        final QModeloAgendamento qModeloAgendamento = QModeloAgendamento.modeloAgendamento;
        final QGrupoAgendamento qGrupoAgendamento = QGrupoAgendamento.grupoAgendamento;
        final QCarteira qCarteira = QCarteira.carteira;
        final QTarefa qTarefa = QTarefa.tarefa;
        final QFluxoTrabalho qFluxoTrabalho = QFluxoTrabalho.fluxoTrabalho;
        final QPessoa qPessoa = QPessoa.pessoa;
        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;

        return new JPAQueryFactory(entityManager)
                .select(QModeloAgendamento.create(
                        qModeloAgendamento.id,
                        qModeloAgendamento.descricao,
                        QGrupoAgendamento.create(
                                qGrupoAgendamento.id,
                                qGrupoAgendamento.descricao,
                                QCarteira.create(
                                        qCarteira.id,
                                        qCarteira.uid,
                                        qCarteira.descricao
                                ),
                                qGrupoAgendamento.area,
                                qGrupoAgendamento.tipoContagemDias
                        ),
                        QFluxoTrabalhoTarefa.create(
                                qFluxoTrabalhoTarefa.id,
                                QTarefa.create(
                                        qTarefa.id,
                                        qTarefa.idTarefa,
                                        qTarefa.nome,
                                        qTarefa.descricao
                                )
                        ),
                        qModeloAgendamento.memoPadrao,
                        QPessoa.create(
                                qPessoa.id,
                                qPessoa.nomeRazaoSocial,
                                qPessoa.apelidoFantasia,
                                qPessoa.rgInscricaoEstadual,
                                qPessoa.cpfCnpj
                        ),
                        qModeloAgendamento.tipoDestinatario
                ))
                .from(qModeloAgendamento)
                .join(qModeloAgendamento.grupoAgendamento, qGrupoAgendamento)
                .join(qGrupoAgendamento.carteira, qCarteira)
                .join(qModeloAgendamento.pessoa, qPessoa)
                .join(qModeloAgendamento.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefa)
                .join(qFluxoTrabalhoTarefa.tarefa, qTarefa)
                .where(qTarefa.in(tarefas))
                .fetch();
    }

    @Override
    public List<ModeloAgendamento> findByGrupoAgendamento(GrupoAgendamento grupoAgendamento) {
        final QGrupoAgendamento qGrupoAgendamento = QGrupoAgendamento.grupoAgendamento;
        final QModeloAgendamento qModeloAgendamento = QModeloAgendamento.modeloAgendamento;
        final QTarefa qTarefa = QTarefa.tarefa;
        final QPessoa qPessoa = QPessoa.pessoa;
        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;

        return new JPAQueryFactory(entityManager)
                .select(qModeloAgendamento)
                .from(qModeloAgendamento)
                .join(qModeloAgendamento.grupoAgendamento, qGrupoAgendamento)
                .join(qModeloAgendamento.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefa).fetchJoin()
                .leftJoin(qModeloAgendamento.pessoa, qPessoa).fetchJoin()
                .where(qGrupoAgendamento.eq(grupoAgendamento))
                .fetch();
    }

    @Override
    public List<ModeloAgendamento> findByTarefa(FluxoTrabalhoTarefa fluxoTrabalhoTarefa) {
        QModeloAgendamento qModeloAgendamento = QModeloAgendamento.modeloAgendamento;
        QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;

        return new JPAQueryFactory(entityManager)
                .select(qModeloAgendamento)
                .from(qModeloAgendamento)
                .join(qModeloAgendamento.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefa)
                .where(qFluxoTrabalhoTarefa.eq(fluxoTrabalhoTarefa))
                .fetch();
    }

}
