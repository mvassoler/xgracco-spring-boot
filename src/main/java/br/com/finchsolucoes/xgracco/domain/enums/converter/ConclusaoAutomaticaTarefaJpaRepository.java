package br.com.finchsolucoes.xgracco.domain.enums.converter;

import org.springframework.stereotype.Repository;

@Repository
public class ConclusaoAutomaticaTarefaJpaRepository { //} implements ConclusaoAutomaticaTarefaRepository {

    //TODO - ACERTAR ESTA CLASSE

   /* @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ConclusaoAutomaticaTarefa> find(ConclusaoAutomatica conclusaoAutomatica, Query<ConclusaoAutomaticaTarefa> query) {

        final JPAQuery<ConclusaoAutomaticaTarefa> jpaQuery = createQuery(conclusaoAutomatica, query);
        final PathBuilder<ConclusaoAutomaticaTarefa> builder = new PathBuilder<>(ConclusaoAutomaticaTarefa.class, "conclusaoAutomaticaTarefa");

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

    private JPAQuery<ConclusaoAutomaticaTarefa> createQuery(ConclusaoAutomatica conclusaoAutomatica, Query<ConclusaoAutomaticaTarefa> query) {
        final QConclusaoAutomaticaTarefa qConclusaoAutomaticaTarefa = QConclusaoAutomaticaTarefa.conclusaoAutomaticaTarefa;
        final QConclusaoAutomatica qConclusaoAutomatica = QConclusaoAutomatica.conclusaoAutomatica;
        final QTarefa qTarefa = QTarefa.tarefa;
        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;

        JPAQuery jpaQuery = new JPAQueryFactory(entityManager)
                .select(QConclusaoAutomaticaTarefa.create(
                        qConclusaoAutomaticaTarefa.id,
                        QConclusaoAutomatica.create(
                                qConclusaoAutomatica.id
                        ),
                        QFluxoTrabalhoTarefa.create(
                                qFluxoTrabalhoTarefa.id,
                                QTarefa.create(
                                        qTarefa.id,
                                        qTarefa.nome
                                )
                        )
                ))
                .from(qConclusaoAutomaticaTarefa)
                .join(qConclusaoAutomaticaTarefa.conclusaoAutomatica, qConclusaoAutomatica)
                .join(qConclusaoAutomaticaTarefa.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefa)
                .join(qFluxoTrabalhoTarefa.tarefa, qTarefa)
                .where(qConclusaoAutomaticaTarefa.conclusaoAutomatica.eq(conclusaoAutomatica));

        return jpaQuery;
    }

    @Override
    public void create(ConclusaoAutomaticaTarefa conclusaoAutomaticaTarefa) {
        entityManager.persist(conclusaoAutomaticaTarefa);
        entityManager.flush();
    }

    @Override
    public void delete(ConclusaoAutomaticaTarefa conclusaoAutomaticaTarefa) {
        entityManager.remove(conclusaoAutomaticaTarefa);
        entityManager.flush();
    }

    @Override
    public void deleteByConclusaoAndIdTarefa(ConclusaoAutomatica conclusaoAutomatica, Long idTarefa) {
        final QConclusaoAutomaticaTarefa qConclusaoAutomaticaTarefa = QConclusaoAutomaticaTarefa.conclusaoAutomaticaTarefa;

        new JPADeleteClause(entityManager, qConclusaoAutomaticaTarefa)
                .where(qConclusaoAutomaticaTarefa.conclusaoAutomatica.eq(conclusaoAutomatica))
                .where(qConclusaoAutomaticaTarefa.fluxoTrabalhoTarefa.id.eq(idTarefa))
                .execute();
    }

    @Override
    public long count(ConclusaoAutomatica conclusaoAutomatica, Query<ConclusaoAutomaticaTarefa> query) {
        return createQuery(conclusaoAutomatica, query).fetchCount();
    }*/
}
