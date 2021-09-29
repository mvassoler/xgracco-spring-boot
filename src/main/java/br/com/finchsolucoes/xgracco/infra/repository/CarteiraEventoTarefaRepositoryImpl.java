package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.repository.CarteiraEventoTarefaJpaRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;

/**
 * Implementação JPA do repositório da entidade CarteiraEventoTarefa.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
@Repository
public class CarteiraEventoTarefaRepositoryImpl implements CarteiraEventoTarefaJpaRepository {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public List<CarteiraEventoTarefa> findByTarefas(Collection<Tarefa> tarefas) {
        final QCarteiraEventoTarefa qCarteiraEventoTarefa = QCarteiraEventoTarefa.carteiraEventoTarefa;
        final QCarteiraEvento qCarteiraEvento = QCarteiraEvento.carteiraEvento;
        final QCarteira qCarteira = QCarteira.carteira;
        final QTarefa qTarefa = QTarefa.tarefa;
        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;

        return new JPAQueryFactory(entityManager)
                .select(QCarteiraEventoTarefa.create(
                        qCarteiraEventoTarefa.id,
                        QCarteiraEvento.create(
                                qCarteiraEvento.id,
                                QCarteira.create(
                                        qCarteira.id,
                                        qCarteira.uid,
                                        qCarteira.descricao
                                ),
                                qCarteiraEvento.evento,
                                qCarteiraEvento.nome
                        ),
                        QFluxoTrabalhoTarefa.create(
                                qFluxoTrabalhoTarefa.id,
                                QTarefa.create(
                                        qTarefa.id,
                                        qTarefa.idTarefa,
                                        qTarefa.nome,
                                        qTarefa.descricao
                                )
                        )
                ))
                .from(qCarteiraEventoTarefa)
                .join(qCarteiraEventoTarefa.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefa)
                .join(qFluxoTrabalhoTarefa.tarefa, qTarefa)
                .join(qCarteiraEventoTarefa.carteiraEvento, qCarteiraEvento)
                .join(qCarteiraEvento.carteira, qCarteira)
                .where(qTarefa.in(tarefas))
                .fetch();
    }

    @Override
    public List<CarteiraEventoTarefa> findByTarefa(FluxoTrabalhoTarefa fluxoTrabalhoTarefa) {
        QCarteiraEventoTarefa qCarteiraEventoTarefa = QCarteiraEventoTarefa.carteiraEventoTarefa;
        QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;

        return new JPAQueryFactory(entityManager)
                .select(qCarteiraEventoTarefa)
                .from(qCarteiraEventoTarefa)
                .join(qCarteiraEventoTarefa.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefa)
                .where(qFluxoTrabalhoTarefa.eq(fluxoTrabalhoTarefa))
                .fetch();
    }
}
