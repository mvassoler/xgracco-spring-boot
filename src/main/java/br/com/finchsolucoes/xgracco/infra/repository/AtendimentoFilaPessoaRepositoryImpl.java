package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.*;
import br.com.finchsolucoes.xgracco.domain.repository.AtendimentoFilaPessoaJpaRepository;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

/**
 * Implementação JPA do repositório da entidade Fila
 *
 * @author Raphael Moreira
 * @since 2.1
 */
@Repository
public class AtendimentoFilaPessoaRepositoryImpl implements AtendimentoFilaPessoaJpaRepository {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public Optional<AtendimentoFilaPessoa> findAtendimentoFilaByPessoa(Pessoa pessoa) {
        final QAtendimentoFilaPessoa qAtendimentoFilaPessoa = QAtendimentoFilaPessoa.atendimentoFilaPessoa;
        final QPessoa qPessoa = QPessoa.pessoa;
        final QFila qFila = QFila.fila;
        final QTarefa qTarefa = QTarefa.tarefa;
        final QDadosBasicosTarefa qDadosBasicosTarefa = QDadosBasicosTarefa.dadosBasicosTarefa;
        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(qAtendimentoFilaPessoa)
                .from(qAtendimentoFilaPessoa)
                .join(qAtendimentoFilaPessoa.pessoa, qPessoa)
                .join(qAtendimentoFilaPessoa.dadosBasicosTarefa, qDadosBasicosTarefa)
                .leftJoin(qAtendimentoFilaPessoa.fila, qFila).fetchJoin()
                .leftJoin(qAtendimentoFilaPessoa.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefa).fetchJoin()
                .leftJoin(qFluxoTrabalhoTarefa.tarefa, qTarefa).fetchJoin()
                .where(qPessoa.eq(pessoa))
                .fetchFirst());
    }

    @Override
    public Long countAtendimentoFilaByFila(Fila fila) {
        final QAtendimentoFilaPessoa qAtendimentoFilaPessoa = QAtendimentoFilaPessoa.atendimentoFilaPessoa;

        return new JPAQueryFactory(entityManager)
                .select(qAtendimentoFilaPessoa)
                .from(qAtendimentoFilaPessoa)
                .where(qAtendimentoFilaPessoa.fila.eq(fila))
                .fetchCount();
    }

    @Override
    public void removePessoasFila(Long idFila) {
        QAtendimentoFilaPessoa qAtendimentoFilaPessoa = QAtendimentoFilaPessoa.atendimentoFilaPessoa;
        QFila qFila = QFila.fila;

        new JPAUpdateClause(entityManager, qAtendimentoFilaPessoa)
                .setNull(qAtendimentoFilaPessoa.fila)
                .where(qAtendimentoFilaPessoa.id.in(
                        new JPAQueryFactory(entityManager)
                                .select(qAtendimentoFilaPessoa.id)
                                .from(qAtendimentoFilaPessoa)
                                .join(qAtendimentoFilaPessoa.fila, qFila)
                                .where(qFila.id.eq(idFila))
                                .fetch()
                ))
                .execute();
    }

    /**
     * Remove a entidade através do ID.
     *
     * @param id
     */
    @Override
    public void removeById(Long id) {
        QAtendimentoFilaPessoa qAtendimentoFilaPessoa = QAtendimentoFilaPessoa.atendimentoFilaPessoa;
        new JPADeleteClause(entityManager, qAtendimentoFilaPessoa)
                .where(qAtendimentoFilaPessoa.id.eq(id))
                .execute();
    }


    @Override
    public Optional<AtendimentoFilaPessoa> findByDadosBasicosTarefa(DadosBasicosTarefa dadosBasicosTarefa) {
        final QAtendimentoFilaPessoa qAtendimentoFilaPessoa = QAtendimentoFilaPessoa.atendimentoFilaPessoa;
        final QPessoa qPessoa = QPessoa.pessoa;
        final QFila qFila = QFila.fila;
        final QTarefa qTarefa = QTarefa.tarefa;
        final QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(qAtendimentoFilaPessoa)
                .from(qAtendimentoFilaPessoa)
                .innerJoin(qAtendimentoFilaPessoa.pessoa, qPessoa).fetchJoin()
                .innerJoin(qAtendimentoFilaPessoa.fila, qFila).fetchJoin()
                .innerJoin(qAtendimentoFilaPessoa.fluxoTrabalhoTarefa, qFluxoTrabalhoTarefa).fetchJoin()
                .innerJoin(qFluxoTrabalhoTarefa.tarefa, qTarefa).fetchJoin()
                .where(qAtendimentoFilaPessoa.dadosBasicosTarefa.eq(dadosBasicosTarefa))
                .fetchOne());
    }

    @Override
    @Transactional
    public void create(AtendimentoFilaPessoa atendimentoFilaPessoa) {
        entityManager.persist(atendimentoFilaPessoa);
        entityManager.flush();
    }
}
