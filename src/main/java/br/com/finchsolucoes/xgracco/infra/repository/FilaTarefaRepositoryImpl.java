package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.repository.FilaTarefaJpaRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;

/**
 * @author Felipi Berdun
 * @since 2.1
 */
@Repository
public class FilaTarefaRepositoryImpl implements FilaTarefaJpaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<FilaTarefa> findByFila(Fila fila) {
        final QFila qFila = QFila.fila;
        final QTarefa qTarefa = QTarefa.tarefa;
        final QCarteira qCarteira = QCarteira.carteira;
        final QFilaTarefa qFilaTarefa = QFilaTarefa.filaTarefa;
        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;

        return new JPAQueryFactory(entityManager)
                .select(qFilaTarefa)
                .from(qFilaTarefa)
                .join(qFilaTarefa.fila, qFila)
                .join(qFilaTarefa.carteira, qCarteira).fetchJoin()
                .join(qFilaTarefa.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefa).fetchJoin()
                .join(qFluxoTrabalhoTarefa.tarefa, qTarefa).fetchJoin()
                .where(qFila.eq(fila))
                .fetch();
    }

    @Override
    public List<FilaTarefa> findByTarefas(Collection<Tarefa> tarefas) {
        final QFila qFila = QFila.fila;
        final QFilaTarefa qFilaTarefa = QFilaTarefa.filaTarefa;
        final QTarefa qTarefa = QTarefa.tarefa;
        final QFluxoTrabalho qFluxoTrabalho = QFluxoTrabalho.fluxoTrabalho;
        final QCarteira qCarteira = QCarteira.carteira;
        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;

        return new JPAQueryFactory(entityManager)
                .select(QFilaTarefa.create(
                        qFilaTarefa.id,
                        QFila.create(
                                qFila.id,
                                qFila.descricao
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
                        QCarteira.create(
                                qCarteira.id,
                                qCarteira.uid,
                                qCarteira.descricao
                        )
                ))
                .from(qFilaTarefa)
                .join(qFilaTarefa.fila, qFila)
                .join(qFilaTarefa.carteira, qCarteira)
                .join(qFilaTarefa.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefa)
                .join(qFluxoTrabalhoTarefa.tarefa, qTarefa)
                .where(qTarefa.in(tarefas))
                .fetch();
    }

    @Override
    public List<Fila> findByEsteiraAndTarefa(Esteira esteira, FluxoTrabalhoTarefa fluxoTrabalhoTarefa) {
        QFila qFila = QFila.fila;
        QFilaTarefa qFilaTarefa = QFilaTarefa.filaTarefa;

        return new JPAQueryFactory(this.entityManager)
                .select(qFila)
                .from(qFila)
                .join(qFila.tarefas, qFilaTarefa)
                .where(qFila.esteira.eq(esteira))
                .where(qFilaTarefa.fluxoTrabalhoTarefa.eq(fluxoTrabalhoTarefa))
                .fetch();
    }
}
