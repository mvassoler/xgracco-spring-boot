package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.query.Query;
import br.com.finchsolucoes.xgracco.domain.repository.EsteiraTarefaJpaRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author Felipi Berdun
 * @since 2.1
 */
@Repository
public class EsteiraTarefaRepositoryImpl extends AbstractJpaRepository<EsteiraTarefa, Long> implements EsteiraTarefaJpaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<EsteiraTarefa> findByEsteira(Esteira esteira) {
        final QEsteiraTarefa qEsteiraTarefa = QEsteiraTarefa.esteiraTarefa;
        final QTarefa qTarefa = QTarefa.tarefa;
        final QCarteira qCarteira = QCarteira.carteira;
        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;

        return new JPAQueryFactory(entityManager)
                .select(qEsteiraTarefa)
                .from(qEsteiraTarefa)
                .join(qEsteiraTarefa.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefa).fetchJoin()
                .join(qFluxoTrabalhoTarefa.tarefa, qTarefa).fetchJoin()
                .join(qEsteiraTarefa.carteira, qCarteira).fetchJoin()
                .where(qEsteiraTarefa.esteira.eq(esteira))
                .fetch();
    }

    @Override
    public List<EsteiraTarefa> findByTarefas(Collection<Tarefa> tarefas) {
        final QEsteira qEsteira = QEsteira.esteira;
        final QEsteiraTarefa qEsteiraTarefa = QEsteiraTarefa.esteiraTarefa;
        final QTarefa qTarefa = QTarefa.tarefa;
        final QCarteira qCarteira = QCarteira.carteira;
        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;

        return new JPAQueryFactory(entityManager)
                .select(QEsteiraTarefa.create(
                        qEsteiraTarefa.id,
                        QEsteira.create(
                                qEsteira.id,
                                qEsteira.descricao
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
                .from(qEsteiraTarefa)
                .join(qEsteiraTarefa.esteira, qEsteira)
                .join(qEsteiraTarefa.carteira, qCarteira)
                .join(qEsteiraTarefa.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefa)
                .join(qFluxoTrabalhoTarefa.tarefa, qTarefa)
                .where(qTarefa.in(tarefas))
                .fetch();
    }

    @Override
    public void deleteEsteiraTarefa(Long id) {
        QEsteiraTarefa qEsteiraTarefa = QEsteiraTarefa.esteiraTarefa;
        new JPAQueryFactory(entityManager)
                .delete(qEsteiraTarefa)
                .where(qEsteiraTarefa.id.eq(id))
                .execute();
    }

    @Override
    public Optional<EsteiraTarefa> findByEsteiraAndCarteiraAndTarefa(Esteira esteira, Carteira carteira, FluxoTrabalhoTarefa fluxoTrabalhoTarefa) {
        final QEsteiraTarefa qEsteiraTarefa = QEsteiraTarefa.esteiraTarefa;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(qEsteiraTarefa)
                .from(qEsteiraTarefa)
                .where(qEsteiraTarefa.esteira.eq(esteira))
                .where(qEsteiraTarefa.fluxoTrabalhoTarefa.eq(fluxoTrabalhoTarefa))
                .where(qEsteiraTarefa.carteira.eq(carteira))
                .fetchOne()
        );

    }

    @Override
    public List<EsteiraTarefa> find(Query<EsteiraTarefa> query) {
        return null;
    }

    @Override
    public long count(Query<EsteiraTarefa> query) {
        return 0;
    }
}
