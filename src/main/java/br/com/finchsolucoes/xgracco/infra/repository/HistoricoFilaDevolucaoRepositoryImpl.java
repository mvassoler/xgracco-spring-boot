package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.DadosBasicosTarefa;
import br.com.finchsolucoes.xgracco.domain.entity.HistoricoFilaDevolucao;
import br.com.finchsolucoes.xgracco.domain.entity.QHistoricoFilaDevolucao;
import br.com.finchsolucoes.xgracco.domain.repository.HistoricoFilaDevolucaoJpaRepository;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class HistoricoFilaDevolucaoRepositoryImpl implements HistoricoFilaDevolucaoJpaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<HistoricoFilaDevolucao> findByDadosBasicosTarefa(DadosBasicosTarefa dadosBasicosTarefa) {
        QHistoricoFilaDevolucao qHistoricoFilaDevolucao = QHistoricoFilaDevolucao.historicoFilaDevolucao;

        return Optional.ofNullable(new JPAQueryFactory(entityManager)
                .select(qHistoricoFilaDevolucao)
                .from(qHistoricoFilaDevolucao)
                .where(qHistoricoFilaDevolucao.dadosBasicosTarefa.eq(dadosBasicosTarefa))
                .where(qHistoricoFilaDevolucao.devolucaoConcluida.eq(false).or(qHistoricoFilaDevolucao.devolucaoConcluida.isNull()))
                .fetchOne());
    }

    @Override
    public void removeByIdFila(Long idFila) {
        QHistoricoFilaDevolucao qHistoricoFilaDevolucao = QHistoricoFilaDevolucao.historicoFilaDevolucao;

        new JPADeleteClause(entityManager, qHistoricoFilaDevolucao)
                .where(qHistoricoFilaDevolucao.fila.id.eq(idFila))
                .execute();
    }

    @Override
    public void create(HistoricoFilaDevolucao historicoFilaDevolucao) {
        entityManager.persist(historicoFilaDevolucao);
        entityManager.flush();
    }

    @Override
    public void update(HistoricoFilaDevolucao historicoFilaDevolucao) {
        entityManager.merge(historicoFilaDevolucao);
        entityManager.flush();
    }
}
