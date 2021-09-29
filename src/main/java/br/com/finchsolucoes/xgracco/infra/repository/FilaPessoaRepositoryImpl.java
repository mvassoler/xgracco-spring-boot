package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.repository.FilaPessoaJpaRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class FilaPessoaRepositoryImpl implements FilaPessoaJpaRepository {


    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Consulta as pessoas que estão relacionadas com a fila.
     *
     * @param fila
     * @return
     */
    @Override
    public List<FilaPessoa> findByFila(Fila fila) {
        final QFila qFila = QFila.fila;
        final QPessoa qPessoa = QPessoa.pessoa;
        final QFilaPessoa qFilaPessoa = QFilaPessoa.filaPessoa;

        return new JPAQueryFactory(entityManager)
                .select(qFilaPessoa)
                .from(qFilaPessoa)
                .join(qFilaPessoa.fila, qFila).fetchJoin()
                .join(qFilaPessoa.pessoa, qPessoa).fetchJoin()
                .where(qFila.id.eq(fila.getId()))
                .fetch();
    }

    /**
     * Consulta as filas em que a pessoa está relacionada.
     *
     * @param idPessoa
     * @return
     */
    @Override
    public List<FilaPessoa> findByPessoa(Long idPessoa) {
        final QFila qFila = QFila.fila;
        final QPessoa qPessoa = QPessoa.pessoa;
        final QFilaPessoa qFilaPessoa = QFilaPessoa.filaPessoa;

        return new JPAQueryFactory(entityManager)
                .select(qFilaPessoa)
                .from(qFilaPessoa)
                .join(qFilaPessoa.fila, qFila)
                .join(qFilaPessoa.pessoa, qPessoa).fetchJoin()
                .where(qPessoa.id.eq(idPessoa))
                .fetch();
    }

    /**
     * Consulta a fila em que a pessoa está ativa.
     *
     * @param idPessoa
     * @return
     */
    @Override
    public Optional<FilaPessoa> findByPessoaAtivo(Long idPessoa) {
        final QFila qFila = QFila.fila;
        final QPessoa qPessoa = QPessoa.pessoa;
        final QFilaPessoa qFilaPessoa = QFilaPessoa.filaPessoa;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(qFilaPessoa)
                .from(qFilaPessoa)
                .join(qFilaPessoa.fila, qFila).fetchJoin()
                .join(qFila.esteira).fetchJoin()
                .join(qFilaPessoa.pessoa, qPessoa).fetchJoin()
                .where(qPessoa.id.eq(idPessoa))
                .where(qFilaPessoa.ativo.eq(Boolean.TRUE))
                .fetchOne());
    }

    @Override
    public Optional<List<FilaPessoa>> findByPessoaAndEsteira(Long idPessoa, Long idEsteira){

        final QPessoa qPessoa = QPessoa.pessoa;
        final QEsteira qEsteira = QEsteira.esteira;
        final QFilaPessoa qFilaPessoa = QFilaPessoa.filaPessoa;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(qFilaPessoa)
                .from(qFilaPessoa)
                .join(qFilaPessoa.pessoa, qPessoa).fetchJoin()
                .join(qPessoa.esteiras, qEsteira).fetchJoin()
                .where(qPessoa.id.eq(idPessoa))
                .where(qEsteira.id.eq(idEsteira))
                .where(qFilaPessoa.ativo.eq(Boolean.TRUE))
                .fetch());

    }

    /**
     * Consulta a relação filapessoa através das duas entidades
     *
     * @param idFila
     * @param idPessoa
     * @return
     */
    @Override
    public Optional<FilaPessoa> findByFilaAndPessoa(Long idFila, Long idPessoa) {
        final QFila qFila = QFila.fila;
        final QPessoa qPessoa = QPessoa.pessoa;
        final QFilaPessoa qFilaPessoa = QFilaPessoa.filaPessoa;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(qFilaPessoa)
                .from(qFilaPessoa)
                .join(qFilaPessoa.fila, qFila)
                .join(qFilaPessoa.pessoa, qPessoa).fetchJoin()
                .where(qPessoa.id.eq(idPessoa))
                .where(qFila.id.eq(idFila))
                .fetchOne());
    }

    /**
     * Altera a entidade fila pessoa.
     * Caso o status seja true, altera as demais relações da pessoa para ativo = false;
     *
     * @param status, idFila, idPessoa
     */
    @Override
    public void update(Boolean status, Long idFila, Long idPessoa) {
        QFilaPessoa qFilaPessoa = QFilaPessoa.filaPessoa;

        new JPAUpdateClause(entityManager, qFilaPessoa)
                .set(qFilaPessoa.ativo, status)
                .where(qFilaPessoa.fila.id.eq(idFila))
                .where(qFilaPessoa.pessoa.id.eq(idPessoa))
                .execute();

        if (status) {
            new JPAUpdateClause(entityManager, qFilaPessoa)
                    .set(qFilaPessoa.ativo, false)
                    .where(qFilaPessoa.pessoa.id.eq(idPessoa))
                    .where(qFilaPessoa.fila.id.notIn(idFila))
                    .execute();
        }
    }

    /**
     * Consulta quantos recursos ativos a fila possui.
     *
     * @param idFila
     */
    @Override
    public Long countRecursosAtivosFila(Long idFila) {
        final QFilaPessoa qFilaPessoa = QFilaPessoa.filaPessoa;

        return new JPAQueryFactory(entityManager)
                .select(qFilaPessoa)
                .from(qFilaPessoa)
                .where(qFilaPessoa.fila.id.eq(idFila))
                .where(qFilaPessoa.ativo.eq(true)).fetchCount();
    }


    /**
     * Consulta e retorna a quantidade de pessoas relacionados a fila
     *
     * @param idFila
     */
    @Override
    public Long countRecursosFila(Long idFila) {
        final QFilaPessoa qFilaPessoa = QFilaPessoa.filaPessoa;

        return new JPAQueryFactory(entityManager)
                .select(qFilaPessoa)
                .from(qFilaPessoa)
                .where(qFilaPessoa.fila.id.eq(idFila))
                .fetchCount();
    }


    /**
     * Consulta os recursos ativos da fila.
     *
     * @param idFila
     */
    @Override
    public List<FilaPessoa> consultaRecursosAtivosFila(Long idFila) {
        final QFilaPessoa qFilaPessoa = QFilaPessoa.filaPessoa;

        return new JPAQueryFactory(entityManager)
                .select(qFilaPessoa)
                .from(qFilaPessoa)
                .where(qFilaPessoa.fila.id.eq(idFila))
                .where(qFilaPessoa.ativo.eq(true))
                .fetch();
    }

}
