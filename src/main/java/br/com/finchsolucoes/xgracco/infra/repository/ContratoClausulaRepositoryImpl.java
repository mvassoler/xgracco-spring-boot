package br.com.finchsolucoes.xgracco.infra.repository;

import br.com.finchsolucoes.xgracco.domain.entity.ContratoClausula;
import br.com.finchsolucoes.xgracco.domain.entity.FluxoTrabalhoTarefa;
import br.com.finchsolucoes.xgracco.domain.entity.QContratoClausula;
import br.com.finchsolucoes.xgracco.domain.entity.QFluxoTrabalhoTarefa;
import br.com.finchsolucoes.xgracco.domain.repository.ContratoClausulaJpaRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Implementação JPA do repositório da entidade ContratoClausula.
 *
 * @author Rodolpho Couto
 * @since 2.1
 */
@Repository
public class ContratoClausulaRepositoryImpl implements ContratoClausulaJpaRepository {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public List<ContratoClausula> findByTarefa(FluxoTrabalhoTarefa fluxoTrabalhoTarefa) {
        QContratoClausula qContratoClausula = QContratoClausula.contratoClausula;
        QFluxoTrabalhoTarefa qFluxoTrabalhoTarefa = QFluxoTrabalhoTarefa.fluxoTrabalhoTarefa;

        return new JPAQueryFactory(entityManager)
                .select(qContratoClausula)
                .from(qContratoClausula)
                .join(qContratoClausula.fluxoTrabalhoTarefas, qFluxoTrabalhoTarefa)
                .where(qContratoClausula.fluxoTrabalhoTarefas.contains(fluxoTrabalhoTarefa))
                .fetch();
    }
}