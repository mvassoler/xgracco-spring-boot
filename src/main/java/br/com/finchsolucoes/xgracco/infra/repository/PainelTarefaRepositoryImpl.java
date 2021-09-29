package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.repository.PainelTarefaJpaRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;

/**
 * Implementação JPA do repositório da entidade PainelTarefa.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
@Repository
public class PainelTarefaRepositoryImpl implements PainelTarefaJpaRepository {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public List<PainelTarefa> findByTarefas(Collection<Tarefa> tarefas) {
        final QPainel qPainel = QPainel.painel;
        final QCarteira qCarteira = QCarteira.carteira;
        final QPainelTarefa qPainelTarefa = QPainelTarefa.painelTarefa;
        final QTarefa qTarefa = QTarefa.tarefa;
        final QFluxoTrabalho qFluxoTrabalho = QFluxoTrabalho.fluxoTrabalho;
        final QCampo qCampoData = QCampo.campo;
        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;

        return new JPAQueryFactory(entityManager)
                .select(QPainelTarefa.create(
                        qPainelTarefa.id,
                        qPainelTarefa.tipoDataSla,
                        qPainelTarefa.tipoIntervaloSla,
                        qPainelTarefa.sla,
                        qPainelTarefa.principal,
                        QPainel.create(
                                qPainel.id,
                                qPainel.nome,
                                QCarteira.create(
                                        qCarteira.id,
                                        qCarteira.uid,
                                        qCarteira.descricao
                                )
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
                        QCampo.create(
                                qCampoData.id,
                                qCampoData.descricao
                        )
                ))
                .from(qPainelTarefa)
                .join(qPainelTarefa.painel, qPainel)
                .join(qPainel.carteira, qCarteira)
                .join(qPainelTarefa.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefa)
                .join(qFluxoTrabalhoTarefa.tarefa, qTarefa)
                .leftJoin(qPainelTarefa.campoData, qCampoData)
                .where(qTarefa.in(tarefas))
                .fetch();
    }

    @Override
    public List<PainelTarefa> findTarefas(Painel painel) {
        final QPainel qPainel = QPainel.painel;
        final QCarteira qCarteira = QCarteira.carteira;
        final QPainelTarefa qPainelTarefa = QPainelTarefa.painelTarefa;
        final QTarefa qTarefa = QTarefa.tarefa;
        final QFluxoTrabalho qFluxoTrabalho = QFluxoTrabalho.fluxoTrabalho;
        final QCampo qCampoData = QCampo.campo;
        final QFormulario qFormulario = QFormulario.formulario;
        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;

        return  null;
        // TODO - ACERTAR ESTA CLASSE

        /*return new JPAQueryFactory(entityManager)
                .select(QPainelTarefa.create(
                        qPainelTarefa.id,
                        qPainelTarefa.tipoDataSla,
                        qPainelTarefa.tipoIntervaloSla,
                        qPainelTarefa.sla,
                        qPainelTarefa.principal,
                        QPainel.create(
                                qPainel.id,
                                qPainel.nome,
                                QCarteira.create(
                                        qCarteira.id,
                                        qCarteira.uid,
                                        qCarteira.descricao
                                )
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
                        QCampo.create(
                                qCampoData.id,
                                qCampoData.descricao,
                                QFormulario.create(
                                        qFormulario.id,
                                        qFormulario.nome)
                        )
                ))
                .from(qPainelTarefa)
                .join(qPainelTarefa.painel, qPainel)
                .join(qPainel.carteira, qCarteira)
                .join(qPainelTarefa.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefa)
                .join(qFluxoTrabalhoTarefa.tarefa, qTarefa)
                .leftJoin(qPainelTarefa.campoData, qCampoData)
                .leftJoin(qCampoData.formulario, qFormulario)
                .where(qPainel.eq(painel))
                .fetch();*/
    }

    @Override
    public List<PainelTarefa> findByTarefa(FluxoTrabalhoTarefa fluxoTrabalhoTarefa) {
        QPainelTarefa qPainelTarefa = QPainelTarefa.painelTarefa;
        QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;

        return new JPAQueryFactory(entityManager)
                .select(qPainelTarefa)
                .from(qPainelTarefa)
                .join(qPainelTarefa.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefa)
                .where(qFluxoTrabalhoTarefa.eq(fluxoTrabalhoTarefa))
                .fetch();
    }
}
